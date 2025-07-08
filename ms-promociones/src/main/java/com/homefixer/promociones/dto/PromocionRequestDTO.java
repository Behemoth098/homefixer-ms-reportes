package com.homefixer.promociones.dto;

import com.homefixer.promociones.model.Promocion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
public class PromocionRequestDTO {
    
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede exceder 100 caracteres")
    private String titulo;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
    
    @DecimalMin(value = "0.01", message = "El porcentaje debe ser mayor a 0")
    private BigDecimal porcentajeDescuento;
    
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal montoDescuento;
    
    @NotNull(message = "El tipo de promoción es obligatorio")
    private Promocion.TipoPromocion tipo;
    
    private LocalDate fechaInicio;
    
    private LocalDate fechaFin;
    
    @Size(max = 100, message = "La especialidad no puede exceder 100 caracteres")
    private String especialidadAplicable;
    
    private Boolean activa;
    
    @Min(value = 1, message = "Los usos máximos deben ser mayor a 0")
    private Integer usosMaximos;
}