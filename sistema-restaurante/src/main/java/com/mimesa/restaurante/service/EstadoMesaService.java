package com.mimesa.restaurante.service;

import com.mimesa.restaurante.model.EstadoMesa;
import com.mimesa.restaurante.repository.EstadoMesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoMesaService {

    @Autowired
    private EstadoMesaRepository repositorio;

    public List<EstadoMesa> listarTodos() {
        return repositorio.findAll();
    }
}
