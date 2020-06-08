//package br.com.infosolo.appname.restapp.config.security.interceptor;
//
//import static br.com.surittec.atmosfero.core.entity.enums.Group.USER;
//import static br.com.surittec.atmosfero.core.util.enumeration.MessageCode.E_FORBIDDEN_PERMISSION;
//import static br.com.surittec.atmosfero.core.util.rest.RestConstants.*;
//import static org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import br.com.surittec.atmosfero.app.config.security.UserPrincipal;
//import br.com.surittec.atmosfero.app.config.security.jwt.JwtTokenAuthentication;
//import br.com.surittec.atmosfero.app.config.security.service.RestSecurityService;
//import br.com.surittec.atmosfero.core.dto.EnvelopeVO;
//import br.com.surittec.atmosfero.core.entity.*;
//import br.com.surittec.atmosfero.core.entity.enums.Role;
//import br.com.surittec.atmosfero.core.util.security.SecurityContext;
//import br.com.surittec.atmosfero.core.util.spring.ApplicationContextUtil;
//
///**
// * Secure Team and Project data.
// * Grant only to authorized users
// */
//public class AdditionalSecurityHandlerInterceptor implements HandlerInterceptor {
//	private RestSecurityService restSecurityService;
//	private ObjectMapper objectMapper;
//
//	private Team team = null;
//	private Project project = null;
//	private TeamUser teamUser = null;
//	private ProjectUser projectUser = null;
//
//	private void init() {
//		team = null;
//		teamUser = null;
//		project = null;
//		projectUser = null;
//		restSecurityService = ApplicationContextUtil.getBean(RestSecurityService.class);
//		objectMapper = ApplicationContextUtil.getBean(ObjectMapper.class);
//	}
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		User user = SecurityContext.getUser();
//		Map pathVariables = (Map) request.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE);
//		boolean authorizedRequest = true;
//
//		if (user != null && CollectionUtils.isNotEmpty(pathVariables.keySet())) {
//			init();
//			authorizedRequest = validateTeamMembership(pathVariables, user);
//			authorizedRequest = authorizedRequest && validateProjectMembership(pathVariables, user);
//
//			if (!authorizedRequest) {
//				EnvelopeVO envelopeDto = EnvelopeVO.instance().message(E_FORBIDDEN_PERMISSION);
//
//				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//				response.setContentType(DEFAULT_MEDIA_TYPE);
//				response.getWriter().write(objectMapper.writeValueAsString(envelopeDto));
//				response.getWriter().flush();
//				response.getWriter().close();
//
//			} else {
//				UserPrincipal principal = SecurityContext.getUserPrincipalAs();
//				Collection<Role> authorities = new ArrayList<>();
//
//				if (teamUser != null)
//					authorities.addAll(teamUser.getTeamGroup().getRoles());
//
//				if (projectUser != null)
//					authorities.addAll(projectUser.getProjectGroup().getRoles());
//
//				UserPrincipal newPrincipal = new UserPrincipal(
//						principal.getToken(),
//						principal.getUser(),
//						team,
//						project,
//						principal.isSocialLogin(),
//						authorities,
//						teamUser,
//						projectUser
//				);
//
//				SecurityContextHolder.getContext().setAuthentication(new JwtTokenAuthentication(newPrincipal));
//			}
//
//		}
//
//		return authorizedRequest;
//	}
//
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//	}
//
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//	}
//}