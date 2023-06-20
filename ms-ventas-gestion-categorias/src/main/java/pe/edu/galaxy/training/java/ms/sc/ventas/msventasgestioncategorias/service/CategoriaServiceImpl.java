package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.json.JsonMapper;

import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.dto.CategoriaDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.entity.CategoriaEntity;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.repository.CategoriaRepository;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.service.exception.ServiceException;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	private CategoriaRepository categoriaRepository;
	private JsonMapper jsonMapper;

	public CategoriaServiceImpl(CategoriaRepository categoriaRepository, JsonMapper jsonMapper) {
		super();
		this.categoriaRepository = categoriaRepository;
		this.jsonMapper = jsonMapper;
	}

	@Override
	public List<CategoriaDTO> findByLike(CategoriaDTO t) throws ServiceException {
		try {
			List<CategoriaEntity> lstCategoriaEntity = categoriaRepository.findAll();
			return lstCategoriaEntity.stream().map(e -> this.getCategoriaDTO(e)).collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public Optional<CategoriaDTO> findById(Long id) throws ServiceException {
	    try {
	        Optional<CategoriaEntity> optionalCategoriaEntity = categoriaRepository.findById(id);
	        if (optionalCategoriaEntity.isPresent()) {
	            CategoriaEntity categoriaEntity = optionalCategoriaEntity.get();
	            CategoriaDTO categoriaDTO = getCategoriaDTO(categoriaEntity);
	            return Optional.of(categoriaDTO);
	        } else {
	            return Optional.empty();
	        }
	    } catch (Exception e) {
	        throw new ServiceException(e);
	    }
	}


	// Mappers

	private CategoriaDTO getCategoriaDTO(CategoriaEntity e) {
		return jsonMapper.convertValue(e, CategoriaDTO.class);
	}

	private CategoriaEntity getCategoriaEntity(CategoriaDTO d) {
		return jsonMapper.convertValue(d, CategoriaEntity.class);
	}

	private CategoriaEntity updateCategoriaEntity(CategoriaEntity entity, CategoriaDTO dto) {
		entity.setNombre(dto.getNombre());
		entity.setDescripcion(dto.getDescripcion());
		// Actualizar otros campos según sea necesario
		return entity;
	}

	@Override
	public CategoriaDTO save(CategoriaDTO t) throws ServiceException {
		try {
			CategoriaEntity categoriaEntity = getCategoriaEntity(t);
			CategoriaEntity savedCategoriaEntity = categoriaRepository.save(categoriaEntity);
			return getCategoriaDTO(savedCategoriaEntity);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public CategoriaDTO update(Long id, CategoriaDTO categoriaDTO) throws ServiceException {
		try {
			Optional<CategoriaEntity> optionalCategoriaEntity = categoriaRepository.findById(id);
			if (optionalCategoriaEntity.isPresent()) {
				CategoriaEntity existingCategoriaEntity = optionalCategoriaEntity.get();
				CategoriaEntity updatedCategoriaEntity = updateCategoriaEntity(existingCategoriaEntity, categoriaDTO);
				CategoriaEntity savedCategoriaEntity = categoriaRepository.save(updatedCategoriaEntity);
				return getCategoriaDTO(savedCategoriaEntity);
			} else {
				throw new ServiceException("Categoria no encontrada");
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(Long id) throws ServiceException {
		Optional<CategoriaEntity> optionalCategoriaEntity = categoriaRepository.findById(id);
		if (optionalCategoriaEntity.isEmpty()) {
			throw new ServiceException("Categoria no encontrada");
		}
		categoriaRepository.delete(optionalCategoriaEntity.get());
	}

}