package com.htecweb.beerstore.service;

import com.htecweb.beerstore.model.Beer;
import com.htecweb.beerstore.model.BeerType;
import com.htecweb.beerstore.repository.Beers;
import com.htecweb.beerstore.service.exception.BeerAlreadyExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.when;

public class BeerServiceTest {

    private BeerService beerService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        beerService = new BeerService(beersMocked);
    }

    @Mock
    private Beers beersMocked;

    @Test(expected = BeerAlreadyExistException.class)
    public void should_deny_creation_of_beer_that_exist() {
        final Beer beerInDatabase = new Beer(10L, "Heineken", BeerType.LARGER, new BigDecimal("355"));
        when(beersMocked.findByNameAndType("Heineken", BeerType.LARGER)).thenReturn(Optional.of(beerInDatabase));

        final Beer newBeer = new Beer(null, "Heineken", BeerType.LARGER, new BigDecimal("355"));

        beerService.save(newBeer);
    }

    @Test
    public void should_create_new_beer() {
        when(beersMocked.findByNameAndType("Heineken", BeerType.LARGER)).thenReturn(Optional.ofNullable(null));

        final Beer newBeer = new Beer(null, "Heineken", BeerType.LARGER, new BigDecimal("600"));
        final Beer beerMocked = new Beer(10L, "Heineken", BeerType.LARGER, new BigDecimal("600"));

        when(beersMocked.save(newBeer)).thenReturn(beerMocked);

        final Beer beerSaved = beerService.save(newBeer);

        assertThat(beerSaved.getId(), equalTo(10L));
        assertThat(beerSaved.getName(), equalTo("Heineken"));
        assertThat(beerSaved.getType(), equalTo(BeerType.LARGER));
    }

    @Test
    public void should_update_beer_volume() {
        final Beer beerInDB = new Beer(10L, "Heineken", BeerType.LARGER, new BigDecimal("600"));
        when(beersMocked.findByNameAndType("Heineken", BeerType.LARGER)).thenReturn(Optional.of(beerInDB));

        final Beer beertoUpdate = new Beer(10L, "Heineken", BeerType.LARGER, new BigDecimal("500"));
        final Beer beerMocked = new Beer(10L, "Heineken", BeerType.LARGER, new BigDecimal("600"));
        when(beersMocked.save(beertoUpdate)).thenReturn(beerMocked);

        final Beer beerUpdated = beerService.save(beertoUpdate);

        assertThat(beerUpdated.getId(), equalTo(10L));
        assertThat(beerUpdated.getName(), equalTo("Heineken"));
        assertThat(beerUpdated.getType(), equalTo(BeerType.LARGER));
        assertThat(beerUpdated.getVolume(), equalTo(new BigDecimal("600")));
    }

    @Test(expected = BeerAlreadyExistException.class)
    public void should_deny_update_of_an_existing_beer_that_already_exists() {
        final Beer beerInDatabase = new Beer(10L, "Heineken", BeerType.LARGER, new BigDecimal("355"));
        when(beersMocked.findByNameAndType("Heineken", BeerType.LARGER)).thenReturn(Optional.of(beerInDatabase));

        final Beer beerToUpdate = new Beer(5L, "Heineken", BeerType.LARGER, new BigDecimal("355"));

        beerService.save(beerToUpdate);
    }
}
