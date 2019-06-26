package com.example.escenarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.escenarios.dtos.IdentidadDTO;
import com.example.escenarios.dtos.Message;
import com.example.escenarios.servicios.StoreMessages;

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
}
