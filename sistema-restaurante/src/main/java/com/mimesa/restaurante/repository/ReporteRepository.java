package com.mimesa.restaurante.repository;

import com.mimesa.restaurante.model.ProductoMenu;
import com.mimesa.restaurante.repository.proyeccion.ProductoMasVendidoProyeccion;
import com.mimesa.restaurante.repository.proyeccion.VentaPorFechaProyeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ReporteRepository extends JpaRepository<ProductoMenu, Integer> {

    @Query(value = "SELECT p.nombre AS nombreProducto, SUM(dp.cantidad) AS cantidadTotal, " +
            "SUM(dp.cantidad * dp.precio_unitario) AS totalVendido " +
            "FROM detalle_pedido dp " +
            "JOIN producto_menu p ON dp.producto_id = p.id " +
            "GROUP BY p.id, p.nombre " +
            "ORDER BY cantidadTotal DESC", nativeQuery = true)
    List<ProductoMasVendidoProyeccion> obtenerProductosMasVendidos();

    @Query(value = "SELECT DATE(p.fecha) AS fecha, COUNT(p.id) AS cantidadPedidos, SUM(p.total) AS totalMonto " +
            "FROM pedido p " +
            "WHERE DATE(p.fecha) BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY DATE(p.fecha) " +
            "ORDER BY fecha DESC", nativeQuery = true)
    List<VentaPorFechaProyeccion> obtenerVentasPorFecha(@Param("fechaInicio") String fechaInicio,
                                                        @Param("fechaFin") String fechaFin);
}