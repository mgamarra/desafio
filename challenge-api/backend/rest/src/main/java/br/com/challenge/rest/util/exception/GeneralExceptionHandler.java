package br.com.challenge.rest.util.exception;

import static br.com.challenge.core.data.enumeration.MessageCode.E_ERROR_PROCESSING_REQUEST;
import static br.com.challenge.core.data.enumeration.MessageCode.E_FORBIDDEN_PERMISSION;
import static br.com.challenge.core.data.enumeration.MessageCode.E_MISSING_PARAMETERS;
import static br.com.challenge.core.data.enumeration.MessageCode.E_PATTERN_INVALID_PARAMETER_VALUE;
import static br.com.challenge.core.data.enumeration.MessageCode.E_RECORD_NOT_FOUND;
import static br.com.challenge.core.data.enumeration.MessageCode.E_UNAUTHORIZED;
import static br.com.challenge.core.data.enumeration.MessageCode.E_UPDATING_OLD_DATA;
import static org.springframework.http.HttpStatus.*;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.OptimisticLockException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.challenge.rest.util.ResponseUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.challenge.core.base.exception.BusinessException;
import br.com.challenge.core.base.security.SecurityContext;
import br.com.challenge.core.data.enumeration.MessageCode;
import br.com.challenge.core.data.vo.rest.EnvelopeVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return (ResponseEntity) ResponseUtils.badRequest(E_ERROR_PROCESSING_REQUEST, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ViolationReport> violationReports = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ViolationReport(ex.getBindingResult().getTarget().getClass().getCanonicalName(), fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return (ResponseEntity) ResponseUtils.badRequest(violationReports, E_MISSING_PARAMETERS, ex.getMessage());
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<EnvelopeVO> handleBE(BusinessException ex, WebRequest request) {
        return ResponseUtils.createResponse(ex.getCode().getCode(), null, ex.getCode(), ex.getParams());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity handleIAE(IllegalArgumentException ex, WebRequest request) {
        return ResponseUtils.badRequest(E_ERROR_PROCESSING_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({TransactionSystemException.class})
    public ResponseEntity handleTSE(TransactionSystemException ex, WebRequest request) {
        Throwable root = ExceptionUtils.getRootCause(ex);

        ResponseEntity response = null;

        if (root != null) {
            if (root instanceof ConstraintViolationException) {
                List<ViolationReport> violationReports = ConstraintViolationException.class.cast(root)
                        .getConstraintViolations().stream()
                        .map(ViolationReport::new)
                        .collect(Collectors.toList());

                response = ResponseUtils.createResponse(BAD_REQUEST, violationReports, E_MISSING_PARAMETERS);
            }
        } else {
            response = ResponseUtils.createResponse(INTERNAL_SERVER_ERROR, null, E_ERROR_PROCESSING_REQUEST, ex.getMessage());
        }

        return response;
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity handleBCE(BadCredentialsException ex, WebRequest request) {
        return ResponseUtils.unauthorized(MessageCode.valueOf(ex.getMessage()));
    }

    @ExceptionHandler({OptimisticLockException.class})
    public ResponseEntity handleOLE(OptimisticLockException ex, WebRequest request) {
        return ResponseUtils.badRequest(E_UPDATING_OLD_DATA);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity handleADE(AccessDeniedException ex, WebRequest request) {

        MessageCode messageCode = SecurityContext.getUserPrincipal() == null ? E_UNAUTHORIZED : E_FORBIDDEN_PERMISSION;
        HttpStatus status = SecurityContext.getUserPrincipal() == null ? UNAUTHORIZED: FORBIDDEN;

        return ResponseUtils.createResponse(status, null, messageCode);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity handleCVE(ConstraintViolationException cve, WebRequest request) {
        ResponseEntity<EnvelopeVO> response = ResponseUtils.badRequest(E_ERROR_PROCESSING_REQUEST);
        Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();

        Predicate<ConstraintViolation<?>> predNotNull = cv -> cv.getConstraintDescriptor().getAnnotation().annotationType().equals(NotNull.class);
        Predicate<ConstraintViolation<?>> predPattern = cv -> cv.getConstraintDescriptor().getAnnotation().annotationType().equals(Pattern.class);
        Predicate<ConstraintViolation<?>> predReturnValue = cv -> cv.getPropertyPath().toString().contains("<return value>");

        boolean isCVEOfNotNullReturn = violations.stream().anyMatch(cv -> predNotNull.and(predReturnValue).test(cv));
        boolean isCVEOfPattern = violations.stream().anyMatch(predPattern);

        if (isCVEOfNotNullReturn) {
            response = ResponseUtils.notFound(E_RECORD_NOT_FOUND);

        } else if (isCVEOfPattern) {
            ConstraintViolation<?> cv = violations.stream().findFirst().orElse(null);
            String propertyName = ((PathImpl) cv.getPropertyPath()).getLeafNode().getName();
            String regexp = cv.getConstraintDescriptor().getAttributes().get("regexp").toString();
            response = ResponseUtils.badRequest(E_PATTERN_INVALID_PARAMETER_VALUE, propertyName, regexp);
        } else {
            cve.printStackTrace();
            log.error("CVE IN WRONG FORMAT, REVIEW IT. GeneralExceptionHandler:handleCVE");
        }
        return response;
    }
}
