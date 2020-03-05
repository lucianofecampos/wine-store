package com.htecweb.winestore.service;

import com.htecweb.winestore.model.Wine;
import com.htecweb.winestore.model.WineType;
import com.htecweb.winestore.repository.Wines;
import com.htecweb.winestore.service.exception.WineAlreadyExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.when;

public class WineServiceTest {

    private WineService wineService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        wineService = new WineService(winesMocked);
    }

    @Mock
    private Wines winesMocked;

    @Test(expected = WineAlreadyExistException.class)
    public void should_deny_creation_of_wine_that_exist() {
        final Wine wineInDatabase = new Wine(10L, "Heineken", WineType.LARGER, new BigDecimal("355"));
        when(winesMocked.findByNameAndType("Heineken", WineType.LARGER)).thenReturn(Optional.of(wineInDatabase));

        final Wine newWine = new Wine(null, "Heineken", WineType.LARGER, new BigDecimal("355"));

        wineService.save(newWine);
    }

    @Test
    public void should_create_new_wine() {
        when(winesMocked.findByNameAndType("Heineken", WineType.LARGER)).thenReturn(Optional.ofNullable(null));

        final Wine newWine = new Wine(null, "Heineken", WineType.LARGER, new BigDecimal("600"));
        final Wine wineMocked = new Wine(10L, "Heineken", WineType.LARGER, new BigDecimal("600"));

        when(winesMocked.save(newWine)).thenReturn(wineMocked);

        final Wine wineSaved = wineService.save(newWine);

        assertThat(wineSaved.getId(), equalTo(10L));
        assertThat(wineSaved.getName(), equalTo("Heineken"));
        assertThat(wineSaved.getType(), equalTo(WineType.LARGER));
    }

    @Test
    public void should_update_wine_volume() {
        final Wine wineInDB = new Wine(10L, "Heineken", WineType.LARGER, new BigDecimal("600"));
        when(winesMocked.findByNameAndType("Heineken", WineType.LARGER)).thenReturn(Optional.of(wineInDB));

        final Wine winetoUpdate = new Wine(10L, "Heineken", WineType.LARGER, new BigDecimal("500"));
        final Wine wineMocked = new Wine(10L, "Heineken", WineType.LARGER, new BigDecimal("600"));
        when(winesMocked.save(winetoUpdate)).thenReturn(wineMocked);

        final Wine wineUpdated = wineService.save(winetoUpdate);

        assertThat(wineUpdated.getId(), equalTo(10L));
        assertThat(wineUpdated.getName(), equalTo("Heineken"));
        assertThat(wineUpdated.getType(), equalTo(WineType.LARGER));
        assertThat(wineUpdated.getVolume(), equalTo(new BigDecimal("600")));
    }

    @Test(expected = WineAlreadyExistException.class)
    public void should_deny_update_of_an_existing_wine_that_already_exists() {
        final Wine wineInDatabase = new Wine(10L, "Heineken", WineType.LARGER, new BigDecimal("355"));
        when(winesMocked.findByNameAndType("Heineken", WineType.LARGER)).thenReturn(Optional.of(wineInDatabase));

        final Wine wineToUpdate = new Wine(5L, "Heineken", WineType.LARGER, new BigDecimal("355"));

        wineService.save(wineToUpdate);
    }
}
