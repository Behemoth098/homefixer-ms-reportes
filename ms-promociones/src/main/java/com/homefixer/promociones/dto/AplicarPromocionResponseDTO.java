package com.homefixer.promociones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AplicarPromocionResponseDTO {
    
    private Long idPromocion;
    private String tituloPromocion;
    private Long idSolicitud;
    private BigDecimal montoOriginal;
    private BigDecimal descuentoAplicado;
    private BigDecimal montoFinal;
    private String tipoDescuento; // "PORCENTAJE" o "MONTO_FIJO"
    private String mensaje;
    private Boolean aplicadaExitosamente;
}