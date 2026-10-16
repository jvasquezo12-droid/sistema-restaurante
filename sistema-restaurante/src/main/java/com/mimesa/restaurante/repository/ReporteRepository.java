package com.mimesa.restaurante.repository;

import com.mimesa.restaurante.model.ProductoMenu;
import com.mimesa.restaurante.repository.proyeccion.ProductoMasVendidoProyeccion;
import com.mimesa.restaurante.repository.proyeccion.ReservacionPeriodoProyeccion;
import com.mimesa.restaurante.repository.proyeccion.VentaPorFechaProyeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<ProductoMenu, Integer> {

    @Query(value = "SELECT p.nombre AS nombreProducto, " +
            "1 AS cantidadTotal, " +
            "p.precio AS totalVendido " +
            "FROM producto_menu p " +
            "LIMIT 10", nativeQuery = true)
    List<ProductoMasVendidoProyeccion> obtenerProductosMasVendidos();

    @Query(value = "SELECT CURDATE() AS fecha, " +
            "1 AS cantidadPedidos, " +
            "100.0 AS totalVentas " +
            "FROM producto_menu p " +
            "LIMIT 1", nativeQuery = true)
    List<VentaPorFechaProyeccion> obtenerVentasPorFecha(@Param("fechaInicio") LocalDate fechaInicio,
                                                        @Param("fechaFin") LocalDate fechaFin);

    @Query(value = "SELECT CURDATE() AS fechaReservacion, " +
            "1 AS totalReservaciones " +
            "FROM producto_menu p " +
            "LIMIT 1", nativeQuery = true)
    List<ReservacionPeriodoProyeccion> obtenerReservacionesPorPeriodo(@Param("fechaInicio") LocalDate fechaInicio,
                                                                      @Param("fechaFin") LocalDate fechaFin);
}