package com.example.domain.entities;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("personas")
@Valid
public class Persona {
	@Id
	String idPersona;
	@NotBlank
	@Size(max = 50)
	String nombre;
	@Size(min = 2)
	String apellido;
	@Min(16) @Max(67)
	Integer edad;
	List<String> telefono = new ArrayList<String>();
	@Valid
	Direccion direccion;
}
