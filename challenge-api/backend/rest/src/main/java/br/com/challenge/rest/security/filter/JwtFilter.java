package br.com.challenge.rest.security.filter;


import static br.com.challenge.rest.util.RestConstants.DEFAULT_MEDIA_TYPE;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.challenge.rest.security.jwt.JwtTokenAuthentication;
import br.com.challenge.rest.security.service.RestSecurityService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.challenge.core.base.security.authentication.UserPrincipal;
import br.com.challenge.core.data.enumeration.MessageCode;
import br.com.challenge.core.data.vo.rest.EnvelopeVO;


public class JwtFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Value(value = "${jwt.header}")
    private String AUTH_HEADER = "Authorization";

    @Autowired
    private RestSecurityService restSecurityService;

    @Autowired
    private ObjectMapper objectMapper;

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTH_HEADER);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authToken = getToken(request);
        // get username from token
        UserPrincipal principal;

        if (StringUtils.isNotBlank(authToken)) {

            try {
                principal = restSecurityService.authenticate(authToken);
                SecurityContextHolder.getContext().setAuthentication(new JwtTokenAuthentication(principal));
                response.addHeader("Renew-JWT", principal.getToken());

            } catch (BadCredentialsException be) {
                EnvelopeVO envelopeDto = EnvelopeVO.instance().message(MessageCode.valueOf(be.getMessage()));
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(DEFAULT_MEDIA_TYPE);
                response.getWriter().write(objectMapper.writeValueAsString(envelopeDto));
                response.getWriter().flush();
                response.getWriter().close();

                return;
            }
        }

        chain.doFilter(request, response);
    }
}
