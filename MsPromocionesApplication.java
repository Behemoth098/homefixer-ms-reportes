package com.homefixer.promociones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsPromocionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPromocionesApplication.class, args);
        System.out.println("   MS-PROMOCIONES INICIADO EN PUERTO 8088 ");
        System.out.println("   Endpoints disponibles:");
        System.out.println("   POST   /api/promociones");
        System.out.println("   GET    /api/promociones/activas");
        System.out.println("   GET    /api/promociones/aplicables/{especialidad}");
        System.out.println("   POST   /api/promociones/{id}/aplicar");
        System.out.println("   GET    /api/promociones/proximas-vencer");
        System.out.println("   GET    /api/promociones/tipo/{tipo}");
        System.out.println("   GET    /api/promociones/health");
        System.out.println("   Base de datos: homefixer_promociones");
        System.out.println("   Microservicio de Promociones funcionando correctamente!");
    }
}
