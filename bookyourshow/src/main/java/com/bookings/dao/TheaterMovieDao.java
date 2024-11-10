package com.bookings.dao;

import com.bookings.dto.TheaterMovieNativeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class TheaterMovieDao {
    @Autowired
    private EntityManager entityManager;

    public UUID findId(UUID theaterId, UUID movieId) {
        String jpql = "SELECT tm.id FROM TheaterMovie tm WHERE tm.movie.id = :movieId AND tm.theater.id = :theaterId";
        Query query = entityManager.createQuery(jpql);

        log.info("Movie ID: " + movieId.toString());
        log.info("Theater ID: " + theaterId.toString());

        query.setParameter("movieId", movieId);
        query.setParameter("theaterId", theaterId);

        try {
            return (UUID) query.getSingleResult();
        } catch (NonUniqueResultException multipleResult) {
            return (UUID) query.getResultList().get(0);
        }
    }

    public List<TheaterMovieNativeDto> getAssociatedTheaters(UUID movieId) {
        String sql = "SELECT new com.bookings.dto.TheaterMovieNativeDto(tm.id, tm.theater.id, tm.movie.id) " +
                "FROM TheaterMovie tm WHERE tm.movie.id = :movieId";
        Query query = entityManager.createQuery(sql);
        query.setParameter("movieId", movieId);

        return query.getResultList();
    }

}
