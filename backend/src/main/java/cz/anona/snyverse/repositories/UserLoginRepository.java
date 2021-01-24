package cz.anona.snyverse.repositories;

import cz.anona.snyverse.entities.UserLoginEntity;
import cz.anona.snyverse.entities.UserSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLoginEntity, Long> {
    UserLoginEntity getByUsername(String username);
    UserLoginEntity getByEmail(String email);
}
