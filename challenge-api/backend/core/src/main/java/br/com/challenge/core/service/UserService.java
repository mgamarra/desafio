package br.com.challenge.core.service;


import br.com.challenge.core.base.security.SecurityContext;
import br.com.challenge.core.data.entity.Address;
import br.com.challenge.core.data.entity.User;
import br.com.challenge.core.data.enumeration.Group;
import br.com.challenge.core.data.enumeration.MessageCode;
import br.com.challenge.core.data.enumeration.State;
import br.com.challenge.core.data.repository.AddressRepository;
import br.com.challenge.core.data.repository.ApiAddressRepository;
import br.com.challenge.core.data.repository.UserRepository;
import br.com.challenge.core.data.vo.CreateUserVO;
import br.com.challenge.core.data.vo.UserVO;
import br.com.challenge.core.util.Assert;
import br.com.challenge.core.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Service
@Validated
public class UserService extends ServiceSupport {

    private static final long serialVersionUID = -5193563719045746402L;
    private UserRepository userRepository;
    private AddressRepository addressRepository;

    @Autowired
    public UserService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @NotNull
    @Transactional
    public User createUser(@Valid @NotNull CreateUserVO createUserVO) {
        Assert.isTrue(userRepository.isUniqueName(createUserVO.getName()), MessageCode.E_USER_NOT_UNIQUE);
        User user = new User();
        user.setName(createUserVO.getName());
        user.setCpf(createUserVO.getCpf());
        for(String email : createUserVO.getEmails()){
            user.getEmails().put("emails", email);
        }

        for(Map<String, String> phone: createUserVO.getPhones()){
            user.getPhones().add(phone);
        }

        user.setUserGroup(Group.READER);
        user.setPassword(PasswordUtil.encode(createUserVO.getPassword()));

        user = userRepository.save(user);

        manageAddressData(createUserVO, user);

        return user;
    }

    @Transactional
    private Address manageAddressData(@Valid @NotNull CreateUserVO createUserVO, User user) {
        Address address = new Address();
        address.setCity(createUserVO.getCity());
        address.setState(State.valueOf(createUserVO.getUf()));
        address.setDescription(createUserVO.getAddressDescription());
        address.setZipCode(createUserVO.getZipCode());
        address.setUserId(user.getId());
        addressRepository.save(address);
        return address;
    }

    @Transactional
    public User update(@Valid @NotNull UserVO userDto) {
        User persistentUser = proxy(this).findById(SecurityContext.getUserId());
        return userRepository.save(persistentUser);
    }

    @NotNull
    public User findById(@NotNull Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @NotNull
    public User findByName(@NotNull String name) {
        return userRepository.findByName(name);
    }


    public Page<User> fetchAll(@NotNull Integer page, @NotNull Integer size){
        return userRepository.findAll(PageRequest.of(page, size));
    }
}
