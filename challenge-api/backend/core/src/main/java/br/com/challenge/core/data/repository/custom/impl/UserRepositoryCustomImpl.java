package br.com.challenge.core.data.repository.custom.impl;

import br.com.challenge.core.data.entity.User;
import br.com.challenge.core.data.repository.BaseRepository;
import br.com.challenge.core.data.repository.custom.UserRepositoryCustom;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.challenge.core.data.entity.QUser;


public class UserRepositoryCustomImpl extends BaseRepository implements UserRepositoryCustom {
    private QUser user = QUser.user;


    /*
     *   Public methods
     */

    @Override
    public boolean isUniqueCPF(Long userId, String cpf) {
        JPAQuery q = getJPAQueryFactory().selectFrom(user);

        if (userId != null)
            q.where(user.id.ne(userId));

        q.where(user.cpf.eq(cpf));

        return q.fetchCount() == 0;
    }

    @Override
    public boolean isUniqueName(String name) {
        JPAQuery q = getJPAQueryFactory().selectFrom(user);

        q.where(user.name.eq(name));

        return q.fetchCount() == 0;
    }


    @Override
    public User findBy(Long id, String usernameEmail, boolean enabled) {
        JPAQuery q = getJPAQueryFactory().selectFrom(user);

        if (id != null)
            q.where(user.id.eq(id));

        q.where(user.enabled.isTrue());

        return (User) q.fetchOne();
    }

}
