package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.json.JsonMapper;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.dto.ProductoDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.entity.ProductoEntity;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.repository.ProductoRepository;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service.exception.ServiceException;

@Service
public class ProductoServiceImpl implements ProductoService{
 
	private ProductoRepository productoRepository;
	private JsonMapper jsonMapper;
	
	public ProductoServiceImpl(	ProductoRepository productoRepository,
								JsonMapper jsonMapper
									) {
		super();
		this.productoRepository = productoRepository;
		this.jsonMapper=jsonMapper;
	}

	@Override
	public List<ProductoDTO> findByLike(ProductoDTO t) throws ServiceException {
		try {
			List<ProductoEntity> lstProductoEntity= productoRepository.findAll();
			return lstProductoEntity.stream().map(e -> this.getProductoDTO(e))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public Optional<ProductoDTO> findById(Long id) throws ServiceException {
	    try {
	        Optional<ProductoEntity> optionalProductoEntity = productoRepository.findById(id);
	        if (optionalProductoEntity.isPresent()) {
	            ProductoEntity productoEntity = optionalProductoEntity.get();
	            ProductoDTO productoDTO = getProductoDTO(productoEntity);
	            return Optional.of(productoDTO);
	        } else {
	            return Optional.empty();
	        }
	    } catch (Exception e) {
	        throw new ServiceException(e);
	    }
	}


	//Mappers
	
	private ProductoDTO getProductoDTO(ProductoEntity e) {
		return jsonMapper.convertValue(e, ProductoDTO.class);
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
			ProductoEntity productoEntity = getProductoEntity(t);
			ProductoEntity savedProductoEntity = productoRepository.save(productoEntity);
			return getProductoDTO(savedProductoEntity);
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
	            return getProductoDTO(savedProductoEntity);
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
