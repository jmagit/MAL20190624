package com.example.catalogo.domain.resources;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.catalogo.application.dtos.ActorDTO;
import com.example.catalogo.application.dtos.FilmEditDTO;
import com.example.catalogo.application.dtos.FilmShortDTO;
import com.example.catalogo.domain.entities.Category;
import com.example.catalogo.domain.entities.Film;
import com.example.catalogo.infraestructures.repositories.FilmRepository;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.InvalidDataException;
import com.example.demo.exceptions.NotFoundException;

@RestController
@RequestMapping(path = "/peliculas")
public class FilmResource {
	@Autowired
	private FilmRepository dao;
	@Autowired
	private Validator validator;

	public Set<ConstraintViolation<@Valid Film>> validate(Film item) {
		return validator.validate(item);
	}

	public boolean isValid(Film item) {
		return validate(item).size() == 0;
	}

	public boolean notIsValid(Film item) {
		return !isValid(item);
	}

	@GetMapping
	public Page<Film> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}
	@GetMapping("/minimo")
	public List<FilmShortDTO> getAll() {
		return dao.findAll().stream()
				.map(item-> FilmShortDTO.from(item))
				.collect(Collectors.toList());
	}
	@GetMapping(path = "/minimo/{id}")
	public FilmShortDTO getOneCorto(@PathVariable int id) throws Exception {
		Optional<Film> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return FilmShortDTO.from(rslt.get());
	}

	@GetMapping(path = "/{id}")
	public FilmEditDTO getOne(@PathVariable int id) throws Exception {
		Optional<Film> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return FilmEditDTO.from(rslt.get());
	}

	@GetMapping(path = "/{id}/reparto")
	@Transactional
	public List<ActorDTO> getFilms(@PathVariable int id) throws Exception {
		Optional<Film> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get().getFilmActors().stream().map(item -> ActorDTO.from(item.getActor()))
				.collect(Collectors.toList());
	}
	@GetMapping(path = "/{id}/categorias")
	@Transactional
	public List<Category> getCategories(@PathVariable int id) throws Exception {
		Optional<Film> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get().getFilmCategories().stream().map(item -> item.getCategory())
				.collect(Collectors.toList());
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Object> add(@Valid @RequestBody FilmEditDTO item) throws Exception {
		Film rslt = dao.save(FilmEditDTO.from(item));
		if (notIsValid(rslt))
			throw new InvalidDataException("Invalid");
		if (dao.findById(item.getFilmId()).isPresent())
			throw new InvalidDataException("Duplicate key");
		dao.save(rslt);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(rslt.getFilmId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/{id}")
	public FilmEditDTO change(@PathVariable int id, @Valid @RequestBody FilmEditDTO item) throws Exception {
		Film rslt = dao.save(FilmEditDTO.from(item));
		if (notIsValid(rslt))
			throw new InvalidDataException("Invalid");
		if (dao.findById(item.getFilmId()).isPresent())
			throw new InvalidDataException("Missing item");
		if (item.getFilmId() != id)
			throw new BadRequestException("No coinciden los ID");
		dao.save(rslt);
		return FilmEditDTO.from(dao.save(FilmEditDTO.from(item)));
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) throws Exception {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			throw new InvalidDataException("Missing item", e);
		}
	}

	public List<Film> novedades(Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

}
