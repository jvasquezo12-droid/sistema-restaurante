package com.mimesa.restaurante.service;

import com.mimesa.restaurante.dto.IndicadoresDTO;
import com.mimesa.restaurante.model.Mesa;
import com.mimesa.restaurante.model.ProductoMenu;
import com.mimesa.restaurante.repository.CategoriaMenuRepository;
import com.mimesa.restaurante.repository.MesaRepository;
import com.mimesa.restaurante.repository.ProductoMenuRepository;
import com.mimesa.restaurante.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndicadorService {
    @Autowired
    private ProductoMenuRepository productoRepository;

    @Autowired
    private CategoriaMenuRepository categoriaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public IndicadoresDTO obtenerIndicadores() {

        IndicadoresDTO datos = new IndicadoresDTO();

        // Productos: se cuentan todos y aparte los que estan disponibles
        List<ProductoMenu> productos = productoRepository.findAll();
        long productosDisponibles = 0;
        for (ProductoMenu producto : productos) {
            if (Boolean.TRUE.equals(producto.getDisponible())) {
                productosDisponibles++;
            }
        }

        // Mesas: se cuentan todas y aparte las que estan libres
        List<Mesa> mesas = mesaRepository.findAll();
        long mesasDisponibles = 0;
        for (Mesa mesa : mesas) {
            if (mesa.getEstado() != null
                    && "Disponible".equalsIgnoreCase(mesa.getEstado().getNombre())) {
                mesasDisponibles++;
            }
        }

        datos.setTotalProductos(productos.size());
        datos.setProductosDisponibles(productosDisponibles);
        datos.setTotalCategorias(categoriaRepository.count());
        datos.setTotalMesas(mesas.size());
        datos.setMesasDisponibles(mesasDisponibles);
        datos.setTotalUsuarios(usuarioRepository.count());

        return datos;
    }
}
