package com.mimesa.restaurante.repository;

import com.mimesa.restaurante.model.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservacionRepository extends JpaRepository<Reservacion, Integer> {

    @Query("SELECT r FROM Reservacion r ORDER BY r.fecha DESC, r.hora DESC")
    List<Reservacion> listarOrdenadas();

    @Query("SELECT r FROM Reservacion r WHERE r.estado.idEstadoReservacion = :idEstado "
            + "ORDER BY r.fecha DESC, r.hora DESC")
    List<Reservacion> buscarPorEstado(@Param("idEstado") Integer idEstado);

    // Cuenta reservaciones de la misma mesa, a la misma fecha y hora, que no esten canceladas.
    // Al editar se excluye la propia reservacion.
    @Query("SELECT COUNT(r) FROM Reservacion r "
            + "WHERE r.mesa.idMesa = :idMesa AND r.fecha = :fecha AND r.hora = :hora "
            + "AND r.estado.nombre <> 'Cancelada' "
            + "AND (:idReservacion IS NULL OR r.idReservacion <> :idReservacion)")
    long contarSolapadas(@Param("idMesa") Integer idMesa,
                         @Param("fecha") LocalDate fecha,
                         @Param("hora") LocalTime hora,
                         @Param("idReservacion") Integer idReservacion);
}