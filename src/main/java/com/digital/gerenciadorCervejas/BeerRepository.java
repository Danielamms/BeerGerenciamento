package com.digital.gerenciadorCervejas;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, Long> {

    Optional<Beer> findByName(String name);
	
	
	

}
