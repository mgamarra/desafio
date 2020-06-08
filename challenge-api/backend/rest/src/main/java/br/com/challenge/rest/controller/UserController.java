package br.com.challenge.rest.controller;

import br.com.challenge.core.data.entity.User;
import br.com.challenge.core.data.vo.CreateUserVO;
import br.com.challenge.core.data.vo.rest.EnvelopeVO;
import br.com.challenge.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static br.com.challenge.core.data.enumeration.MessageCode.I_USER_HAS_BEEN_CREATED;
import static br.com.challenge.rest.util.ResponseUtils.created;
import static br.com.challenge.rest.util.ResponseUtils.ok;
import static br.com.challenge.rest.util.RestConstants.DEFAULT_MEDIA_TYPE;
import static br.com.challenge.rest.util.RestConstants.USER_PATH;

@RestController
@RequestMapping(value = USER_PATH, produces = DEFAULT_MEDIA_TYPE)
public class UserController extends BaseController{

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        super(USER_PATH);
        this.userService = userService;
    }

    @GetMapping(produces = DEFAULT_MEDIA_TYPE)
    public ResponseEntity<EnvelopeVO> fetchAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                               @RequestParam(value = "size", required = false, defaultValue = "5") Integer size){
        return ok(userService.fetchAll(page,size));
    }

    @PostMapping(value = "/save", produces = DEFAULT_MEDIA_TYPE)
    public ResponseEntity<EnvelopeVO> register(@RequestBody @Valid @NotNull CreateUserVO createUserVO) {

        User user = userService.createUser(createUserVO);

        return created(I_USER_HAS_BEEN_CREATED, user.getName());
    }
}
