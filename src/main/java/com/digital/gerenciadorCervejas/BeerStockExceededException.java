package com.digital.gerenciadorCervejas;

import org.springframework.web.bind.annotation.ResponseStatus;

public class BeerStockExceededException extends Exception{

	 public BeerStockExceededException(Long id, int quantityToIncrement) {
	        super(String.format("Beers with %s ID to increment informed exceeds the max stock capacity: %s", id, quantityToIncrement));
	    }
}
