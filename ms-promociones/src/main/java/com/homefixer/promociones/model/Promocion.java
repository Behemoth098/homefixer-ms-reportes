package com.homefixer.promociones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "promociones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promocion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPromocion;
    
    @Column(name = "titulo", nullable = false)
    private String titulo;
    
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @Column(name = "porcentaje_descuento", precision = 5, scale = 2)
    private BigDecimal porcentajeDescuento; // Ej: 15.50 para 15.5%
    
    @Column(name = "monto_descuento", precision = 10, scale = 2)
    private BigDecimal montoDescuento; // Descuento fijo en pesos
    
    @Enumerated(EnumType.STRING)
    private TipoPromocion tipo; // PORCENTAJE, MONTO_FIJO, PRIMERA_COMPRA
    
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    
    @Column(name = "especialidad_aplicable", length = 100)
    private String especialidadAplicable; // null = todas las especialidades
    
    @Column(name = "activa")
    private Boolean activa;
    
    @Column(name = "usos_maximos")
    private Integer usosMaximos;
    
    @Column(name = "usos_actuales")
    private Integer usosActuales;
    
    @PrePersist
    public void prePersist() {
        if (this.activa == null) {
            this.activa = true;
        }
        if (this.usosActuales == null) {
            this.usosActuales = 0;
        }
    }
    
    // Método útil para verificar si la promoción es válida
    public boolean isVigente() {
        LocalDate hoy = LocalDate.now();
        return this.activa && 
               (this.fechaInicio == null || !hoy.isBefore(this.fechaInicio)) &&
               (this.fechaFin == null || !hoy.isAfter(this.fechaFin)) &&
               (this.usosMaximos == null || this.usosActuales < this.usosMaximos);
    }
    
    public enum TipoPromocion {
        PORCENTAJE, MONTO_FIJO, PRIMERA_COMPRA, CLIENTE_PREMIUM
    }
}