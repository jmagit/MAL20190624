package com.example.cliente.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
