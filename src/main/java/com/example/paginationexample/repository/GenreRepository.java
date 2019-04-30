package com.example.paginationexample.repository;

import com.example.paginationexample.model.Genre;
import com.example.paginationexample.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findById(Long genreId);

    /**
     * DB contains table 'movies_genres:(movie_id, genre_id)
     * Spring Data JPA automatically finds all Genres that are related to 'movie'
     *
     * @param movie         movie object, contains 'movie_id'
     * @param pageable      Abstract interface for pagination information from PageRequest
     * @return              Page<T> Object with Genre Objects
     */
    Page<Genre> findAllByMovies(Movie movie, Pageable pageable);
}
