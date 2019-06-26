package com.example.cliente.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ActorDTO implements Serializable {
	  Integer actorId;
	  String firstName;
	  String lastName;

}
