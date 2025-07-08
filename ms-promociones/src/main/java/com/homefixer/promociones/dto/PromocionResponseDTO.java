package com.homefixer.promociones.dto;

import com.homefixer.promociones.model.Promocion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromocionResponseDTO {
    
    private Long idPromocion;
    private String titulo;
    private String descripcion;
    private BigDecimal porcentajeDescuento;
    private BigDecimal montoDescuento;
    private Promocion.TipoPromocion tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String especialidadAplicable;
    private Boolean activa;
    private Integer usosMaximos;
    private Integer usosActuales;
    private Integer usosRestantes;
    private Boolean vigente;
    
    public static PromocionResponseDTO fromEntity(Promocion promocion) {
        Integer usosRestantes = null;
        if (promocion.getUsosMaximos() != null) {
            usosRestantes = promocion.getUsosMaximos() - promocion.getUsosActuales();
        }
        
        return PromocionResponseDTO.builder()
                .idPromocion(promocion.getIdPromocion())
                .titulo(promocion.getTitulo())
                .descripcion(promocion.getDescripcion())
                .porcentajeDescuento(promocion.getPorcentajeDescuento())
                .montoDescuento(promocion.getMontoDescuento())
                .tipo(promocion.getTipo())
                .fechaInicio(promocion.getFechaInicio())
                .fechaFin(promocion.getFechaFin())
                .especialidadAplicable(promocion.getEspecialidadAplicable())
                .activa(promocion.getActiva())
                .usosMaximos(promocion.getUsosMaximos())
                .usosActuales(promocion.getUsosActuales())
                .usosRestantes(usosRestantes)
                .vigente(promocion.isVigente())
                .build();
    }
}