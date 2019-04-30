package com.example.paginationexample.repository;

import com.example.paginationexample.model.Genre;
import com.example.paginationexample.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findById(Long movieId);

    /**
     * DB has table 'movies:(id, name, director_id)'
     * @param directorId        director_id
     * @param pageable          Abstract interface for pagination information from PageRequest
     * @return                  Page<T> Object with Movie Objects
     */
    Page<Movie> findAllByDirectorId(Long directorId, Pageable pageable);

    /**
     * DB contains table 'movies_genres:(movie_id, genre_id)
     * Spring Data JPA automatically finds all Genres that are related to 'genre'
     *
     * @param genre         genre object, contains 'genre_id'
     * @param pageable      Abstract interface for pagination information from PageRequest
     * @return              Page<T> Object with Movie Objects
     */
    Page<Movie> findAllByGenres(Genre genre, Pageable pageable);
}
