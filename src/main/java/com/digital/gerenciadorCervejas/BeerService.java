package com.digital.gerenciadorCervejas;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class BeerService {

	@Autowired
	private BeerRepository beerRepository;
	private BeerMapper beerMapper = BeerMapper.INSTANCE;

	// metodo service listAll que usa o metodo repositorio findAll
	// get beer

	public List<BeerDTO> listAll() {
		return beerRepository.findAll().stream().map(BeerDTO::create).collect(Collectors.toList());

	}

	// Puxa uma cerveja pelo id
	// certifica primeiro se ela existe

	public Optional<BeerDTO> findCervejaById(Long id) {
		return beerRepository.findById(id).map(BeerDTO::create);
	}

	// Puxa a cerveja pelo nome
	public Optional<BeerDTO> findByName(String name) {
		return beerRepository.findByName(name).map(BeerDTO::create);

	}

	 public BeerDTO createBeer(BeerDTO beerDTO) throws BeerAlreadyRegisteredException {
	        verifyIfIsAlreadyRegistered(beerDTO.getName());
	        Beer beer = beerMapper.toModel(beerDTO);
	        Beer savedBeer = beerRepository.save(beer);
	        return beerMapper.toDTO(savedBeer);
	    }

	 
	 private void verifyIfIsAlreadyRegistered(String name) throws BeerAlreadyRegisteredException {
	        Optional<Beer> optSavedBeer = beerRepository.findByName(name);
	        if (optSavedBeer.isPresent()) {
	            throw new BeerAlreadyRegisteredException(name);
	        }
	 }
	 
	 
	 
	 
	public boolean deleteById(Long id) {
		if (findCervejaById(id).isPresent()){
			beerRepository.deleteById(id);
			return true;
		}
		return false;
		
	}


	
		public BeerDTO increment(Long id, int quantityToIncrement) throws BeerNotFoundException, BeerStockExceededException {
	        Beer beerToIncrementStock = verifyIfExists(id);
	        int quantityAfterIncrement = quantityToIncrement + beerToIncrementStock.getQuantity();
	        if (quantityAfterIncrement <= beerToIncrementStock.getMax()) {
	            beerToIncrementStock.setQuantity(beerToIncrementStock.getQuantity() + quantityToIncrement);
	            Beer incrementedBeerStock = beerRepository.save(beerToIncrementStock);
	            return beerMapper.toDTO(incrementedBeerStock);
	        }
	        throw new BeerStockExceededException(id, quantityToIncrement);
	    }

		private Beer verifyIfExists(Long id) {
			// TODO Auto-generated method stub
			return null;
		}
	}

