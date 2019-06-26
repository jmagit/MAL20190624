package com.example.cliente.dto;

import java.util.List;

import lombok.Data;

@Data
public class Paginado<T> {
	int number;
	int size;
	int numberOfElements;
	List<T> content;
	boolean hasContent;
}
