package br.com.challenge.core.data.repository.custom;

import br.com.challenge.core.data.entity.User;

public interface UserRepositoryCustom {


    boolean isUniqueCPF(Long userId, String cpf);

    boolean isUniqueName(String cpf);

    User findBy(Long id, String email, boolean enabled);


}
