package com.mimesa.restaurante.service;

import com.mimesa.restaurante.model.CategoriaMenu;
import com.mimesa.restaurante.repository.CategoriaMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaMenuService {

    @Autowired
    private CategoriaMenuRepository repositorio;

    public List<CategoriaMenu> listarTodos() {
        return repositorio.findAll();
    }

    public CategoriaMenu buscarPorId(Integer id) {
        return repositorio.findById(id).orElse(null);
    }

    public CategoriaMenu guardar(CategoriaMenu categoria) {
        return repositorio.save(categoria);
    }

    public void eliminar(Integer id) {
        repositorio.deleteById(id);
    }
}