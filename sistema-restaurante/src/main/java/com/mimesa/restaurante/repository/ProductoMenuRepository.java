package com.mimesa.restaurante.repository;

import com.mimesa.restaurante.model.ProductoMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoMenuRepository extends JpaRepository<ProductoMenu, Integer> {
}