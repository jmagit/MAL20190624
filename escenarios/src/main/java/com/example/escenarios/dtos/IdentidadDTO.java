package com.example.escenarios.dtos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Component
@ApiModel(value = "Identidad del Servicio", description = "Información completa de la personas")
public class IdentidadDTO {
	@Value("${spring.application.name}")
	private String nombre;
	@Value("${server.port}")
	@ApiModelProperty(value = "Puerto de la instancia actual del servidor", required = true)
	private String puerto;
	public String getNombre() {
		return nombre;
	}
	public String getPuerto() {
		return puerto;
	}
	
}
