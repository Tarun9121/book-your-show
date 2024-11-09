package com.bookings.dao;

import com.bookings.entity.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Component
public class ShowDao {
    @Autowired
    private EntityManager entityManager;

    public List<Show> availableShowsByTheaterMovieId(UUID theaterMovieId) {
        String jpql = "SELECT s FROM Show s WHERE s.theaterMovie.id = :theaterMovieId";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("theaterMovieId", theaterMovieId);

        return query.getResultList();
    }
}
