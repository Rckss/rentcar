package com.ricks.rntcr.repository;

import com.ricks.rntcr.domain.RentHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the RentHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RentHistoryRepository extends JpaRepository<RentHistory, Long> {

    @Query("select rent_history from RentHistory rent_history where rent_history.client.login = ?#{principal.username}")
    List<RentHistory> findByClientIsCurrentUser();

    @Query(value = "select distinct rent_history from RentHistory rent_history left join fetch rent_history.cars",
        countQuery = "select count(distinct rent_history) from RentHistory rent_history")
    Page<RentHistory> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct rent_history from RentHistory rent_history left join fetch rent_history.cars")
    List<RentHistory> findAllWithEagerRelationships();

    @Query("select rent_history from RentHistory rent_history left join fetch rent_history.cars where rent_history.id =:id")
    Optional<RentHistory> findOneWithEagerRelationships(@Param("id") Long id);

    @Query(value = "select distinct rent_history from RentHistory rent_history left join fetch rent_history.cars where rent_history.client.id =:id",
        countQuery = "select count(distinct rent_history) from RentHistory rent_history")
    Page<RentHistory> findAllWithEagerRelationships(Pageable pageable, @Param("id") Long id);

    @Query(value = "select distinct rent_history from RentHistory rent_history where rent_history.client.id =:id",
        countQuery = "select count(distinct rent_history) from RentHistory rent_history")
    Page<RentHistory> findAll(Pageable pageable, @Param("id") Long id);
}
