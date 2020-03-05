package com.htecweb.winestore.service;

import com.htecweb.winestore.model.Wine;
import com.htecweb.winestore.repository.Wines;
import com.htecweb.winestore.service.exception.WineAlreadyExistException;
import com.htecweb.winestore.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WineService {

    private Wines wines;

    public WineService(@Autowired Wines wines) {
        this.wines = wines;
    }

    public Wine save(final Wine wine) {
        verifyIfWineExists(wine);
        return wines.save(wine);
    }

    private void verifyIfWineExists(final Wine wine) {
        Optional<Wine> wineByNameAndType = wines.findByNameAndType(wine.getName(), wine.getType());

        if (wineByNameAndType.isPresent() && (wine.isNew()
            || isUpdatingToDifferentWine(wine, wineByNameAndType))) {
            throw new WineAlreadyExistException();
        }
    }

    private boolean isUpdatingToDifferentWine(Wine wine, Optional<Wine> wineByNameAndType) {
        return wine.alreadyExist() && !wineByNameAndType.get().equals(wine);
    }

    public List<Wine> findAll() {
        return wines.findAll();
    }

    public void delete(Long id) {
        final Optional<Wine> wineById = wines.findById(id);

        final Wine wineToDelete = wineById.orElseThrow(EntityNotFoundException::new);
        wines.delete(wineToDelete);
    }
}
