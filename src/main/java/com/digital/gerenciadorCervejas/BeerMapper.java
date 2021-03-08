package com.digital.gerenciadorCervejas;

import org.modelmapper.ModelMapper;
import org.mapstruct.factory.Mappers;

public interface BeerMapper {

	
	 BeerMapper INSTANCE = Mappers.getMapper(BeerMapper.class);

	    Beer toModel(BeerDTO beerDTO);

	    BeerDTO toDTO(Beer beer);
	
}



	


