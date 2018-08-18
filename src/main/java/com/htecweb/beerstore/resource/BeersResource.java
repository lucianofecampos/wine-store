package com.htecweb.beerstore.resource;

import com.htecweb.beerstore.model.Beer;
import com.htecweb.beerstore.service.BeerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/beers")
public class BeersResource {

    private static final Logger LOGGER = LogManager.getLogger(BeersResource.class);

    @Autowired
    private BeerService beerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
     public Beer create(@Valid @RequestBody Beer beer) {
        LOGGER.info("create beers");
        return beerService.save(beer);
    }

    @GetMapping
    public List<Beer> all() {
        LOGGER.info("find all");
        return beerService.findAll();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody Beer beer) {
        beer.setId(id);
        beerService.save(beer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        beerService.delete(id);
    }

}
