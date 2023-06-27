package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.json.JsonMapper;

import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.dto.Detalle_BoletaDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.entity.Detalle_BoletaEntity;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.repository.Detalle_BoletaRepository;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.exception.ServiceException;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.producto.ProductoDTO;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.producto.ProductoException;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.producto.ProductoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Detalle_BoletaImpl implements Detalle_BoletaService {
 
    @Autowired
    private JsonMapper jsonMapper;

    @Autowired
    private Detalle_BoletaRepository detalle_BoletaRepository;
    
    @Autowired
    private ProductoService productoService ;
    
    @Override
    public List<Detalle_BoletaDTO> findByLike(Detalle_BoletaDTO t) throws ServiceException {
        try {
            List<Detalle_BoletaEntity> detalle_BoletaEntities = detalle_BoletaRepository.findAll();
            return detalle_BoletaEntities.stream()
                    .map(this::getDetalle_BoletaDTO)
                    .map(detalle_BoletaDTO -> {
                        try {
                            ProductoDTO productoDTO = productoService.findById(detalle_BoletaDTO.getId_producto());
                            detalle_BoletaDTO.setProducto(productoDTO);
                            return detalle_BoletaDTO;
                        } catch (ProductoException e) {
                            log.error("Error al obtener el producto del detalle de boleta", e);
                            return detalle_BoletaDTO;
                        }
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Error al buscar detalles de boleta", e);
        }
    }

    @Override
    public Optional<Detalle_BoletaDTO> findById(Detalle_BoletaDTO detalle_BoletaDTO) throws ServiceException {
        try {
            Optional<Detalle_BoletaEntity> detalle_BoletaEntity = detalle_BoletaRepository.findById(detalle_BoletaDTO.getId());
            if (detalle_BoletaEntity.isPresent()) {
                Detalle_BoletaDTO oDetalle_BoletaDTO = this.getDetalle_BoletaDTO(detalle_BoletaEntity.get());
                if (!Objects.isNull(oDetalle_BoletaDTO)) {
                    oDetalle_BoletaDTO.setProducto(productoService.findById(oDetalle_BoletaDTO.getId_producto()));
                }
                return Optional.of(oDetalle_BoletaDTO);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // Mappers

    private Detalle_BoletaDTO getDetalle_BoletaDTO(Detalle_BoletaEntity detalle_BoletaEntity) {
        return jsonMapper.convertValue(detalle_BoletaEntity, Detalle_BoletaDTO.class);
    }

    private Detalle_BoletaEntity updateDetalle_BoletaEntity(Detalle_BoletaEntity entity, Detalle_BoletaDTO dto) {
        entity.setUnidad(dto.getUnidad());
        entity.setId_boleta(dto.getId_boleta());
        entity.setId_producto(dto.getId_producto());
        // Actualizar otros campos según sea necesario
        return entity;
    }

    @Override
    public Detalle_BoletaDTO save(Detalle_BoletaDTO t) throws ServiceException {
        try {
            Detalle_BoletaEntity detalle_BoletaEntity = new Detalle_BoletaEntity();
            detalle_BoletaEntity.setUnidad(t.getUnidad());
            detalle_BoletaEntity.setId_boleta(t.getId_boleta());
            detalle_BoletaEntity.setId_producto(t.getId_producto());

            Detalle_BoletaEntity savedDetalle_BoletaEntity = detalle_BoletaRepository.save(detalle_BoletaEntity);
            Detalle_BoletaDTO savedDetalle_BoletaDTO = getDetalle_BoletaDTO(savedDetalle_BoletaEntity);
            try {
                savedDetalle_BoletaDTO.setProducto(productoService.findById(savedDetalle_BoletaDTO.getId_producto()));
            } catch (ProductoException e) {
                log.error("Error al obtener el producto del detalle de boleta", e);
            }
            return savedDetalle_BoletaDTO;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Detalle_BoletaDTO update(Long id, Detalle_BoletaDTO detalle_BoletaDTO) throws ServiceException {
        try {
            Optional<Detalle_BoletaEntity> optionalDetalle_BoletaEntity = detalle_BoletaRepository.findById(id);
            if (optionalDetalle_BoletaEntity.isPresent()) {
                Detalle_BoletaEntity existingDetalle_BoletaEntity = optionalDetalle_BoletaEntity.get();
                Detalle_BoletaEntity updatedDetalle_BoletaEntity = updateDetalle_BoletaEntity(existingDetalle_BoletaEntity, detalle_BoletaDTO);
                Detalle_BoletaEntity savedDetalle_BoletaEntity = detalle_BoletaRepository.save(updatedDetalle_BoletaEntity);
                Detalle_BoletaDTO updatedDetalle_BoletaDTO = getDetalle_BoletaDTO(savedDetalle_BoletaEntity);
                try {
                    updatedDetalle_BoletaDTO.setProducto(productoService.findById(updatedDetalle_BoletaDTO.getId_producto()));
                } catch (ProductoException e) {
                    log.error("Error al obtener el producto del detalle de boleta", e);
                }
                return updatedDetalle_BoletaDTO;
            } else {
                throw new ServiceException("Detalle de boleta no encontrado");
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        Optional<Detalle_BoletaEntity> optionalDetalle_BoletaEntity = detalle_BoletaRepository.findById(id);
        if (optionalDetalle_BoletaEntity.isEmpty()) {
            throw new ServiceException("Detalle de boleta no encontrado");
        }
        detalle_BoletaRepository.delete(optionalDetalle_BoletaEntity.get());
    }

}
