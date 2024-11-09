package com.bookings.dao;

import com.bookings.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
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

}
