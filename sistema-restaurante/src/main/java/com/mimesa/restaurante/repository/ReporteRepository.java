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

    // Suma las cantidades y los subtotales del detalle de los pedidos,
    // agrupando por producto, y devuelve los diez primeros.
    @Query(value = "SELECT p.nombre AS nombreProducto, " +
            "SUM(d.cantidad) AS cantidadTotal, " +
            "SUM(d.subtotal) AS totalVendido " +
            "FROM detalle_pedido d " +
            "JOIN producto_menu p ON p.idProducto = d.idProducto " +
            "GROUP BY p.idProducto, p.nombre " +
            "ORDER BY cantidadTotal DESC " +
            "LIMIT 10", nativeQuery = true)
    List<ProductoMasVendidoProyeccion> obtenerProductosMasVendidos();

    // Agrupa las facturas por dia dentro del rango indicado y suma sus totales.
    @Query(value = "SELECT DATE(f.fFactura) AS fecha, " +
            "COUNT(*) AS cantidadPedidos, " +
            "SUM(f.total) AS totalVentas " +
            "FROM factura f " +
            "WHERE DATE(f.fFactura) BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY DATE(f.fFactura) " +
            "ORDER BY fecha", nativeQuery = true)
    List<VentaPorFechaProyeccion> obtenerVentasPorFecha(@Param("fechaInicio") LocalDate fechaInicio,
                                                        @Param("fechaFin") LocalDate fechaFin);

    // Lista las reservaciones del rango indicado, con el nombre del cliente
    // y el estado en que se encuentra cada una.
    @Query(value = "SELECT r.idReservacion AS idReservacion, " +
            "u.nombre AS nombreCliente, " +
            "r.fecha AS fecha, " +
            "e.nombre AS estado " +
            "FROM reservacion r " +
            "JOIN cliente c ON c.idCliente = r.idCliente " +
            "JOIN usuario u ON u.idUsuario = c.idUsuario " +
            "JOIN estado_reservacion e ON e.idEstadoReservacion = r.idEstadoReservacion " +
            "WHERE r.fecha BETWEEN :fechaInicio AND :fechaFin " +
            "ORDER BY r.fecha", nativeQuery = true)
    List<ReservacionPeriodoProyeccion> obtenerReservacionesPorPeriodo(@Param("fechaInicio") LocalDate fechaInicio,
                                                                      @Param("fechaFin") LocalDate fechaFin);
}