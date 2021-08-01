package com.example.apisecuritytienda.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/productos")

public class ProductoController {

    private final  ProductoService productoService;
    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    private List<ProductoEntity> listarProductos(){
        return productoService.listarProducto();
    }

    @GetMapping("/buscar/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_CLIENTE')")
    //@PreAuthorize("hasAnyAuthority('producto:write','producto:read')")
    private ProductoEntity getProductoId(@PathVariable Integer id){
        return productoService.getProductoPorId(id);
    }

    @GetMapping("/{nombre}")
    @PreAuthorize("hasAnyAuthority('producto:write','producto:read')")//cualquiera que pueda leer o escribir producto puede buscaProductoxNombre
    private List<ProductoEntity> buscarProductoNombre(@PathVariable String nombre){
        return productoService.getProductoNombre(nombre);
    }

    @PutMapping(path = "/editar", consumes = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String guardarProducto(@RequestBody ProductoEntity producto){
        return productoService.guardarProducto(producto);
    }

    @PostMapping(path = "/crear", consumes = "application/json")
    @PreAuthorize("hasAuthority('producto:write')")// El que tenga permiso de escribir producto, sin importar su rol, podra crear un producto
    private String crearProducto(@RequestBody ProductoEntity producto){
        return productoService.guardarProducto(producto);
    }

    @DeleteMapping("/borrar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String borrarProducto(@PathVariable Integer id){
        return productoService.borrarProducto(id);
    }

    @GetMapping("/mi-rol")
    public String getMiRol(){

        SecurityContext securityContext= SecurityContextHolder.getContext();
        return  securityContext.getAuthentication().getAuthorities().toString();
        /*  me devuelve el nombre del usuario y esto verifica que nos estamos autenticando bien (OK)
         return securityContext.getAuthentication().getName();
         */
    }
}
