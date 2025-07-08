package com.homefixer.promociones.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AplicarPromocionRequestDTO {
    
    @NotNull(message = "El ID de solicitud es obligatorio")
    private Long idSolicitud;
    
    @NotNull(message = "El monto original es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal montoOriginal;
    
    private String especialidad; // Para validar especialidad aplicable
    
    private Long idUsuario; // Para validar promociones de primera compra
}