package com.mimesa.restaurante.service;

import com.mimesa.restaurante.model.EstadoReservacion;
import com.mimesa.restaurante.model.Mesa;
import com.mimesa.restaurante.model.Reservacion;
import com.mimesa.restaurante.repository.EstadoReservacionRepository;
import com.mimesa.restaurante.repository.MesaRepository;
import com.mimesa.restaurante.repository.ReservacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservacionService {

    @Autowired
    private ReservacionRepository reservacionRepositorio;

    @Autowired
    private EstadoReservacionRepository estadoRepositorio;

    @Autowired
    private MesaRepository mesaRepositorio;

    public List<Reservacion> listarTodas() {
        return reservacionRepositorio.listarOrdenadas();
    }

    public List<Reservacion> buscarPorEstado(Integer idEstado) {
        return reservacionRepositorio.buscarPorEstado(idEstado);
    }

    public Reservacion buscarPorId(Integer id) {
        return reservacionRepositorio.findById(id).orElse(null);
    }

    public List<EstadoReservacion> listarEstados() {
        return estadoRepositorio.findAll();
    }

    public Mesa buscarMesa(Integer idMesa) {
        return mesaRepositorio.findById(idMesa).orElse(null);
    }

    // Regla del negocio: una mesa no puede reservarse dos veces en el mismo horario
    public boolean horarioOcupado(Integer idMesa, LocalDate fecha, LocalTime hora, Integer idReservacion) {
        return reservacionRepositorio.contarSolapadas(idMesa, fecha, hora, idReservacion) > 0;
    }

    public Reservacion guardar(Reservacion reservacion) {
        if (reservacion.getIdReservacion() == null) {
            reservacion.setFCreation(LocalDateTime.now());
        } else {
            Reservacion actual = buscarPorId(reservacion.getIdReservacion());
            if (actual != null) {
                reservacion.setFCreation(actual.getFCreation());
            }
        }
        return reservacionRepositorio.save(reservacion);
    }

    public void eliminar(Integer id) {
        reservacionRepositorio.deleteById(id);
    }
}