package br.com.challenge.rest.security.service;


import java.util.Calendar;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.challenge.core.base.exception.BusinessException;
import br.com.challenge.core.base.security.authentication.UserPrincipal;
import br.com.challenge.core.data.entity.User;
import br.com.challenge.core.data.enumeration.MessageCode;
import br.com.challenge.core.data.vo.CredentialsVO;
import br.com.challenge.core.service.UserService;
import br.com.challenge.core.util.PasswordUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Validated
public class RestSecurityService {

    @Value("${app.name}")
    private String appName;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expires_in}")
    private int expiresIn;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    private final UserService userService;

    private final Hashids hashids;

    @Autowired
    public RestSecurityService(Hashids hashids, UserService userService) {
        this.userService = userService;
        this.hashids = hashids;
    }

    private Claims getClaimsFromToken(@NotBlank String token) {
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ignored) {
        }

        return claims;
    }

    private UserPrincipal createStatelessSession(@NotNull User user) {
        String token = this.generateJtwToken(user);

        return new UserPrincipal(token, user, user.getUserGroup().getRoles());
    }

    private String generateJtwToken(@NotNull User user) {
        return Jwts.builder()
                .setIssuer(appName)
                .setSubject(String.valueOf(user.getId()))
                .claim("name", user.getName())
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(new Date(System.currentTimeMillis() + this.expiresIn))
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    private Long getUserIdFromToken(@NotBlank String token) {
        Long userid = null;
        final Claims claims = this.getClaimsFromToken(token);

        if (claims != null)
            userid = Long.valueOf(claims.getSubject());

        return userid;
    }

    private String getClaimFromToken(@NotBlank String token, @NotBlank String key) {
        String value = null;
        final Claims claims = this.getClaimsFromToken(token);

        if (claims != null)
            value = claims.get(key) != null ? claims.get(key).toString() : null;

        return value;
    }

    /**
     * Public members
     */

    public UserPrincipal authenticate(@Valid @NotNull CredentialsVO credentials) {
        User user = userService.findByName(credentials.getName());
        if (user == null || !PasswordUtil.matches(credentials.getPassword(), user.getPassword()))
            throw new BusinessException(MessageCode.E_INVALID_CREDENTIALS);

        return createStatelessSession(user);
    }


    public UserPrincipal authenticate(@NotBlank String token) {
        Long userId = getUserIdFromToken(token);
        UserPrincipal principal;

        if (userId == null)
            throw new BadCredentialsException(MessageCode.E_SESSION_EXPIRED.name());

        User user = userService.findById(userId);

        if (user == null)
            throw new BadCredentialsException(MessageCode.E_INVALID_ACCOUNT.name());

        principal = createStatelessSession(user);

        return principal;
    }


}
