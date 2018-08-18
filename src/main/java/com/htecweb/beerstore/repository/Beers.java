package com.htecweb.beerstore.repository;

import com.htecweb.beerstore.model.Beer;
import com.htecweb.beerstore.model.BeerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Beers extends JpaRepository<Beer, Long> {

    Optional<Beer> findByNameAndType(String name, BeerType beerType);
}
