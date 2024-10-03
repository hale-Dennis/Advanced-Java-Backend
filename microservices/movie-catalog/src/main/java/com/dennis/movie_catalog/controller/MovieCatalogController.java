package com.dennis.movie_catalog.controller;


import com.dennis.movie_catalog.clients.MovieInfoClient;
import com.dennis.movie_catalog.clients.MovieRatingClient;
import com.dennis.movie_catalog.model.CatalogItem;
import com.dennis.movie_catalog.model.Movie;
import com.dennis.movie_catalog.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    private MovieInfoClient movieInfoClient;

    @Autowired
    private MovieRatingClient movieRatingClient;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        List<String> movieIds = Arrays.asList("1", "2", "3");

        return movieIds.stream().map(movieId -> {
            Movie movie = movieInfoClient.getMovieInfo(movieId);
            Rating rating = movieRatingClient.getRating(movieId);
            return new CatalogItem("movie name " + movie.getName(), "Movie ID: " + movie.getId(), rating.getRating());
        }).collect(Collectors.toList());
    }
}
