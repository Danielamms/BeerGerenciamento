package com.digital.gerenciadorCervejas;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class BeerNotFoundException extends Exception {

	
	public BeerNotFoundException(Long id) {
	super(String.format("Beer with id %s not found in the System.", id));
}
	
	
	
}
