package com.example.catalogo.application.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.catalogo.domain.entities.Actor;
import com.example.catalogo.domain.entities.Category;
import com.example.catalogo.domain.entities.Film;
import com.example.catalogo.domain.entities.Language;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FilmEditDTO {
	private int filmId;
	private String description;
	private int length;
	private String rating;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	private Short releaseYear;
	private byte rentalDuration;
	private BigDecimal rentalRate;
	private BigDecimal replacementCost;
	private String title;
	private Byte languageId;
	private Byte languageVOId;
	private List<Integer> filmActors;
	private List<Byte> filmCategories;
	
	public static FilmEditDTO from(Film source) {
		return new FilmEditDTO(
				source.getFilmId(), 
				source.getDescription(),
				source.getLength(),
				source.getRating(),
				source.getReleaseYear(),
				source.getRentalDuration(),
				source.getRentalRate(),
				source.getReplacementCost(),
				source.getTitle(),
				source.getLanguage() == null ? null : source.getLanguage().getLanguageId(),
				source.getLanguageVO() == null ? null : source.getLanguageVO().getLanguageId(),
				source.getFilmActors().stream().map(item -> item.getActor().getActorId())
					.collect(Collectors.toList()),
				source.getFilmCategories().stream().map(item -> item.getCategory().getCategoryId())
					.collect(Collectors.toList())
				);
	}
	public static Film from(FilmEditDTO source) {
		Film rslt = new Film(
				source.getFilmId(), 
				source.getDescription(),
				source.getLength(),
				source.getRating(),
				source.getReleaseYear(),
				source.getRentalDuration(),
				source.getRentalRate(),
				source.getReplacementCost(),
				source.getTitle(),
				source.getLanguageId() == null ? null : new Language(source.getLanguageId()),
				source.getLanguageVOId() == null ? null : new Language(source.getLanguageVOId())
				);
		source.getFilmActors().stream()
			.forEach(item -> rslt.addFilmActor(new Actor(item)));
		source.getFilmCategories().stream()
			.forEach(item -> rslt.addFilmCategory(new Category(item)));
		return rslt;
	}

}
