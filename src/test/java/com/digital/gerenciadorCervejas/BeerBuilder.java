package com.digital.gerenciadorCervejas;

import org.modelmapper.ModelMapper;

import lombok.Builder;

@Builder
public class BeerDTOBuilder {

	
	   @Builder.Default
	    private Long id = 1L;

	    @Builder.Default
	    private String name = "Brahma";

	    @Builder.Default
	    private String brand = "Ambev";

	    @Builder.Default
	    private int max = 50;

	    @Builder.Default
	    private int quantity = 10;

	    @Builder.Default
	    private BeerType type = BeerType.LAGER;

	   public static BeerDTO create(BeerDTOBuilder beerDTOBuilder) {
	    ModelMapper modelMapper = new ModelMapper();
	    return modelMapper.map(beerDTOBuilder, BeerDTO.class);}
	    
	   //para trasnformar em Buider em DTO
	public BeerDTO toBeerDTO() {
		return new BeerDTO(id,
				name,
				brand,
				max,
				quantity,
				type);
	}
	}

	


	    
	    
	    
	    
	    
	    
}

	
	  

	

