package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.json.JsonMapper;

import lombok.extern.slf4j.XSlf4j;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.dto.ProductoDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.entity.ProductoEntity;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.repository.ProductoRepository;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service.category.CategoriaDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service.category.CategoriaException;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service.category.CategoriaService;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductoServiceImpl implements ProductoService{
 
	@Autowired
	private JsonMapper jsonMapper;

	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private CategoriaService categoriaService ;
	
	@Override
	public List<ProductoDTO> findByLike(ProductoDTO t) throws ServiceException {
	    try {
	        List<ProductoEntity> productoEntities = productoRepository.findAll();
	        return productoEntities.stream()
	                .map(this::getProductoDTO)
	                .map(productoDTO -> {
	                    try {
	                        CategoriaDTO categoriaDTO = categoriaService.findById(productoDTO.getIdcategoria());
	                        productoDTO.setCategoria(categoriaDTO);
	                        return productoDTO;
	                    } catch (CategoriaException e) {
	                        log.error("Error al obtener la categoría del producto", e);
	                        return productoDTO;
	                    }
	                })
	                .collect(Collectors.toList());
	    } catch (Exception e) {
	        throw new ServiceException("Error al buscar productos", e);
	    }
	}



	@Override
	public Optional<ProductoDTO> findById(ProductoDTO productoDTO) throws ServiceException {
		try {
			//log.info("id "+pedidoDTO.getId());
			Optional<ProductoEntity> productoEntity= this.productoRepository.findById(productoDTO.getId());
			if (productoEntity.isPresent()) {
				//log.info("isPresent... "+pedidoEntity.get());
				
				ProductoDTO oProductoDTO=this.getProductoDTO(productoEntity.get());
				if (!Objects.isNull(oProductoDTO)) {
					oProductoDTO.setCategoria(categoriaService.findById(oProductoDTO.getIdcategoria()));
				}

				 return Optional.of(oProductoDTO);
			}
			//log.info("empty... ");
			return Optional.empty();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}



	//Mappers
	
	private ProductoDTO getProductoDTO(ProductoEntity productoEntity) {
		return jsonMapper.convertValue(productoEntity, ProductoDTO.class);
	}
	
	private ProductoEntity getProductoEntity(ProductoDTO d) {
		return jsonMapper.convertValue(d, ProductoEntity.class);
	}
	
	private ProductoEntity updateProductoEntity(ProductoEntity entity, ProductoDTO dto) {
		entity.setNombre(dto.getNombre());
		entity.setDescripcion(dto.getDescripcion());
		entity.setPrecio(dto.getPrecio());
		entity.setImg(dto.getImg());
		entity.setEstado(dto.getEstado());
		entity.setIdcategoria(dto.getIdcategoria());
		// Actualizar otros campos según sea necesario
		return entity;
	}

	@Override
	public ProductoDTO save(ProductoDTO t) throws ServiceException {
		try {
			ProductoEntity productoEntity = new ProductoEntity();
			productoEntity.setNombre(t.getNombre());
			productoEntity.setDescripcion(t.getDescripcion());
			productoEntity.setPrecio(t.getPrecio());
			productoEntity.setImg(t.getImg());
			productoEntity.setEstado(t.getEstado());
			productoEntity.setIdcategoria(t.getIdcategoria());

			ProductoEntity savedProductoEntity = productoRepository.save(productoEntity);
			ProductoDTO savedProductoDTO = getProductoDTO(savedProductoEntity);
			try {
				savedProductoDTO.setCategoria(categoriaService.findById(savedProductoDTO.getIdcategoria()));
			} catch (CategoriaException e) {
				log.error("Error al obtener la categoría del producto", e);
			}
			return savedProductoDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public ProductoDTO update(Long id, ProductoDTO productoDTO) throws ServiceException {
		try {
			Optional<ProductoEntity> optionalProductoEntity = productoRepository.findById(id);
			if (optionalProductoEntity.isPresent()) {
				ProductoEntity existingProductoEntity = optionalProductoEntity.get();
				ProductoEntity updatedProductoEntity = updateProductoEntity(existingProductoEntity, productoDTO);
				ProductoEntity savedProductoEntity = productoRepository.save(updatedProductoEntity);
				ProductoDTO updatedProductoDTO = getProductoDTO(savedProductoEntity);
				try {
					updatedProductoDTO.setCategoria(categoriaService.findById(updatedProductoDTO.getIdcategoria()));
				} catch (CategoriaException e) {
					log.error("Error al obtener la categoría del producto", e);
				}
				return updatedProductoDTO;
			} else {
				throw new ServiceException("Producto no encontrado");
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void delete(Long id) throws ServiceException {
	    Optional<ProductoEntity> optionalProductoEntity = productoRepository.findById(id);
	    if (optionalProductoEntity.isEmpty()) {
	    	throw new ServiceException("Producto no encontrado");
	    }
	    productoRepository.delete(optionalProductoEntity.get());
	}

	
	
}
