package cz.anona.snyverse.repositories;

import cz.anona.snyverse.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    List<TagEntity> findByNameIn(List<String> names);
}
