package com.example.escenarios.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.escenarios.dtos.Message;

@Service
public class StoreMessages {
	private List<Message> lst = new ArrayList<Message>();
	
	public void add(String msg, Date enviado) {
		lst.add(new Message(msg, enviado));
	}
	public List<Message> get() {
		return lst; // Clonar
	}
}
