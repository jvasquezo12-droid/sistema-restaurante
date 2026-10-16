package com.mimesa.restaurante.service;

import com.mimesa.restaurante.model.ProductoMenu;
import com.mimesa.restaurante.repository.ProductoMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoMenuService {

    @Autowired
    private ProductoMenuRepository productoMenuRepository;

    public List<ProductoMenu> listarTodos() {
        return productoMenuRepository.findAll();
    }

    public ProductoMenu buscarPorId(Integer id) {
        return productoMenuRepository.findById(id).orElse(null);
    }

    public ProductoMenu guardar(ProductoMenu producto) {
        return productoMenuRepository.save(producto);
    }

    public void eliminar(Integer id) {
        productoMenuRepository.deleteById(id);
    }

    public List<ProductoMenu> buscarPorCategoria(Integer categoriaId) {
        return productoMenuRepository.buscarPorCategoria(categoriaId);
    }
}