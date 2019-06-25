package com.example.catalogo.application.dtos;

import com.example.catalogo.domain.entities.Film;

import lombok.Value;

@Value
public class FilmShortDTO {
	private int filmId;
	private String title;
	
	public static FilmShortDTO from(Film source) {
		return new FilmShortDTO(source.getFilmId(), source.getTitle());
	}
}
