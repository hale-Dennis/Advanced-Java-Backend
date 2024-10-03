package com.dennis.movie_catalog.clients;

import com.dennis.movie_catalog.model.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "movie-info-service")
public interface MovieInfoClient {
    @GetMapping("/movies/{movieId}")
    Movie getMovieInfo(@PathVariable("movieId") String movieId);
}

