package com.mimesa.restaurante.repository;

import com.mimesa.restaurante.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {

    // Método para buscar mesa por número
    Optional<Mesa> findByNumero(Integer numero);

    // Método corregido: utiliza 'estado' en lugar de 'estadoMesa'
    List<Mesa> findByEstado_IdEstadoMesa(Integer idEstadoMesa);
}