package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.rest;

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
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.dto.BoletaDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.BoletaService;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.exception.ServiceException;

import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons.GlobalConstants.API_BOLETAS;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons.GlobalConstants.MSG_CONSULTA_EXITO;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons.GlobalConstants.COD_CONSULTA_EXITO;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons.GlobalConstants.COD_ERROR;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons.GlobalConstants.COD_BOLETA_NOT_FOUND;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons.GlobalConstants.MSG_BOLETA_NOT_FOUND;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(API_BOLETAS)
public class BoletaREST {
	
	@Autowired
    private BoletaService boletaService;

	@GetMapping
    public ResponseEntity<?> findByLike(@RequestParam(name = "nombre", defaultValue = "") String nombre) {
        try {
            List<BoletaDTO> boletas = boletaService.findByLike(BoletaDTO.builder().nombre(nombre).build());
            if (!boletas.isEmpty()) {
                return ResponseEntity.ok(
                        Response.builder()
                                .message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
                                .data(boletas)
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
            BoletaDTO boleta = boletaService.findById(BoletaDTO.builder().id(id).build()).orElse(null);
            if (boleta != null) {
                return ResponseEntity.ok(
                        Response.builder()
                                .message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
                                .data(boleta)
                                .build());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(
                                Response.builder()
                                        .message(Message.builder().code(COD_BOLETA_NOT_FOUND).message(MSG_BOLETA_NOT_FOUND).build())
                                        .build());
            }
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Response> save(@RequestBody BoletaDTO boletaDTO) {
        try {
            BoletaDTO savedBoletaDTO = boletaService.save(boletaDTO);
            if (savedBoletaDTO == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(
                    Response.builder()
                            .message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
                            .data(savedBoletaDTO)
                            .build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody BoletaDTO boletaDTO) throws ServiceException {
        try {
            BoletaDTO updatedBoletaDTO = boletaService.update(id, boletaDTO);
            return ResponseEntity.ok(Response.builder()
                    .message(Message.builder()
                            .code(COD_CONSULTA_EXITO)
                            .message(MSG_CONSULTA_EXITO)
                            .build())
                    .data(updatedBoletaDTO)
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
            boletaService.delete(id);
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