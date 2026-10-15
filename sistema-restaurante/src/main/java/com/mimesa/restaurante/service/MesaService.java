package com.mimesa.restaurante.service;

import com.mimesa.restaurante.model.Mesa;
import com.mimesa.restaurante.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaService {

    @Autowired
    private MesaRepository repositorio;

    public List<Mesa> listarTodos() {
        return repositorio.findAll();
    }

    public Mesa buscarPorId(Integer id) {
        return repositorio.findById(id).orElse(null);
    }

    public Mesa guardar(Mesa mesa) {
        return repositorio.save(mesa);
    }

    public void eliminar(Integer id) {
        repositorio.deleteById(id);
    }

    // Devuelve true si otra mesa distinta ya usa ese numero
    public boolean numeroEnUso(Integer numero, Integer idMesaActual) {
        return repositorio.findByNumero(numero)
                .map(m -> !m.getIdMesa().equals(idMesaActual))
                .orElse(false);
    }
}
