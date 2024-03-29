package com.example.cliente.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.amqp.dtos.MessageDTO;
import com.example.cliente.dto.ActorDTO;
import com.example.cliente.dto.FilmDTO;
import com.example.cliente.proxies.ActoresProxy;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping(path = "/demos")
public class DemoResource {
	@GetMapping(path="/saluda") 
	public String getSaludo(@RequestParam(required = false, defaultValue = "mundo") String nom) {
		return "Hola " + nom;
	}
	@GetMapping(path="/{nom}") 
	public String getSaludo2(@PathVariable String nom) {
		return "Hola " + nom;
	}
	
	@Autowired
	private AmqpTemplate amqp;
	
	@PutMapping(path="/envio/{nom}") 
	public String send(@PathVariable String nom) {
		MessageDTO msg = new MessageDTO("Hola " + nom);
		amqp.convertAndSend("mi-cola", msg);
		return msg.getMsg();
	}
	
	@Autowired
	private RestTemplate srv;
	
	@GetMapping(path="/peliculas") 
	@HystrixCommand(fallbackMethod = "getPeliculasFallback")
	public List<FilmDTO> getPeliculas() {
		ResponseEntity<List<FilmDTO>> response = srv.exchange("http://ESCENARIOS-SERVICE/peliculas", 
				HttpMethod.GET,
				HttpEntity.EMPTY, 
				new ParameterizedTypeReference<List<FilmDTO>>() {
				});
		return response.getBody();
	}
	private List<FilmDTO> getPeliculasFallback() {
		return new ArrayList<FilmDTO>();
	}

	@GetMapping(path="/peliculas/{id}") 
	public FilmDTO getPeliculas(@PathVariable int id) {
		return srv.getForObject("http://CATALOGO-SERVICE/peliculas/{id}?mode=short", 
				FilmDTO.class, id);
	}
	
	@GetMapping(path = "/balancea") 
	public String getInstancia() {
		return srv.getForObject("http://ESCENARIOS-SERVICE/mensajes", 
				String.class);
	}
	
	@Autowired
	private ActoresProxy proxy;
	
	@GetMapping(path = "/actor") 
	public List<ActorDTO> getdemoFeing() {
		proxy.add(new ActorDTO(0, "Otro", "Mas"));
		proxy.delete(225);
		return proxy.getAll().getContent();
	}

	@Value("${mi.valor}")
	private String configVal;
	
	@GetMapping(path="/valor") 
	public String getValor() {
		return configVal;
	}

	
}
