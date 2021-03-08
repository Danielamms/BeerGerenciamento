package com.digital.gerenciadorCervejas;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public class QuantityDTO {

	    @NotNull
	    @Max(100)
	    private Integer quantity;
	    
	    
	    public static QuantityDTO create(Beer beer) {
	    	ModelMapper modelMapper = new ModelMapper();
	    	return modelMapper.map(beer, QuantityDTO.class);
	    }
	    
	    
	    
	    
	    
	    
	    
	}
	
	
	
	
	
	
	
	

