package br.com.challenge.core.data.mapper;

import br.com.challenge.core.data.entity.User;
import br.com.challenge.core.data.vo.CreateUserVO;
import br.com.challenge.core.data.vo.UserAuthResponseVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INST = Mappers.getMapper(UserMapper.class);

    UserAuthResponseVO userToUserAuthResponseVO(User car);
}
