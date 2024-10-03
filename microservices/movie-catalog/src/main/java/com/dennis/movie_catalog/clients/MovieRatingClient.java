package com.dennis.movie_catalog.clients;

import com.dennis.movie_catalog.model.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "movie-rating-service")
public interface MovieRatingClient {
    @GetMapping("/ratings/{movieId}")
    Rating getRating(@PathVariable("movieId") String movieId);
}
