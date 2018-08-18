package com.htecweb.beerstore.service;

import com.htecweb.beerstore.model.Beer;
import com.htecweb.beerstore.repository.Beers;
import com.htecweb.beerstore.service.exception.BeerAlreadyExistException;
import com.htecweb.beerstore.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeerService {

    private Beers beers;

    public BeerService(@Autowired Beers beers) {
        this.beers = beers;
    }

    public Beer save(final Beer beer) {
        verifyIfBeerExists(beer);
        return beers.save(beer);
    }

    private void verifyIfBeerExists(final Beer beer) {
        Optional<Beer> beerByNameAndType = beers.findByNameAndType(beer.getName(), beer.getType());

        if (beerByNameAndType.isPresent() && (beer.isNew()
            || isUpdatingToDifferentBeer(beer, beerByNameAndType))) {
            throw new BeerAlreadyExistException();
        }
    }

    private boolean isUpdatingToDifferentBeer(Beer beer, Optional<Beer> beerByNameAndType) {
        return beer.alreadyExist() && !beerByNameAndType.get().equals(beer);
    }

    public List<Beer> findAll() {
        return beers.findAll();
    }

    public void delete(Long id) {
        final Optional<Beer> beerById = beers.findById(id);

        final Beer beerToDelete = beerById.orElseThrow(EntityNotFoundException::new);
        beers.delete(beerToDelete);
    }
}
