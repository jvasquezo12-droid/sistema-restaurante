package com.mimesa.restaurante.repository;

import com.mimesa.restaurante.model.ProductoMenu;
import com.mimesa.restaurante.repository.proyeccion.ProductoMasVendidoProyeccion;
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
            "SUM(dp.cantidad) AS cantidadTotal, " +
            "SUM(dp.cantidad * dp.precio_unitario) AS totalVendido " +
            "FROM detalle_pedido dp " +
            "JOIN producto_menu p ON dp.id_producto = p.id_producto " +
            "GROUP BY p.id_producto, p.nombre " +
            "ORDER BY cantidadTotal DESC " +
            "LIMIT 10", nativeQuery = true)
    List<ProductoMasVendidoProyeccion> obtenerProductosMasVendidos();

    @Query(value = "SELECT DATE(p.fecha) AS fecha, " +
            "COUNT(p.id_pedido) AS cantidadPedidos, " +
            "SUM(p.total) AS totalVentas " +
            "FROM pedido p " +
            "WHERE DATE(p.fecha) BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY DATE(p.fecha) " +
            "ORDER BY fecha ASC", nativeQuery = true)
    List<VentaPorFechaProyeccion> obtenerVentasPorFecha(@Param("fechaInicio") LocalDate fechaInicio,
                                                        @Param("fechaFin") LocalDate fechaFin);
}