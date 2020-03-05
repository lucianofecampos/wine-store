package com.htecweb.winestore.repository;

import com.htecweb.winestore.model.Wine;
import com.htecweb.winestore.model.WineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Wines extends JpaRepository<Wine, Long> {

    Optional<Wine> findByNameAndType(String name, WineType wineType);
}
