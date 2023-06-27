package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.json.JsonMapper;



import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.dto.BoletaDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.entity.BoletaEntity;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.repository.BoletaRepository;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.cliente.ClienteDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.cliente.ClienteException;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.cliente.ClienteService;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.exception.ServiceException;


@Slf4j
@Service
public class BoletaServiceImpl implements BoletaService{
 
	@Autowired
	private JsonMapper jsonMapper;

	@Autowired
	private BoletaRepository boletaRepository;
	
	@Autowired
	private ClienteService clienteService ;
	
	@Override
	public List<BoletaDTO> findByLike(BoletaDTO t) throws ServiceException {
	    try {
	        List<BoletaEntity> boletaEntities = boletaRepository.findAll();
	        return boletaEntities.stream()
	                .map(this::getBoletaDTO)
	                .map(boletaDTO -> {
	                    try {
	                        ClienteDTO clienteDTO = clienteService.findById(boletaDTO.getId_cliente());
	                        boletaDTO.setCliente(clienteDTO);
	                        return boletaDTO;
	                    } catch (ClienteException e) {
	                        log.error("Error al obtener el cliente de la boleta", e);
	                        return boletaDTO;
	                    }
	                })
	                .collect(Collectors.toList());
	    } catch (Exception e) {
	        throw new ServiceException("Error al buscar boletas", e);
	    }
	}



	@Override
	public Optional<BoletaDTO> findById(BoletaDTO boletaDTO) throws ServiceException {
		try {
			Optional<BoletaEntity> boletaEntity = this.boletaRepository.findById(boletaDTO.getId());
			if (boletaEntity.isPresent()) {
				BoletaDTO oBoletaDTO = this.getBoletaDTO(boletaEntity.get());
				if (!Objects.isNull(oBoletaDTO)) {
					oBoletaDTO.setCliente(clienteService.findById(oBoletaDTO.getId_cliente()));
				}
				return Optional.of(oBoletaDTO);
			}
			return Optional.empty();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}



	//Mappers
	
	private BoletaDTO getBoletaDTO(BoletaEntity boletaEntity) {
		return jsonMapper.convertValue(boletaEntity, BoletaDTO.class);
	}
	
	private BoletaEntity updateBoletaEntity(BoletaEntity entity, BoletaDTO dto) {
		entity.setNombre(dto.getNombre());
		entity.setFecha(dto.getFecha());
		entity.setId_cliente(dto.getId_cliente());
		// Actualizar otros campos según sea necesario
		return entity;
	}

	@Override
	public BoletaDTO save(BoletaDTO t) throws ServiceException {
		try {
			BoletaEntity boletaEntity = new BoletaEntity();
			boletaEntity.setNombre(t.getNombre());
			boletaEntity.setFecha(t.getFecha());
			boletaEntity.setId_cliente(t.getId_cliente());

			BoletaEntity savedBoletaEntity = boletaRepository.save(boletaEntity);
			BoletaDTO savedBoletaDTO = getBoletaDTO(savedBoletaEntity);
			try {
				savedBoletaDTO.setCliente(clienteService.findById(savedBoletaDTO.getId_cliente()));
			} catch (ClienteException e) {
				log.error("Error al obtener el cliente de la boleta", e);
			}
			return savedBoletaDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public BoletaDTO update(Long id, BoletaDTO boletaDTO) throws ServiceException {
		try {
			Optional<BoletaEntity> optionalBoletaEntity = boletaRepository.findById(id);
			if (optionalBoletaEntity.isPresent()) {
				BoletaEntity existingBoletaEntity = optionalBoletaEntity.get();
				BoletaEntity updatedBoletaEntity = updateBoletaEntity(existingBoletaEntity, boletaDTO);
				BoletaEntity savedBoletaEntity = boletaRepository.save(updatedBoletaEntity);
				BoletaDTO updatedBoletaDTO = getBoletaDTO(savedBoletaEntity);
				try {
					updatedBoletaDTO.setCliente(clienteService.findById(updatedBoletaDTO.getId_cliente()));
				} catch (ClienteException e) {
					log.error("Error al obtener el cliente de la boleta", e);
				}
				return updatedBoletaDTO;
			} else {
				throw new ServiceException("Boleta no encontrada");
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void delete(Long id) throws ServiceException {
	    Optional<BoletaEntity> optionalBoletaEntity = boletaRepository.findById(id);
	    if (optionalBoletaEntity.isEmpty()) {
	    	throw new ServiceException("Boleta no encontrada");
	    }
	    boletaRepository.delete(optionalBoletaEntity.get());
	}
}
