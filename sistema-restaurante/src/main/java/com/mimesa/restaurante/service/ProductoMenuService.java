package com.mimesa.restaurante.service;

import com.mimesa.restaurante.model.ProductoMenu;
import com.mimesa.restaurante.repository.ProductoMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoMenuService {

    @Autowired
    private ProductoMenuRepository repositorio;

    public List<ProductoMenu> listarTodos() {
        return repositorio.findAll();
    }

    public ProductoMenu buscarPorId(Integer id) {
        return repositorio.findById(id).orElse(null);
    }

    public ProductoMenu guardar(ProductoMenu producto) {
        return repositorio.save(producto);
    }

    public void eliminar(Integer id) {
        repositorio.deleteById(id);
    }
}