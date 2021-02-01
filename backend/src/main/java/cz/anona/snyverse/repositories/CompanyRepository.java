package cz.anona.snyverse.repositories;

import cz.anona.snyverse.entities.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<CompanyEntity, Long> {

    Page<CompanyEntity> findAllByNameContains(Pageable pageable, String name);

    List<CompanyEntity> findAllByNameEquals(String name);

}
