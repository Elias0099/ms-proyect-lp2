package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.dto.ClienteDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service.ClienteService;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service.exception.ServiceException;

import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.commons.GlobalConstants.API_CLIENTE;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.commons.GlobalConstants.COD_CONSULTA_EXITO;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.commons.GlobalConstants.MSG_CONSULTA_EXITO;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.commons.GlobalConstants.COD_ERROR;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.commons.GlobalConstants.MSG_CLIENTE_NOT_FOUND;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.commons.GlobalConstants.COD_CLIENTE_NOT_FOUND;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(API_CLIENTE)
public class ClienteREST {

	private ClienteService clienteService;

	public ClienteREST(ClienteService clienteService) {
		super();
		this.clienteService = clienteService;
	}
	
	@GetMapping
	public ResponseEntity<Response> findByLike(){
		try {
			List<ClienteDTO> lstClienteDTO = this.clienteService.findByLike(null);
			if (lstClienteDTO.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(
					Response
					.builder()
					.message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
					.data(lstClienteDTO)
					.build());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response> findById(@PathVariable Long id) {
	    try {
	        Optional<ClienteDTO> optionalClienteDTO = clienteService.findById(id);
	        if (optionalClienteDTO.isPresent()) {
	            ClienteDTO clienteDTO = optionalClienteDTO.get();
	            return ResponseEntity.ok(
	                    Response.builder()
	                            .message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
	                            .data(clienteDTO)
	                            .build());
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(Response.builder()
	                            .message(Message.builder().code(COD_CLIENTE_NOT_FOUND).message(MSG_CLIENTE_NOT_FOUND).build())
	                            .build());
	        }
	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	        return ResponseEntity.internalServerError().build();
	    }
	}

	
	@PostMapping
	public ResponseEntity<Response> save(@RequestBody ClienteDTO clienteDTO) {
	    try {
	        ClienteDTO savedClienteDTO = clienteService.save(clienteDTO);
	        if (savedClienteDTO == null) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.ok(
	                Response.builder()
	                        .message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
	                        .data(savedClienteDTO)
	                        .build());
	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	        return ResponseEntity.internalServerError().build();
	    }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) throws ServiceException {
	    try {
	        ClienteDTO updatedClienteDTO = clienteService.update(id, clienteDTO);
	        return ResponseEntity.ok(Response.builder()
	                .message(Message.builder()
	                        .code(COD_CONSULTA_EXITO)
	                        .message(MSG_CONSULTA_EXITO)
	                        .build())
	                .data(updatedClienteDTO)
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
	        clienteService.delete(id);
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
