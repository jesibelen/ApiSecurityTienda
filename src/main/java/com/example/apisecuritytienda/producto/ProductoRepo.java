package com.example.apisecuritytienda.producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepo extends JpaRepository<ProductoEntity, Integer> {
    List<ProductoEntity>findProductoEntitiesByNombreContaining(String nombre);
}
