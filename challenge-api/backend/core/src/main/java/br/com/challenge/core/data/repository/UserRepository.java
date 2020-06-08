package br.com.challenge.core.data.repository;


import br.com.challenge.core.data.entity.User;
import br.com.challenge.core.data.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long>, UserRepositoryCustom {



    User findByName(String name);

    //	@Query("SELECT COUNT (t) " +
//			"FROM Team AS t " +
//			"INNER JOIN t.users AS teamUser " +
//			"WHERE teamUser.userId = :userId " +
//			"AND teamUser.teamGroup IN (br.com.surittec.atmosfero.core.entity.enums.Group.OWNERS, br.com.surittec.atmosfero.core.entity.enums.Group.ADMINISTRATOR)")
//    Long countAllTeamsWhereUserCanCreateProjects(@Param("userId") String userId);
//    User findByEmail(String email);

}
