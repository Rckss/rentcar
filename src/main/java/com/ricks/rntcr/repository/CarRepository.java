package com.ricks.rntcr.repository;

import com.ricks.rntcr.domain.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Car entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = "select distinct car from Car car left join fetch car.tags",
        countQuery = "select count(distinct car) from Car car")
    Page<Car> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct car from Car car left join fetch car.tags")
    List<Car> findAllWithEagerRelationships();

    @Query("select car from Car car left join fetch car.tags where car.id =:id")
    Optional<Car> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select car from Car car left join fetch car.tags left join fetch car.photo where car.id =:id")
    Optional<Car> findOneCompletedWithEagerRelationships(@Param("id") Long id);

    // how to get car and photo in one query?
    // need to create a new dto or domain?
    // *modify dto
    // declare two fetch
    // take care of caching!!!

}
