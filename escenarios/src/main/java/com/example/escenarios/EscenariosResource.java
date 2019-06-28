package com.example.escenarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.escenarios.dtos.FilmDTO;
import com.example.escenarios.dtos.IdentidadDTO;
import com.example.escenarios.dtos.Message;
import com.example.escenarios.servicios.StoreMessages;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Escenarios conceptuales", description = "API que permite el mantenimiento de personas")
public class EscenariosResource {
	@Autowired
	private IdentidadDTO identidadDTO;
	
	@GetMapping(path = "/identificacion")
	@ApiOperation(value = "Instancia que se está ejecutando", notes = "Devuelve una persona por su identificador" )
	@ApiResponses({
		@ApiResponse(code = 200, message = "Servicio encontrado"),
		@ApiResponse(code = 404, message = "Servicio no encontrado")
	})
	public IdentidadDTO getIdentificacion() {
		return identidadDTO;
	}

	@Autowired
	StoreMessages lista;

	@GetMapping("/mensajes")
	public List<Message> getMensajes() {
		return lista.get();
	}
	
	@Autowired
	private RestTemplate srv;
	
	@GetMapping(path="/peliculas") 
	@HystrixCommand(fallbackMethod = "getPeliculasFallback")
	public List<FilmDTO> getPeliculas() throws InterruptedException {
		Thread.sleep((long)(Math.random() * 1000));
		ResponseEntity<List<FilmDTO>> response = srv.exchange("http://CATALOGO-SERVICE/peliculas?mode=short", 
				HttpMethod.GET,
				HttpEntity.EMPTY, 
				new ParameterizedTypeReference<List<FilmDTO>>() {
				});
		return response.getBody();
	}
	private List<FilmDTO> getPeliculasFallback() {
		return new ArrayList<FilmDTO>();
	}

}
