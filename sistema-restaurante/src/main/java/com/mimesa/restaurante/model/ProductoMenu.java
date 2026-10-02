package com.mimesa.restaurante.model;

import jakarta.persistence.*;
        import java.math.BigDecimal;

@Entity
@Table(name = "producto_menu")
public class ProductoMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(length = 500)
    private String img;

    @ManyToOne
    @JoinColumn(name = "idCategoria", nullable = false)
    private CategoriaMenu categoria;

    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }

    public CategoriaMenu getCategoria() { return categoria; }
    public void setCategoria(CategoriaMenu categoria) { this.categoria = categoria; }
}