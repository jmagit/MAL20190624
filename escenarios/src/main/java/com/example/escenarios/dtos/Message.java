package com.example.escenarios.dtos;

import java.util.Date;

import lombok.Value;

@Value
public class Message {
	private String msg;
	private Date enviado;
	private Date recibido = new Date();
}
