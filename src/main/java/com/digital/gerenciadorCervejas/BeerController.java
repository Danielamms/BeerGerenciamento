package com.digital.gerenciadorCervejas;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/beers")
public class BeerController {

	@Autowired
	private BeerService beerService;

	// Listar todas as cervejas

	@GetMapping
	public ResponseEntity<List<BeerDTO>> listBeers() {
		return ResponseEntity.ok(beerService.listAll());
	}

	// Puxa uma cerveja pelo id
	// temos primeiro que checar se ela existe com esse id
	// se existe, retorna a cerveja
	// mesmo que verifyIfExists

	@GetMapping("/{id}")
	public ResponseEntity encontrarPeloId(@PathVariable("id") Long id) {
		Optional<BeerDTO> beer = beerService.findCervejaById(id);

		return beer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

	}

	// Encontrar cerveja pelo nome
	@GetMapping("/{name}")
	public ResponseEntity encontrarCervejaPorNome(@PathVariable("name") String name) throws BeerNotFoundException

	{

		Optional<BeerDTO> beer = beerService.findByName(name);

		return beer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

	}

	// Cadastrar cerveja/antes tem que verificar se ela ja existe
	// verifica pelo nome

	@PostMapping
	public ResponseEntity createBeer(@RequestBody BeerDTO beerDTO) throws BeerAlreadyRegisteredException {
		try {
			BeerDTO b = beerService.createBeer(beerDTO);
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			return ResponseEntity.badRequest().build();

		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteById(@PathVariable("id") Long id) {
		boolean deletado = beerService.deleteById(id);

		return deletado ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();

		
	}
		

	    @PatchMapping("/{id}/increment")
	    public BeerDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws BeerNotFoundException, BeerStockExceededException {
	        return beerService.increment(id, quantityDTO.getQuantity());
	    }
		
		
		
		
		
	}

