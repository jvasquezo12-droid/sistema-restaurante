package com.mimesa.restaurante.repository;

import com.mimesa.restaurante.model.ProductoMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductoMenuRepository extends JpaRepository<ProductoMenu, Integer> {

    @Query("SELECT p FROM ProductoMenu p WHERE :categoriaId IS NULL OR p.categoria.idCategoria = :categoriaId")
    List<ProductoMenu> buscarPorCategoria(@Param("categoriaId") Integer categoriaId);
}