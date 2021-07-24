package com.example.apisecuritytienda.securityConfig;

public enum UserPermission {
    //Aqui se puede crear otros tipos de permissions como CREATE pero por convencion usamos read/write
    PRODUCTO_READ("producto:read"),
    PRODUCTO_WRITE("producto:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private String permission;

    UserPermission(String permission){
        this.permission= permission;
    }

    public String getPermission(){
        return permission;
    }
}
