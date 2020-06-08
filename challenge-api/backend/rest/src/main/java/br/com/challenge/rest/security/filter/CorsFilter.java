package br.com.challenge.rest.security.filter;

import static org.springframework.http.HttpHeaders.*;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class CorsFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String origin = request.getHeader("Origin");

		//CORS HEADERS
		response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin != null ? origin : "*"); //FIXME FREE FOR ALL, LEMBRAR DE TROCAR ISSO. PEGAR DE ACORDO COM O AMBIENTE DA BUILD
		response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, PATCH, DELETE, OPTIONS");
		response.setHeader(ACCESS_CONTROL_MAX_AGE, "3600");
		response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept, Authorization, XSRF-Token");
		response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, "XSRF-Token, Renew-JWT");

		if ("OPTIONS".equals(request.getMethod()))
			response.setStatus(HttpServletResponse.SC_OK);

		else
			filterChain.doFilter(request, response);
	}
}
