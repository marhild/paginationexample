package com.example.paginationexample.controller;

import com.example.paginationexample.helper.PageModel;
import com.example.paginationexample.model.Director;
import com.example.paginationexample.model.Genre;
import com.example.paginationexample.model.Movie;
import com.example.paginationexample.repository.DirectorRepository;
import com.example.paginationexample.repository.GenreRepository;
import com.example.paginationexample.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private PageModel pageModel;

    /**
     * Pagination for one Entity example
     * @param model     represents data in the MVC, ( director objects per Page
     * @return          template 'directors.html'
     */
    @GetMapping("/directors")
    public String getAllDirectors(Model model) {
        pageModel.setSIZE(8);
        pageModel.initPageAndSize();
        model.addAttribute("directors", directorRepository.findAll(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE())));
        return "directors";
    }

    /**
     * 1:M example (Director:Movies)
     * @param directorId    id of director
     * @param model         represents data in the MVC: director object, Page with movie objects
     * @return              template 'moviesByDirector.html'
     */
    @GetMapping("/getMoviesByDirector/{directorId}")
    public String getMoviesByDirector(@PathVariable Long directorId, Model model) {
        pageModel.initPageAndSize();
        Optional<Director> director = directorRepository.findById(directorId);
        model.addAttribute("director", director.get());
        model.addAttribute("movies", movieRepository.findAllByDirectorId(directorId, PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE())));
        return "moviesByDirector";
    }

    /**
     * M:M example (Movies:Genres)
     * @param genreId       id of the genre
     * @param model         represents data in the MVC: genre object, Page with movie objects
     * @return              template 'moviesByGenre.html'
     */
    @GetMapping("/getMoviesByGenre/{genreId}")
    public String getMoviesByGenre(@PathVariable Long genreId, Model model) {
        pageModel.initPageAndSize();
        Optional<Genre> genre = genreRepository.findById(genreId);
        model.addAttribute("movies", movieRepository.findAllByGenres(genre.get(), PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE())));
        model.addAttribute("genre", genre.get());
        return "moviesByGenre";
    }

    /**
     * M:M example (Movies:Genres)
     * @param movieId       id of the movie
     * @param model         represents data in the MVC: movie object, Page with genre objects
     * @return              template 'genresByMovie.html'
     */
    @GetMapping("/getGenresByMovie/{movieId}")
    public String getGenresByMovie(@PathVariable Long movieId, Model model) {
        pageModel.initPageAndSize();
        Optional<Movie> movie = movieRepository.findById(movieId);
        model.addAttribute("genres", genreRepository.findAllByMovies(movie.get(), PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE())));
        model.addAttribute("movie", movie.get());
        return "genresByMovie";
    }
}