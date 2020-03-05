package com.htecweb.winestore.resource;

import com.htecweb.winestore.model.Wine;
import com.htecweb.winestore.service.WineService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/wines")
public class WinesResource {

    private static final Logger LOGGER = LogManager.getLogger(WinesResource.class);

    @Autowired
    private WineService wineService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
     public Wine create(@Valid @RequestBody Wine wine) {
        LOGGER.info("create wines");
        return wineService.save(wine);
    }

    @GetMapping
    public List<Wine> all() {
        LOGGER.info("find all");
        return wineService.findAll();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody Wine wine) {
        wine.setId(id);
        wineService.save(wine);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        wineService.delete(id);
    }

}
