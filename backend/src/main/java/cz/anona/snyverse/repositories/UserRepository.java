package cz.anona.snyverse.repositories;

import cz.anona.snyverse.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM user_entity")
    List<UserEntity> findAll();

    @Query(nativeQuery = true, value = "SELECT * FROM user_entity WHERE user_entity.visible IS NOT 'DELETED' AND user_entity.id = ?1")
    UserEntity getOne(@NonNull Long id);

}
