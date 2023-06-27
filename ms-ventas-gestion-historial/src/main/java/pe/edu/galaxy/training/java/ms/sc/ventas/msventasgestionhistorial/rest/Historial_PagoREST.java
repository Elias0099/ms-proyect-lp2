package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.rest;

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
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.commons.Message;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.dto.Historial_PagoDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.service.Historial_PagoService;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.service.exception.ServiceException;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.commons.GlobalConstants.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(API_HISTORIALES)

public class Historial_PagoREST {

	@Autowired
	private Historial_PagoService historial_PagoService;

	@GetMapping
	public ResponseEntity<?> findByLike(@RequestParam(name = "nombre", defaultValue = "") String nombre) {
		try {
			List<Historial_PagoDTO> historiales_Pago = historial_PagoService.findByLike(Historial_PagoDTO.builder().nombre(nombre).build());
			if (!historiales_Pago.isEmpty()) {
				return ResponseEntity.ok(
						Response.builder()
								.message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
								.data(historiales_Pago)
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
			Historial_PagoDTO historial_Pago = historial_PagoService.findById(Historial_PagoDTO.builder().id(id).build()).orElse(null);
			if (historial_Pago != null) {
				return ResponseEntity.ok(
						Response.builder()
								.message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
								.data(historial_Pago)
								.build());
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(
								Response.builder()
										.message(Message.builder().code(COD_HISTORIAL_PAGO_NOT_FOUND).message(MSG_HISTORIAL_PAGO_NOT_FOUND).build())
										.build());
			}
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping
	public ResponseEntity<Response> save(@RequestBody Historial_PagoDTO historial_PagoDTO) {
		try {
			Historial_PagoDTO savedHistorial_PagoDTO = historial_PagoService.save(historial_PagoDTO);
			if (savedHistorial_PagoDTO == null) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(
					Response.builder()
							.message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
							.data(savedHistorial_PagoDTO)
							.build());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody Historial_PagoDTO historial_PagoDTO) throws ServiceException {
		try {
			Historial_PagoDTO updatedHistorial_PagoDTO = historial_PagoService.update(id, historial_PagoDTO);
			return ResponseEntity.ok(Response.builder()
					.message(Message.builder()
							.code(COD_CONSULTA_EXITO)
							.message(MSG_CONSULTA_EXITO)
							.build())
					.data(updatedHistorial_PagoDTO)
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
			historial_PagoService.delete(id);
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
