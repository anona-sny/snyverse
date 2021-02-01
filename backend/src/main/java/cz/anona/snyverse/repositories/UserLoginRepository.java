package cz.anona.snyverse.repositories;

import cz.anona.snyverse.entities.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;


@Repository
public interface UserLoginRepository extends JpaRepository<UserLoginEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM user_login_entity WHERE user_login_entity.username = ?1")
    UserLoginEntity getByUsername(@NonNull String username);

    @Query(nativeQuery = true, value = "SELECT * FROM user_login_entity WHERE user_login_entity.email = ?1")
    UserLoginEntity getByEmail(@NonNull String email);
}
