package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.json.JsonMapper;

import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.dto.Historial_PagoDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.entity.Historial_PagoEntity;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.repository.Historial_PagoRepository;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.service.exception.ServiceException;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.service.receipt.BoletaDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.service.receipt.BoletaException;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.service.receipt.BoletaService;

@Slf4j
@Service
public class Historial_PagoServiceImpl implements Historial_PagoService {
 
	@Autowired
	private JsonMapper jsonMapper;

	@Autowired
	private Historial_PagoRepository historial_PagoRepository;
	
	@Autowired
	private BoletaService boletaService;
	
	@Override
	public List<Historial_PagoDTO> findByLike(Historial_PagoDTO t) throws ServiceException {
	    try {
	        List<Historial_PagoEntity> historial_PagoEntities = historial_PagoRepository.findAll();
	        return historial_PagoEntities.stream()
	                .map(this::getHistorial_PagoDTO)
	                .map(historial_PagoDTO -> {
	                    try {
	                        BoletaDTO boletaDTO = boletaService.findById(historial_PagoDTO.getId_boleta());
	                        historial_PagoDTO.setBoleta(boletaDTO);
	                        return historial_PagoDTO;
	                    } catch (BoletaException e) {
	                        log.error("Error al obtener la boleta del historial de pago", e);
	                        return historial_PagoDTO;
	                    }
	                })
	                .collect(Collectors.toList());
	    } catch (Exception e) {
	        throw new ServiceException("Error al buscar historiales de pago", e);
	    }
	}

	@Override
	public Optional<Historial_PagoDTO> findById(Historial_PagoDTO historial_PagoDTO) throws ServiceException {
		try {
			Optional<Historial_PagoEntity> historial_PagoEntity = historial_PagoRepository.findById(historial_PagoDTO.getId());
			if (historial_PagoEntity.isPresent()) {
				Historial_PagoDTO oHistorial_PagoDTO = this.getHistorial_PagoDTO(historial_PagoEntity.get());
				if (!Objects.isNull(oHistorial_PagoDTO)) {
					oHistorial_PagoDTO.setBoleta(boletaService.findById(oHistorial_PagoDTO.getId_boleta()));
				}
				return Optional.of(oHistorial_PagoDTO);
			}
			return Optional.empty();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	private Historial_PagoDTO getHistorial_PagoDTO(Historial_PagoEntity historial_PagoEntity) {
		return jsonMapper.convertValue(historial_PagoEntity, Historial_PagoDTO.class);
	}

	private Historial_PagoEntity updateHistorial_PagoEntity(Historial_PagoEntity entity, Historial_PagoDTO dto) {
		entity.setNombre(dto.getNombre());
		entity.setFecha(dto.getFecha());
		entity.setId_boleta(dto.getId_boleta());
		// Actualizar otros campos según sea necesario
		return entity;
	}

	@Override
	public Historial_PagoDTO save(Historial_PagoDTO t) throws ServiceException {
		try {
			Historial_PagoEntity historial_PagoEntity = new Historial_PagoEntity();
			historial_PagoEntity.setNombre(t.getNombre());
			historial_PagoEntity.setFecha(t.getFecha());
			historial_PagoEntity.setId_boleta(t.getId_boleta());

			Historial_PagoEntity savedHistorial_PagoEntity = historial_PagoRepository.save(historial_PagoEntity);
			Historial_PagoDTO savedHistorial_PagoDTO = getHistorial_PagoDTO(savedHistorial_PagoEntity);
			try {
				savedHistorial_PagoDTO.setBoleta(boletaService.findById(savedHistorial_PagoDTO.getId_boleta()));
			} catch (BoletaException e) {
				log.error("Error al obtener la boleta del historial de pago", e);
			}
			return savedHistorial_PagoDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Historial_PagoDTO update(Long id, Historial_PagoDTO historial_PagoDTO) throws ServiceException {
		try {
			Optional<Historial_PagoEntity> optionalHistorial_PagoEntity = historial_PagoRepository.findById(id);
			if (optionalHistorial_PagoEntity.isPresent()) {
				Historial_PagoEntity existingHistorial_PagoEntity = optionalHistorial_PagoEntity.get();
				Historial_PagoEntity updatedHistorial_PagoEntity = updateHistorial_PagoEntity(existingHistorial_PagoEntity, historial_PagoDTO);
				Historial_PagoEntity savedHistorial_PagoEntity = historial_PagoRepository.save(updatedHistorial_PagoEntity);
				Historial_PagoDTO updatedHistorial_PagoDTO = getHistorial_PagoDTO(savedHistorial_PagoEntity);
				try {
					updatedHistorial_PagoDTO.setBoleta(boletaService.findById(updatedHistorial_PagoDTO.getId_boleta()));
				} catch (BoletaException e) {
					log.error("Error al obtener la boleta del historial de pago", e);
				}
				return updatedHistorial_PagoDTO;
			} else {
				throw new ServiceException("Historial de pago no encontrado");
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(Long id) throws ServiceException {
		Optional<Historial_PagoEntity> optionalHistorial_PagoEntity = historial_PagoRepository.findById(id);
		if (optionalHistorial_PagoEntity.isEmpty()) {
			throw new ServiceException("Historial de pago no encontrado");
		}
		historial_PagoRepository.delete(optionalHistorial_PagoEntity.get());
	}
}
