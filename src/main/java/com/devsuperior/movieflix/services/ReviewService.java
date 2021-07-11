package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;

@Service
public class ReviewService {

	private AuthService authService;
	
	private ReviewRepository repository;
	private MovieRepository movieRepository;	

	@Autowired
	public ReviewService(AuthService authService, 
			ReviewRepository repository, MovieRepository movieRepository) {
		this.authService = authService;
		this.repository = repository;
		this.movieRepository = movieRepository;
	}
	
	@Transactional(readOnly = true)
	public Page<ReviewDTO> findAll(Pageable pageable){
		Page<Review> list = repository.findAll(pageable);
		return list.map(review -> new ReviewDTO(review));
	}
	
	@PreAuthorize("hasRole('MEMBER')")
	@Transactional
	public ReviewDTO saveReview(ReviewDTO dto) {
		Movie movie = movieRepository.getOne(dto.getMovieId());
		
		Review review = new Review();
		review.setText(dto.getText());
		review.setUser(authService.authenticated());
		review.setMovie(movie);
		
		review = repository.save(review);
		return new ReviewDTO(review);
	}
}
