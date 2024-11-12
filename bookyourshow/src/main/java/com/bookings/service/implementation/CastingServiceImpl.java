package com.bookings.service.implementation;

import com.bookings.dto.CastingDto;
import com.bookings.entity.Actor;
import com.bookings.entity.Casting;
import com.bookings.entity.Movie;
import com.bookings.exception.ApiException;
import com.bookings.repository.ActorRepository;
import com.bookings.repository.CastingRepository;
import com.bookings.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CastingServiceImpl {

    private final CastingRepository castingRepository;
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    @Autowired
    public CastingServiceImpl(CastingRepository castingRepository, MovieRepository movieRepository, ActorRepository actorRepository) {
        this.castingRepository = castingRepository;
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    @Transactional
    public ResponseEntity<Casting> addCastingToMovie(CastingDto castingDto) {
        try {
            Movie movie = movieRepository.findById(castingDto.getMovieId())
                    .orElseThrow(() -> new ApiException("Movie not found with ID: " + castingDto.getMovieId()));

            Actor actor = actorRepository.findById(castingDto.getActorId())
                    .orElseThrow(() -> new ApiException("Actor not found with ID: " + castingDto.getActorId()));

            Casting casting = new Casting();
            casting.setMovie(movie);
            casting.setActor(actor);
            casting.setCharacterName(castingDto.getCharacterName());
            casting.setRole(castingDto.getRole());

            return ResponseEntity.ok(castingRepository.save(casting));
        } catch (Exception ex) {
            throw new ApiException("An error occurred while adding casting: " + ex.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<Actor>> getActorsByMovieId(UUID movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiException("Movie not found with ID: " + movieId));

        List<Casting> castings = castingRepository.findByMovie(movie);
        List<Actor> actors = castings.stream().map(Casting::getActor).collect(Collectors.toList());

        return ResponseEntity.ok(actors);
    }
}
