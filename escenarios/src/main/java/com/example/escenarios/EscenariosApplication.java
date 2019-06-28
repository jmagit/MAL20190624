package com.example.escenarios;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

import com.example.amqp.dtos.MessageDTO;
import com.example.escenarios.servicios.StoreMessages;

@EnableCircuitBreaker
@SpringBootApplication
public class EscenariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(EscenariosApplication.class, args);
	}

	@Autowired
	StoreMessages lista;
	
	@RabbitListener(queues = "mi-cola")
	public void recive(MessageDTO inMsg) {
		lista.add(inMsg.getMsg(), inMsg.getEnviado());
		System.out.println("\nMENSAJE RECIBIDO:\n" + inMsg + "\n");
	}


}
