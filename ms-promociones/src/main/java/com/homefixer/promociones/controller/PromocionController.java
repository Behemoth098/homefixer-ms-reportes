package com.homefixer.promociones.controller;

import com.homefixer.promociones.dto.*;
import com.homefixer.promociones.model.Promocion;
import com.homefixer.promociones.service.PromocionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promociones")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PromocionController {
    
    private final PromocionService promocionService;
    
    @PostMapping
    public ResponseEntity<PromocionResponseDTO> crearPromocion(@Valid @RequestBody PromocionRequestDTO request) {
        try {
            log.info("🎯 POST /api/promociones - Creando promoción: {}", request.getTitulo());
            
            PromocionResponseDTO response = promocionService.crearPromocion(request);
            
            log.info("✅ Promoción creada exitosamente con ID: {}", response.getIdPromocion());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (RuntimeException e) {
            log.error("❌ Error al crear promoción: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("❌ Error interno al crear promoción: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/activas")
    public ResponseEntity<List<PromocionResponseDTO>> obtenerPromocionesActivas() {
        try {
            log.info(" GET /api/promociones/activas - Obteniendo promociones vigentes");
            
            List<PromocionResponseDTO> promociones = promocionService.obtenerPromocionesActivas();
            
            log.info(" Encontradas {} promociones activas", promociones.size());
            return ResponseEntity.ok(promociones);
            
        } catch (Exception e) {
            log.error(" Error al obtener promociones activas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/aplicables/{especialidad}")
    public ResponseEntity<List<PromocionResponseDTO>> obtenerPromocionesAplicables(@PathVariable String especialidad) {
        try {
            log.info("🎯 GET /api/promociones/aplicables/{} - Buscando promociones", especialidad);
            
            List<PromocionResponseDTO> promociones = promocionService.obtenerPromocionesAplicables(especialidad);
            
            log.info("✅ Encontradas {} promociones para especialidad: {}", promociones.size(), especialidad);
            return ResponseEntity.ok(promociones);
            
        } catch (Exception e) {
            log.error("❌ Error al obtener promociones para especialidad {}: {}", especialidad, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/{id}/aplicar")
    public ResponseEntity<AplicarPromocionResponseDTO> aplicarPromocion(
            @PathVariable Long id, 
            @Valid @RequestBody AplicarPromocionRequestDTO request) {
        try {
            log.info("🎯 POST /api/promociones/{}/aplicar - Aplicando promoción a solicitud: {}", 
                    id, request.getIdSolicitud());
            
            AplicarPromocionResponseDTO response = promocionService.aplicarPromocion(id, request);
            
            if (response.getAplicadaExitosamente()) {
                log.info("✅ Promoción aplicada exitosamente. Descuento: ${}", response.getDescuentoAplicado());
                return ResponseEntity.ok(response);
            } else {
                log.warn("⚠️ No se pudo aplicar promoción: {}", response.getMensaje());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
        } catch (RuntimeException e) {
            log.error("❌ Error al aplicar promoción {}: {}", id, e.getMessage());
            
            AplicarPromocionResponseDTO errorResponse = AplicarPromocionResponseDTO.builder()
                    .idPromocion(id)
                    .idSolicitud(request.getIdSolicitud())
                    .montoOriginal(request.getMontoOriginal())
                    .aplicadaExitosamente(false)
                    .mensaje("Error: " + e.getMessage())
                    .build();
                    
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            log.error("❌ Error interno al aplicar promoción {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/proximas-vencer")
    public ResponseEntity<List<PromocionResponseDTO>> obtenerPromocionesProximasVencer() {
        try {
            log.info("🎯 GET /api/promociones/proximas-vencer - Buscando promociones próximas a vencer");
            
            List<PromocionResponseDTO> promociones = promocionService.obtenerPromocionesProximasVencer();
            
            log.info("✅ Encontradas {} promociones próximas a vencer", promociones.size());
            return ResponseEntity.ok(promociones);
            
        } catch (Exception e) {
            log.error("❌ Error al obtener promociones próximas a vencer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/tipo/{tipoPromocion}")
    public ResponseEntity<List<PromocionResponseDTO>> obtenerPromocionesPorTipo(@PathVariable String tipoPromocion) {
        try {
            log.info("🎯 GET /api/promociones/tipo/{} - Buscando promociones por tipo", tipoPromocion);
            
            Promocion.TipoPromocion tipo = Promocion.TipoPromocion.valueOf(tipoPromocion.toUpperCase());
            List<PromocionResponseDTO> promociones = promocionService.obtenerPromocionesPorTipo(tipo);
            
            log.info("✅ Encontradas {} promociones de tipo: {}", promociones.size(), tipo);
            return ResponseEntity.ok(promociones);
            
        } catch (IllegalArgumentException e) {
            log.error("❌ Tipo de promoción inválido: {}", tipoPromocion);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("❌ Error al obtener promociones por tipo {}: {}", tipoPromocion, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("🎯 ms-promociones funcionando correctamente en puerto 8088");
    }
}