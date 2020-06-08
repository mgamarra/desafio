package br.com.challenge.rest.controller;


import br.com.challenge.core.base.security.SecurityContext;
import br.com.challenge.core.base.security.authentication.UserPrincipal;
import br.com.challenge.core.base.security.authorization.Secure;
import br.com.challenge.core.data.mapper.UserMapper;
import br.com.challenge.core.data.vo.CredentialsVO;
import br.com.challenge.core.data.vo.rest.EnvelopeVO;
import br.com.challenge.core.util.Json;
import br.com.challenge.rest.security.service.RestSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static br.com.challenge.rest.util.ResponseUtils.ok;
import static br.com.challenge.rest.util.RestConstants.AUTH_PATH;
import static br.com.challenge.rest.util.RestConstants.DEFAULT_MEDIA_TYPE;


@RestController
@RequestMapping(value = AUTH_PATH, produces = DEFAULT_MEDIA_TYPE)
public class AuthController extends BaseController {

    private final RestSecurityService securityService;

    @Autowired
    public AuthController(RestSecurityService securityService) {
        super(AUTH_PATH);
        this.securityService = securityService;
    }

    @GetMapping(path = "/current", consumes = DEFAULT_MEDIA_TYPE, produces = DEFAULT_MEDIA_TYPE)
    @Secure
    public ResponseEntity<EnvelopeVO> getCurrent() {
        UserPrincipal principal = UserPrincipal.class.cast(SecurityContext.getUserPrincipal());

        if (principal == null)
            return null;

        return ok(Json.inst()
                .kv("token", principal.getToken())
                .kv("user", UserMapper.INST.userToUserAuthResponseVO(principal.getUser()))
                .toMap());
    }


    @PostMapping(value = "/login", consumes = DEFAULT_MEDIA_TYPE, produces = DEFAULT_MEDIA_TYPE)
    public ResponseEntity<EnvelopeVO> login(@NotNull @RequestBody CredentialsVO credentials) {

        UserPrincipal principal = securityService.authenticate(credentials);

        return ok(Json.inst()
                .kv("token", principal.getToken())
                .kv("user", UserMapper.INST.userToUserAuthResponseVO(principal.getUser()))
                .toMap());
    }

}
