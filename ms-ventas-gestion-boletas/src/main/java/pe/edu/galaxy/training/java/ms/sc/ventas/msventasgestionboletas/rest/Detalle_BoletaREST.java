package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons.Message;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.dto.Detalle_BoletaDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.Detalle_BoletaService;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.exception.ServiceException;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons.GlobalConstants.MSG_CONSULTA_EXITO;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons.GlobalConstants.COD_CONSULTA_EXITO;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons.GlobalConstants.COD_ERROR;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons.GlobalConstants.COD_DETALLE_BOLETA_NOT_FOUND;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons.GlobalConstants.MSG_DETALLE_BOLETA_NOT_FOUND;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons.GlobalConstants.API_DETALLE_BOLETA;

@Slf4j
@RestController
@RequestMapping(API_DETALLE_BOLETA)
public class Detalle_BoletaREST {
	
	@Autowired
    private Detalle_BoletaService detalleBoletaService;

	@GetMapping
    public ResponseEntity<?> findByLike(@RequestParam(name = "unidad", defaultValue = "") String unidad) {
        try {
            List<Detalle_BoletaDTO> detalleBoletas = detalleBoletaService.findByLike(Detalle_BoletaDTO.builder().unidad(unidad).build());
            if (!detalleBoletas.isEmpty()) {
                return ResponseEntity.ok(
                        Response.builder()
                                .message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
                                .data(detalleBoletas)
                                .build());
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

	@GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            Detalle_BoletaDTO detalleBoleta = detalleBoletaService.findById(Detalle_BoletaDTO.builder().id(id).build()).orElse(null);
            if (detalleBoleta != null) {
                return ResponseEntity.ok(
                        Response.builder()
                                .message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
                                .data(detalleBoleta)
                                .build());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(
                                Response.builder()
                                        .message(Message.builder().code(COD_DETALLE_BOLETA_NOT_FOUND).message(MSG_DETALLE_BOLETA_NOT_FOUND).build())
                                        .build());
            }
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Response> save(@RequestBody Detalle_BoletaDTO detalleBoletaDTO) {
        try {
            Detalle_BoletaDTO savedDetalleBoletaDTO = detalleBoletaService.save(detalleBoletaDTO);
            if (savedDetalleBoletaDTO == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(
                    Response.builder()
                            .message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
                            .data(savedDetalleBoletaDTO)
                            .build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody Detalle_BoletaDTO detalleBoletaDTO) throws ServiceException {
        try {
            Detalle_BoletaDTO updatedDetalleBoletaDTO = detalleBoletaService.update(id, detalleBoletaDTO);
            return ResponseEntity.ok(Response.builder()
                    .message(Message.builder()
                            .code(COD_CONSULTA_EXITO)
                            .message(MSG_CONSULTA_EXITO)
                            .build())
                    .data(updatedDetalleBoletaDTO)
                    .build());
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .message(Message.builder()
                            .code(COD_ERROR)
                            .message(e.getMessage())
                            .build())
                    .build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        try {
            detalleBoletaService.delete(id);
            return ResponseEntity.ok(Response.builder()
                    .message(Message.builder()
                            .code(COD_CONSULTA_EXITO)
                            .message(MSG_CONSULTA_EXITO)
                            .build())
                    .build());
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .message(Message.builder()
                            .code(COD_ERROR)
                            .message(e.getMessage())
                            .build())
                    .build());
        }
    }

}