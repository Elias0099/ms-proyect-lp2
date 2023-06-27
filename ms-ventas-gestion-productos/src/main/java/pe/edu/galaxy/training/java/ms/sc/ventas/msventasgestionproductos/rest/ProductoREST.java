package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.rest;

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

import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.commons.Message;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.dto.ProductoDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service.ProductoService;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service.exception.ServiceException;

import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.commons.GlobalConstants.API_PRODUCTO;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.commons.GlobalConstants.COD_CONSULTA_EXITO;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.commons.GlobalConstants.MSG_CONSULTA_EXITO;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.commons.GlobalConstants.COD_ERROR;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.commons.GlobalConstants.COD_PRODUCT_NOT_FOUND;
import static pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.commons.GlobalConstants.MSG_PRODUCT_NOT_FOUND;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(API_PRODUCTO)
public class ProductoREST {

	@Autowired
	private ProductoService productoService;


	@GetMapping
	public ResponseEntity<?> findByLike(@RequestParam(name = "nombre", defaultValue = "") String nombre) {
	    try {
	        List<ProductoDTO> productos = productoService.findByLike(ProductoDTO.builder().nombre(nombre).build());
	        if (!productos.isEmpty()) {
	        	return ResponseEntity.ok(
                        Response.builder()
                                .message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
                                .data(productos)
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
            ProductoDTO producto = productoService.findById(ProductoDTO.builder().id(id).build()).orElse(null);
            if (producto != null) {
                return ResponseEntity.ok(
                        Response.builder()
                                .message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
                                .data(producto)
                                .build());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(
                                Response.builder()
                                        .message(Message.builder().code(COD_PRODUCT_NOT_FOUND).message(MSG_PRODUCT_NOT_FOUND).build())
                                        .build());
            }
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

	
	@PostMapping
	public ResponseEntity<Response> save(@RequestBody ProductoDTO productoDTO) {
	    try {
	        ProductoDTO savedProductoDTO = productoService.save(productoDTO);
	        if (savedProductoDTO == null) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.ok(
	                Response.builder()
	                        .message(Message.builder().code(COD_CONSULTA_EXITO).message(MSG_CONSULTA_EXITO).build())
	                        .data(savedProductoDTO)
	                        .build());
	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	        return ResponseEntity.internalServerError().build();
	    }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) throws ServiceException {
	    try {
	        ProductoDTO updatedProductoDTO = productoService.update(id, productoDTO);
	        return ResponseEntity.ok(Response.builder()
	                .message(Message.builder()
	                        .code(COD_CONSULTA_EXITO)
	                        .message(MSG_CONSULTA_EXITO)
	                        .build())
	                .data(updatedProductoDTO)
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
	        productoService.delete(id);
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
