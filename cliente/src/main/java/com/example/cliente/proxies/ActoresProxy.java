package com.example.cliente.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.cliente.dto.ActorDTO;
import com.example.cliente.dto.Paginado;

@FeignClient(name = "CATALOGO-SERVICE") // Eureka
public interface ActoresProxy {
	@GetMapping(path = "/actores?page=10")
	Paginado<ActorDTO> getAll();
	@GetMapping("/actores/{id}")
	ActorDTO get(@PathVariable int id);
	@PostMapping("/actores")
	void add(ActorDTO item);
	@PutMapping("/actores/{id}")
	void update(@PathVariable int id, @RequestBody ActorDTO item);
	@DeleteMapping("/actores/{id}")
	void delete(@PathVariable int id);
}
