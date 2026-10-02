package com.mimesa.restaurante.repository;

import com.mimesa.restaurante.model.CategoriaMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaMenuRepository extends JpaRepository<CategoriaMenu, Integer> {
}