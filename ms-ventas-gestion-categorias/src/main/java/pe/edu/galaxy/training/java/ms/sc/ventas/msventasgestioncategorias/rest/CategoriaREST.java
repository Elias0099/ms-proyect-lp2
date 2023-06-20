package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.dto.CategoriaDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.service.CategoriaService;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.service.exception.ServiceException;

import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.commons.GlobalConstants.API_CATEGORIA;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.commons.GlobalConstants.COD_CONSULTA_EXITO;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.commons.GlobalConstants.MSG_CONSULTA_EXITO;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.commons.GlobalConstants.COD_ERROR;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.commons.GlobalConstants.COD_CATEGORIA_NOT_FOUND;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.commons.GlobalConstants.MSG_CATEGORIA_NOT_FOUND;


import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(API_CATEGORIA)
public class CategoriaREST {

	private CategoriaService categoriaService;

	public CategoriaREST(CategoriaService categoriaService) {
		super();
		this.categoriaService = categoriaService;
	}
	
	@GetMapping
	public ResponseEntity<Response>  findByLike(){
		try {
			List<CategoriaDTO> lstCategoriaDTO = this.categoriaService.findByLike(null);
			if (lstCategoriaDTO.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(
					Response
					.builder()
					.message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
					.data(lstCategoriaDTO)
					.build());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response> findById(@PathVariable Long id) {
	    try {
	        Optional<CategoriaDTO> optionalCategoriaDTO = categoriaService.findById(id);
	        if (optionalCategoriaDTO.isPresent()) {
	            CategoriaDTO categoriaDTO = optionalCategoriaDTO.get();
	            return ResponseEntity.ok(
	                    Response.builder()
	                            .message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
	                            .data(categoriaDTO)
	                            .build());
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(Response.builder()
	                            .message(Message.builder().code(COD_CATEGORIA_NOT_FOUND).message(MSG_CATEGORIA_NOT_FOUND).build())
	                            .build());
	        }
	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	        return ResponseEntity.internalServerError().build();
	    }
	}

	
	@PostMapping
	public ResponseEntity<Response> save(@RequestBody CategoriaDTO categoriaDTO) {
	    try {
	        CategoriaDTO savedCategoriaDTO = categoriaService.save(categoriaDTO);
	        if (savedCategoriaDTO == null) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.ok(
	                Response.builder()
	                        .message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
	                        .data(savedCategoriaDTO)
	                        .build());
	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	        return ResponseEntity.internalServerError().build();
	    }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) throws ServiceException {
	    try {
	        CategoriaDTO updatedCategoriaDTO = categoriaService.update(id, categoriaDTO);
	        return ResponseEntity.ok(Response.builder()
	                .message(Message.builder()
	                        .code(COD_CONSULTA_EXITO)
	                        .message(MSG_CONSULTA_EXITO)
	                        .build())
	                .data(updatedCategoriaDTO)
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
	        categoriaService.delete(id);
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