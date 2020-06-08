package br.com.challenge.rest.util;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.challenge.core.data.vo.rest.EnvelopeVO;
import br.com.challenge.core.data.enumeration.MessageCode;
import br.com.challenge.core.util.Json;

/**
 * Response utils reunindo uns facilitadores para redução de codigo nas controllers
 */
public class ResponseUtils {

    /*
     * Base
     */

	public static ResponseEntity<EnvelopeVO> createResponse(HttpStatus status, Object data, MessageCode messageCode, Object... params) {
		return createResponse(status.value(), data, messageCode, params);
	}

	public static ResponseEntity<EnvelopeVO> createResponse(Integer httpCode, Object data, MessageCode messageCode, Object... params) {
		if (data instanceof Json)
			data = ((Json) data).toMap();

		EnvelopeVO envelope = EnvelopeVO.instance().data(data).message(messageCode, params);
		return ResponseEntity.status(httpCode).body(envelope);
	}

	public static Json json() {
		return Json.inst();
	}


	/*
	 * 20X
	 */

	//200
	public static ResponseEntity<EnvelopeVO> ok(Object data, MessageCode messageCode, Object... params) {
		return createResponse(OK, data, messageCode, params);
	}

	//200
	public static ResponseEntity<EnvelopeVO> ok(Object data) {
		return createResponse(OK, data, null);
	}

	//201
	public static ResponseEntity<EnvelopeVO> created(MessageCode messageCode, Object... params) {
		return createResponse(CREATED, null, messageCode, params);
	}

	//201
	public static ResponseEntity<EnvelopeVO> created(Object dataBody, MessageCode messageCode, Object... params) {
		return createResponse(CREATED, dataBody, messageCode, params);
	}


    /*
     * 40X
     */

	//400
	public static ResponseEntity<EnvelopeVO> badRequest(MessageCode messageCode, Object... params) {
		return createResponse(BAD_REQUEST, null, messageCode, params);
	}

	public static ResponseEntity<EnvelopeVO> badRequest(Object data, MessageCode messageCode, Object... params) {
		return createResponse(BAD_REQUEST, data, messageCode, params);
	}

	//401
	public static ResponseEntity<EnvelopeVO> unauthorized(MessageCode messageCode, Object... params) {
		return createResponse(UNAUTHORIZED, null, messageCode, params);
	}

	//404
	public static ResponseEntity<EnvelopeVO> notFound(MessageCode messageCode, Object... params) {
		return createResponse(NOT_FOUND, null, messageCode, params);
	}
}
