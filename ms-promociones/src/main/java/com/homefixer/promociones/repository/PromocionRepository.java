package com.homefixer.promociones.repository;

import com.homefixer.promociones.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    
    // Buscar promociones activas
    List<Promocion> findByActivaTrueOrderByFechaInicioDesc();
    
    // Buscar promociones por especialidad específica
    List<Promocion> findByEspecialidadAplicableAndActivaTrueOrderByPorcentajeDescuentoDesc(String especialidad);
    
    // Buscar promociones aplicables a cualquier especialidad (especialidad null)
    List<Promocion> findByEspecialidadAplicableIsNullAndActivaTrueOrderByPorcentajeDescuentoDesc();
    
    // Buscar promociones por tipo
    List<Promocion> findByTipoAndActivaTrueOrderByFechaInicioDesc(Promocion.TipoPromocion tipo);
    
    // Query para promociones vigentes (activas, dentro de fechas, con usos disponibles)
    @Query("""
        SELECT p FROM Promocion p 
        WHERE p.activa = true 
        AND (p.fechaInicio IS NULL OR p.fechaInicio <= :fechaActual)
        AND (p.fechaFin IS NULL OR p.fechaFin >= :fechaActual)
        AND (p.usosMaximos IS NULL OR p.usosActuales < p.usosMaximos)
        ORDER BY p.porcentajeDescuento DESC, p.montoDescuento DESC
        """)
    List<Promocion> findPromocionesVigentes(@Param("fechaActual") LocalDate fechaActual);
    
    // Query para promociones vigentes por especialidad
    @Query("""
        SELECT p FROM Promocion p 
        WHERE p.activa = true 
        AND (p.fechaInicio IS NULL OR p.fechaInicio <= :fechaActual)
        AND (p.fechaFin IS NULL OR p.fechaFin >= :fechaActual)
        AND (p.usosMaximos IS NULL OR p.usosActuales < p.usosMaximos)
        AND (p.especialidadAplicable = :especialidad OR p.especialidadAplicable IS NULL)
        ORDER BY p.porcentajeDescuento DESC, p.montoDescuento DESC
        """)
    List<Promocion> findPromocionesVigentesPorEspecialidad(
            @Param("especialidad") String especialidad, 
            @Param("fechaActual") LocalDate fechaActual
    );
    
    // Buscar promociones que vencen pronto
    @Query("""
        SELECT p FROM Promocion p 
        WHERE p.activa = true 
        AND p.fechaFin IS NOT NULL 
        AND p.fechaFin BETWEEN :fechaActual AND :fechaLimite
        ORDER BY p.fechaFin ASC
        """)
    List<Promocion> findPromocionesProximasVencer(
            @Param("fechaActual") LocalDate fechaActual,
            @Param("fechaLimite") LocalDate fechaLimite
    );
    
    // Buscar promociones por rango de fechas
    List<Promocion> findByFechaInicioBetweenAndActivaTrueOrderByFechaInicioDesc(
            LocalDate fechaInicio, LocalDate fechaFin
    );
    
    // Contar promociones activas
    long countByActivaTrue();
    
    // Contar promociones por tipo
    long countByTipoAndActivaTrue(Promocion.TipoPromocion tipo);
}