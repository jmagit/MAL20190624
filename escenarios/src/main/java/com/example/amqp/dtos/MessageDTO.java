package com.example.amqp.dtos;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.Value;

@Data
public class MessageDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String msg;
	private Date enviado;
}
