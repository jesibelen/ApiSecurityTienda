package com.example.apisecuritytienda.producto;

import javax.persistence.*;

@Entity
@Table(name = "productos")
public class ProductoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer idProducto;
    private String nombre;
    private Double precio;
    private Integer stock;

    public ProductoEntity() {
    }
    public boolean estoyCompleto(){
        return (nombre!=null && precio!=null && stock !=null);
    }
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer producto_id) {
        this.idProducto = producto_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ProductoEntity{" +
                "producto_id=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
