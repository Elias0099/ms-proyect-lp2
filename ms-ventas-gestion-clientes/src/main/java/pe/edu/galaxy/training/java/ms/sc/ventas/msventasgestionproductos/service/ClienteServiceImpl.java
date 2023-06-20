package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.json.JsonMapper;

import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.dto.ClienteDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.entity.ClienteEntity;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.repository.ClienteRepository;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service.exception.ServiceException;

@Service
public class ClienteServiceImpl implements ClienteService {

	private ClienteRepository clienteRepository;
	private JsonMapper jsonMapper;

	public ClienteServiceImpl(ClienteRepository clienteRepository, JsonMapper jsonMapper) {
		super();
		this.clienteRepository = clienteRepository;
		this.jsonMapper = jsonMapper;
	}

	@Override
	public List<ClienteDTO> findByLike(ClienteDTO t) throws ServiceException {
		try {
			List<ClienteEntity> lstClienteEntity = clienteRepository.findAll();
			return lstClienteEntity.stream().map(e -> this.getClienteDTO(e)).collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public Optional<ClienteDTO> findById(Long id) throws ServiceException {
		try {
			Optional<ClienteEntity> optionalClienteEntity = clienteRepository.findById(id);
			if (optionalClienteEntity.isPresent()) {
				ClienteEntity clienteEntity = optionalClienteEntity.get();
				ClienteDTO clienteDTO = getClienteDTO(clienteEntity);
				return Optional.of(clienteDTO);
			} else {
				return Optional.empty();
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	// Mappers

	private ClienteDTO getClienteDTO(ClienteEntity e) {
		return jsonMapper.convertValue(e, ClienteDTO.class);
	}

	private ClienteEntity getClienteEntity(ClienteDTO d) {
		return jsonMapper.convertValue(d, ClienteEntity.class);
	}

	private ClienteEntity updateClienteEntity(ClienteEntity entity, ClienteDTO dto) {
		entity.setNombres(dto.getNombres());
		entity.setApellidos(dto.getApellidos());
		entity.setDni(dto.getDni());
		entity.setDireccion(dto.getDireccion());
		entity.setTelefono(dto.getTelefono());
		entity.setCorreo(dto.getCorreo());
		entity.setEstado(dto.getEstado());
		// Actualizar otros campos según sea necesario
		return entity;
	}

	@Override
	public ClienteDTO save(ClienteDTO t) throws ServiceException {
		try {
			ClienteEntity clienteEntity = getClienteEntity(t);
			ClienteEntity savedClienteEntity = clienteRepository.save(clienteEntity);
			return getClienteDTO(savedClienteEntity);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ClienteDTO update(Long id, ClienteDTO clienteDTO) throws ServiceException {
		try {
			Optional<ClienteEntity> optionalClienteEntity = clienteRepository.findById(id);
			if (optionalClienteEntity.isPresent()) {
				ClienteEntity existingClienteEntity = optionalClienteEntity.get();
				ClienteEntity updatedClienteEntity = updateClienteEntity(existingClienteEntity, clienteDTO);
				ClienteEntity savedClienteEntity = clienteRepository.save(updatedClienteEntity);
				return getClienteDTO(savedClienteEntity);
			} else {
				throw new ServiceException("Cliente no encontrado");
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(Long id) throws ServiceException {
		Optional<ClienteEntity> optionalClienteEntity = clienteRepository.findById(id);
		if (optionalClienteEntity.isEmpty()) {
			throw new ServiceException("Cliente no encontrado");
		}
		clienteRepository.delete(optionalClienteEntity.get());
	}

}
