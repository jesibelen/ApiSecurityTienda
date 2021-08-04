package com.example.apisecuritytienda.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/productos")

public class ProductoController {
    private final  ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoEntity> listarProductos(){
        return productoService.listarProducto();
    }

    @GetMapping("/buscar/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_CLIENTE', 'ROLE_ADMIN')")
    public ProductoEntity getProductoId(@PathVariable Integer id){
        return productoService.getProductoPorId(id);
    }

    @GetMapping("/{nombre}")
    @PreAuthorize("hasAnyAuthority('producto:read')")
    public List<ProductoEntity> buscarProductoNombre(@PathVariable String nombre){
        return productoService.getProductoNombre(nombre);
    }

    @PutMapping(path = "/guardar", consumes = "application/json")
    @PreAuthorize("hasAuthority('producto:write')")
    public String editarProducto(@RequestBody ProductoEntity producto){
        return productoService.guardarProducto(producto);
    }

    @PostMapping(path = "/editar", consumes = "application/json")
    @PreAuthorize("hasAuthority('producto:write')")
    public String crearProducto(@RequestBody ProductoEntity producto){
        return productoService.guardarProducto(producto);
    }

    @DeleteMapping("/borrar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String borrarProducto(@PathVariable Integer id){
        return productoService.borrarProducto(id);
    }

    @GetMapping("/mi-rol")
    public String getMiRol(){

        SecurityContext securityContext= SecurityContextHolder.getContext();

        /*  me devuelve el nombre del usuario y esto verifica que nos estamos autenticando bien (OK)
         return securityContext.getAuthentication().getName();
         */
        return  securityContext.getAuthentication().getAuthorities().toString();
        //Esto comprobo que fallo mi teoria de que mi rol que habia generado era manual
        // ya que al verlo por postman me aparece [ROLE_ADMIN]
        //Mas adelante lo generaremos manualmente
    }
}
