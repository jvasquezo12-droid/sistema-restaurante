package com.mimesa.restaurante.repository;

import com.mimesa.restaurante.model.ProductoMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoMenuRepository extends JpaRepository<ProductoMenu, Long> {

    List<ProductoMenu> findByNombreContainingIgnoreCase(String nombre);

    List<ProductoMenu> findByCategoria_IdCategoria(Long idCategoria);
}