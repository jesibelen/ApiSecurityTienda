package com.example.apisecuritytienda.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepo productoRepo;
    @Autowired
    public ProductoService(ProductoRepo productoRepo) {
        this.productoRepo = productoRepo;
    }

    public  List<ProductoEntity> listarProducto(){
        return productoRepo.findAll();
    }

    public ProductoEntity getProductoPorId(Integer id){
        ProductoEntity producto = productoRepo.findById(id).orElse(null);
        return producto;
    }

    public List<ProductoEntity> getProductoNombre(String nombre){
        return productoRepo.findProductoEntitiesByNombreContaining(nombre);
    }

    public String guardarProducto (ProductoEntity productoRecibido){
        if(productoRecibido.getIdProducto()!=null) {
            ProductoEntity productoExistente = productoRepo.findById(productoRecibido.getIdProducto()).orElse(null);
            if (productoExistente != null) {
                if (productoRecibido.getNombre() != null) productoExistente.setNombre(productoRecibido.getNombre());
                if (productoRecibido.getPrecio() != null) productoExistente.setPrecio(productoRecibido.getPrecio());
                if (productoRecibido.getStock() != null) productoExistente.setStock(productoRecibido.getStock());

                productoRepo.save(productoExistente);
                return "La modificacion fue exitosa.";
            } else productoRecibido.setIdProducto(null); // producto nuevo
        }
        if (productoRecibido.estoyCompleto()) {
            productoRepo.save(productoRecibido);
            return "La creacion de producto fue exitosa.";
        }else return "Si quieres crear nuevo producto, debe ingresar: nombre, precio y stock";
    }

    public String borrarProducto(Integer id){
        if(productoRepo.existsById(id)){
            productoRepo.deleteById(id);
            return "Borrado exitoso.";
        }
        return "ese Id no existe";

    }

    /*public String guardar(ProductoEntity producto){
        productoRepo.save(producto);
        return "exito";
    }*/
}
