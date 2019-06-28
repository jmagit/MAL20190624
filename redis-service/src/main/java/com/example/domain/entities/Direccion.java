package com.example.domain.entities;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Direccion {
	String calle;
	@Size(max = 5)
	String codigoPostal;
	String localidad;
	String provincia;
	String pais;
}
