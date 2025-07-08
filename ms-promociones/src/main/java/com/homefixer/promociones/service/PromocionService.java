package com.homefixer.promociones.service;

import com.homefixer.promociones.dto.*;
import com.homefixer.promociones.model.Promocion;
import com.homefixer.promociones.repository.PromocionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromocionService {
    
    private final PromocionRepository promocionRepository;
    
    @Transactional
    public PromocionResponseDTO crearPromocion(PromocionRequestDTO request) {
        log.info("✅ Creando nueva promoción: {}", request.getTitulo());
        
        // Validar que tenga al menos un tipo de descuento
        if (request.getPorcentajeDescuento() == null && request.getMontoDescuento() == null) {
            throw new RuntimeException("La promoción debe tener porcentaje o monto de descuento");
        }
        
        Promocion promocion = Promocion.builder()
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .porcentajeDescuento(request.getPorcentajeDescuento())
                .montoDescuento(request.getMontoDescuento())
                .tipo(request.getTipo())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .especialidadAplicable(request.getEspecialidadAplicable())
                .activa(request.getActiva() != null ? request.getActiva() : true)
                .usosMaximos(request.getUsosMaximos())
                .usosActuales(0)
                .build();
        
        Promocion savedPromocion = promocionRepository.save(promocion);
        log.info("✅ Promoción creada exitosamente con ID: {}", savedPromocion.getIdPromocion());
        
        return PromocionResponseDTO.fromEntity(savedPromocion);
    }
    
    @Transactional(readOnly = true)
    public List<PromocionResponseDTO> obtenerPromocionesActivas() {
        log.info("🔍 Buscando promociones activas vigentes");
        
        List<Promocion> promociones = promocionRepository.findPromocionesVigentes(LocalDate.now());
        
        log.info("✅ Encontradas {} promociones vigentes", promociones.size());
        return promociones.stream()
                .map(PromocionResponseDTO::fromEntity)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public List<PromocionResponseDTO> obtenerPromocionesAplicables(String especialidad) {
        log.info("🔍 Buscando promociones aplicables para especialidad: {}", especialidad);
        
        List<Promocion> promociones = promocionRepository.findPromocionesVigentesPorEspecialidad(
                especialidad, LocalDate.now()
        );
        
        log.info("✅ Encontradas {} promociones para especialidad: {}", promociones.size(), especialidad);
        return promociones.stream()
                .map(PromocionResponseDTO::fromEntity)
                .toList();
    }
    
    @Transactional
    public AplicarPromocionResponseDTO aplicarPromocion(Long idPromocion, AplicarPromocionRequestDTO request) {
        log.info("🎯 Aplicando promoción {} a solicitud {}", idPromocion, request.getIdSolicitud());
        
        // Buscar promoción
        Optional<Promocion> promocionOpt = promocionRepository.findById(idPromocion);
        if (promocionOpt.isEmpty()) {
            throw new RuntimeException("Promoción no encontrada con ID: " + idPromocion);
        }
        
        Promocion promocion = promocionOpt.get();
        
        // Validar que la promoción sea vigente
        if (!promocion.isVigente()) {
            return AplicarPromocionResponseDTO.builder()
                    .idPromocion(idPromocion)
                    .idSolicitud(request.getIdSolicitud())
                    .montoOriginal(request.getMontoOriginal())
                    .aplicadaExitosamente(false)
                    .mensaje("La promoción no está vigente o ha alcanzado el límite de usos")
                    .build();
        }
        
        // Validar especialidad si aplica
        if (promocion.getEspecialidadAplicable() != null && 
            request.getEspecialidad() != null &&
            !promocion.getEspecialidadAplicable().equalsIgnoreCase(request.getEspecialidad())) {
            
            return AplicarPromocionResponseDTO.builder()
                    .idPromocion(idPromocion)
                    .idSolicitud(request.getIdSolicitud())
                    .montoOriginal(request.getMontoOriginal())
                    .aplicadaExitosamente(false)
                    .mensaje("Esta promoción no aplica para la especialidad: " + request.getEspecialidad())
                    .build();
        }
        
        // Calcular descuento
        BigDecimal descuentoAplicado = calcularDescuento(promocion, request.getMontoOriginal());
        BigDecimal montoFinal = request.getMontoOriginal().subtract(descuentoAplicado);
        
        // Asegurar que el monto final no sea negativo
        if (montoFinal.compareTo(BigDecimal.ZERO) < 0) {
            montoFinal = BigDecimal.ZERO;
            descuentoAplicado = request.getMontoOriginal();
        }
        
        // Incrementar usos de la promoción
        promocion.setUsosActuales(promocion.getUsosActuales() + 1);
        promocionRepository.save(promocion);
        
        log.info("✅ Promoción aplicada exitosamente. Descuento: ${}, Monto final: ${}", 
                descuentoAplicado, montoFinal);
        
        return AplicarPromocionResponseDTO.builder()
                .idPromocion(idPromocion)
                .tituloPromocion(promocion.getTitulo())
                .idSolicitud(request.getIdSolicitud())
                .montoOriginal(request.getMontoOriginal())
                .descuentoAplicado(descuentoAplicado)
                .montoFinal(montoFinal)
                .tipoDescuento(promocion.getTipo().toString())
                .aplicadaExitosamente(true)
                .mensaje("Promoción aplicada exitosamente")
                .build();
    }
    
    @Transactional(readOnly = true)
    public List<PromocionResponseDTO> obtenerPromocionesProximasVencer() {
        log.info("🔍 Buscando promociones próximas a vencer");
        
        LocalDate hoy = LocalDate.now();
        LocalDate dentroDeUnaSemana = hoy.plusDays(7);
        
        List<Promocion> promociones = promocionRepository.findPromocionesProximasVencer(hoy, dentroDeUnaSemana);
        
        log.info("✅ Encontradas {} promociones próximas a vencer", promociones.size());
        return promociones.stream()
                .map(PromocionResponseDTO::fromEntity)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public List<PromocionResponseDTO> obtenerPromocionesPorTipo(Promocion.TipoPromocion tipo) {
        log.info("🔍 Buscando promociones de tipo: {}", tipo);
        
        List<Promocion> promociones = promocionRepository.findByTipoAndActivaTrueOrderByFechaInicioDesc(tipo);
        
        return promociones.stream()
                .map(PromocionResponseDTO::fromEntity)
                .toList();
    }
    
    private BigDecimal calcularDescuento(Promocion promocion, BigDecimal montoOriginal) {
        BigDecimal descuento = BigDecimal.ZERO;
        
        if (promocion.getPorcentajeDescuento() != null) {
            // Descuento por porcentaje
            descuento = montoOriginal
                    .multiply(promocion.getPorcentajeDescuento())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            
        } else if (promocion.getMontoDescuento() != null) {
            // Descuento fijo
            descuento = promocion.getMontoDescuento();
        }
        
        return descuento;
    }
}