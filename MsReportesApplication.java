package com.homefixer.reportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsReportesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsReportesApplication.class, args);
        System.out.println(" ===== MS-REPORTES INICIADO EN PUERTO 8089 =====");
        System.out.println(" Endpoints disponibles:");
        System.out.println("   POST   /api/reportes/generar");
        System.out.println("   GET    /api/reportes/ventas");
        System.out.println("   GET    /api/reportes/tecnicos");
        System.out.println("   PATCH    /api/reportes/dashboard");
        System.out.println("   GET    /api/reportes/periodo/{inicio}/{fin}");
        System.out.println("   GET    /api/reportes/tipo/{tipo}");
        System.out.println("   GET    /api/reportes/health");
        System.out.println(" Base de datos: homefixer_reportes");
        System.out.println(" Microservicio de Reportes funcionando correctamente!");
    }
}