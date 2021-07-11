package com.devsuperior.movieflix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.MovieDetailDTO;
import com.devsuperior.movieflix.dto.MovieMinDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.MovieService;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

	private MovieService service;
	
	@Autowired
	public MovieController(MovieService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<Page<MovieMinDTO>> findAll(
			@RequestParam(name = "genreId", defaultValue = "0") Long genreId
			,Pageable pageable) {
		Page<MovieMinDTO> dto = service.findAll(genreId, pageable);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieDetailDTO> findById(@PathVariable Long id) {
		MovieDetailDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/{id}/reviews")
	public ResponseEntity<List<ReviewDTO>> findReviewsByMovieId(@PathVariable Long id) {
		List<ReviewDTO> dto = service.findReviewsByMovieId(id);
		return ResponseEntity.ok().body(dto);
	}

}
