package com.digital.gerenciadorCervejas;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.digital.gerenciadorCervejas.Beer;
import com.digital.gerenciadorCervejas.BeerAlreadyRegisteredException;
import com.digital.gerenciadorCervejas.BeerDTO;
import com.digital.gerenciadorCervejas.BeerMapper;
import com.digital.gerenciadorCervejas.BeerNotFoundException;
import com.digital.gerenciadorCervejas.BeerRepository;
import com.digital.gerenciadorCervejas.BeerService;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

	// Vou fazer um Mock do Repositorio

	private static final Long INVALID_BEER_ID = null;
	@Mock
	private BeerRepository mockBeerRepository;
	private BeerMapper mockBeerMapper = BeerMapper.INSTANCE;

	// Vou injetar o repositorio mockado na classe service
	@InjectMocks
	private BeerService beerService;

	

	 @Test
	    void whenBeerInformedThenItShouldBeCreated() throws BeerAlreadyRegisteredException {
	        // given
	        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
	        Beer expectedSavedBeer = mockBeerMapper.toModel(expectedBeerDTO);

	        // when
	        when(mockBeerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.empty());
	        when(mockBeerRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);

	        //then
	        BeerDTO createdBeerDTO = beerService.createBeer(expectedBeerDTO);

	        assertThat(createdBeerDTO.getId(), is(equalTo(expectedBeerDTO.getId())));
	        assertThat(createdBeerDTO.getName(), is(equalTo(expectedBeerDTO.getName())));
	        assertThat(createdBeerDTO.getQuantity(), is(equalTo(expectedBeerDTO.getQuantity())));
	    }

	    @Test
	    void whenAlreadyRegisteredBeerInformedThenAnExceptionShouldBeThrown() {
	        // given
	        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
	        Beer duplicatedBeer = mockBeerMapper.toModel(expectedBeerDTO);

	        // when
	        when(mockBeerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.of(duplicatedBeer));

	        // then
	        assertThrows(BeerAlreadyRegisteredException.class, () -> beerService.createBeer(expectedBeerDTO));
	    }

	    @Test
	    void whenValidBeerNameIsGivenThenReturnABeer() throws BeerNotFoundException {
	        // transformando a cerveja builder em cerveja dto esperada
	        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
	        Beer expectedFoundBeer = mockBeerMapper.toModel(expectedFoundBeerDTO);

	        // when
	        when(mockBeerRepository.findByName(expectedFoundBeer.getName())).thenReturn(Optional.of(expectedFoundBeer));

	        // then
	        Optional<BeerDTO> foundBeerDTO = beerService.findByName(expectedFoundBeerDTO.getName());

	        assertThat(foundBeerDTO, is(equalTo(expectedFoundBeerDTO)));
	    }

	    @Test
	    void whenNotRegisteredBeerNameIsGivenThenThrowAnException() {
	        // given
	        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

	        // when
	        when(mockBeerRepository.findByName(expectedFoundBeerDTO.getName())).thenReturn(Optional.empty());

	        // then
	        assertThrows(BeerNotFoundException.class, () -> beerService.findByName(expectedFoundBeerDTO.getName()));
	    }

	    @Test
	    void whenListBeerIsCalledThenReturnAListOfBeers() {
	        // given
	        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
	        Beer expectedFoundBeer = mockBeerMapper.toModel(expectedFoundBeerDTO);

	        //when
	        when(mockBeerRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundBeer));

	        //then
	        List<BeerDTO> foundListBeersDTO = beerService.listAll();

	       
	        assertThat(foundListBeersDTO.get(0), is(equalTo(expectedFoundBeerDTO)));
	    }

	 

	

	    @Test
	    void whenExclusionIsCalledWithValidIdThenABeerShouldBeDeleted() throws BeerNotFoundException{
	        // given
	        BeerDTO expectedDeletedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
	        Beer expectedDeletedBeer = mockBeerMapper.toModel(expectedDeletedBeerDTO);

	        // when
	        when(mockBeerRepository.findById(expectedDeletedBeerDTO.getId())).thenReturn(Optional.of(expectedDeletedBeer));
	        doNothing().when(mockBeerRepository).deleteById(expectedDeletedBeerDTO.getId());

	        // then
	        beerService.deleteById(expectedDeletedBeerDTO.getId());

	        verify(mockBeerRepository, times(1)).findById(expectedDeletedBeerDTO.getId());
	        verify(mockBeerRepository, times(1)).deleteById(expectedDeletedBeerDTO.getId());
	    }

	    @Test
	    void whenIncrementIsCalledThenIncrementBeerStock() throws BeerNotFoundException, BeerStockExceededException {
	        //given
	        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
	        Beer expectedBeer = mockBeerMapper.toModel(expectedBeerDTO);

	        //when
	        when(mockBeerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));
	        when(mockBeerRepository.save(expectedBeer)).thenReturn(expectedBeer);

	        int quantityToIncrement = 10;
	        int expectedQuantityAfterIncrement = expectedBeerDTO.getQuantity() + quantityToIncrement;

	        // then
	        BeerDTO incrementedBeerDTO = beerService.increment(expectedBeerDTO.getId(), quantityToIncrement);

	        assertThat(expectedQuantityAfterIncrement, equalTo(incrementedBeerDTO.getQuantity()));
	       
	    }

	    @Test
	    void whenIncrementIsGreatherThanMaxThenThrowException() {
	        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
	        Beer expectedBeer = mockBeerMapper.toModel(expectedBeerDTO);

	        when(mockBeerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));

	        int quantityToIncrement = 80;
	        assertThrows(BeerStockExceededException.class, () -> beerService.increment(expectedBeerDTO.getId(), quantityToIncrement));
	    }

	    @Test
	    void whenIncrementAfterSumIsGreatherThanMaxThenThrowException() {
	        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
	        Beer expectedBeer = mockBeerMapper.toModel(expectedBeerDTO);

	        when(mockBeerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));

	        int quantityToIncrement = 45;
	        assertThrows(BeerStockExceededException.class, () -> beerService.increment(expectedBeerDTO.getId(), quantityToIncrement));
	    }

	    @Test
	    void whenIncrementIsCalledWithInvalidIdThenThrowException() {
	        int quantityToIncrement = 10;

	        when(mockBeerRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());

	        assertThrows(BeerNotFoundException.class, () -> beerService.increment(INVALID_BEER_ID, quantityToIncrement));
	    }
}