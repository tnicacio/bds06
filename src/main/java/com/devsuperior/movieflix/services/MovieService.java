package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieDetailDTO;
import com.devsuperior.movieflix.dto.MovieMinDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {
	
	private AuthService authService;

	private MovieRepository repository;
	private GenreRepository genreRepository;
	private ReviewRepository reviewRepository;
	
	@Autowired
	public MovieService(
			AuthService authService,
			MovieRepository repository, 
			GenreRepository genreRepository, 
			ReviewRepository reviewRepository
			) {
		this.authService = authService;
		this.repository = repository;
		this.genreRepository = genreRepository;
		this.reviewRepository = reviewRepository;
	}
	
	@Transactional(readOnly = true)
	public Page<MovieMinDTO> findAll(Long genreId, Pageable pageable){
		Genre genre = (genreId == 0) ?  null : genreRepository.getOne(genreId);
		Page<MovieMinDTO> page = repository.find(genre, pageable);
		return page;
	}
	
	@Transactional(readOnly = true)
	public MovieDetailDTO findById(Long id) {
		authService.authenticated();
		Optional<Movie> obj = repository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
		return new MovieDetailDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<ReviewDTO> findReviewsByMovieId(Long id) {
		authService.authenticated();
		return reviewRepository.findByMovieId(id);
	}

}
