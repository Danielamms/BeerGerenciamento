package com.digital.gerenciadorCervejas;

public class BeerAlreadyRegisteredException extends Exception {

	 public BeerAlreadyRegisteredException(String name) {
	        super(String.format("Beer with name %s already registered in the system.", name));
	    }

}
