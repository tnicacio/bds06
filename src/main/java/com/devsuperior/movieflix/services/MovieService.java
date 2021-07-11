package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.movieflix.repositories.MovieRepository;

@Service
public class MovieService {

	private MovieRepository repository;
	
	@Autowired
	public MovieService(MovieRepository repository) {
		this.repository = repository;
	}
}
