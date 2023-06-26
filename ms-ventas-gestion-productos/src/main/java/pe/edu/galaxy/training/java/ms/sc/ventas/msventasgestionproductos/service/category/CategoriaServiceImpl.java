package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service.category;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class CategoriaServiceImpl implements CategoriaService{
	
	private DiscoveryClient discoveryClient;
	
	private RestTemplate 	restTemplate;
	
	private String url="ms-ventas-gestion-categorias";
	
	private CircuitBreaker circuitBreaker;
	
	
	public CategoriaServiceImpl ( 
			RestTemplate restTemplate,
			DiscoveryClient discoveryClient,
			CircuitBreakerFactory<?, ?> circuitBreakerFactory) {
		this.discoveryClient=discoveryClient;
		this.restTemplate = restTemplate;
		//log.info("this.getURI() "+this.getURI());
		this.circuitBreaker = circuitBreakerFactory.create("ms-ventas-gestion-categorias");
	}

	@Override
	public CategoriaDTO findById(Long id) throws CategoriaException {
		//log.info("this.getURI() "+this.getURI());
		
		// Sin resilencia
		/*
		ResponseEntity<ClienteDTOResponse> rEClienteDTO=restTemplate.getForEntity(this.getURI()+"/v1/clientes/"+id, ClienteDTOResponse.class);
	
		if (!Objects.isNull(rEClienteDTO)) {
			return rEClienteDTO.getBody().getData();
		}
		return null;
		
		*/
		
		// Con resilencia
		return circuitBreaker.run(() -> {
			
			// Plan A
			ResponseEntity<CategoriaDTOResponse> rECategoriaDTO=restTemplate.getForEntity(this.getURI()+"/v1/categorias/"+id, CategoriaDTOResponse.class);
			
			if (!Objects.isNull(rECategoriaDTO)) {
				return rECategoriaDTO.getBody().getData();
			}
			return null;
		},
			throwable -> getCategoriaDTO()
		);
	}
	

	@Override
	public List<CategoriaDTO> findByLike() throws CategoriaException {
	    try {
	        ResponseEntity<CategoriaDTOResponse[]> responseEntity = restTemplate.getForEntity(this.getURI() + "/v1/categorias", CategoriaDTOResponse[].class);
	        CategoriaDTOResponse[] categoriaDTOResponses = responseEntity.getBody();

	        if (!Objects.isNull(categoriaDTOResponses)) {
	            return Arrays.stream(categoriaDTOResponses)
	                    .map(CategoriaDTOResponse::getData)
	                    .collect(Collectors.toList());
	        }

	        return Collections.emptyList();
	    } catch (Exception e) {
	        throw new CategoriaException("Error al buscar categorías", e);
	    }
	}

	
	private CategoriaDTO getCategoriaDTO() {
		return CategoriaDTO.
					builder().
					id(0L)
					.nombre("Categoria por definir")
					.descripcion("esta categoria es...")
					.estado("999999999")
					.build();
		// Plan B
		/*
		return circuitBreaker.run(() -> {
					
				// Ok
			},
				throwable -> //Error
				// Plan C
			);
		*/
	}
	
	
	private String getURI() {
		if (Objects.isNull(discoveryClient)) {
			log.info("discoveryClient is null");
			return "";
		}
		List<ServiceInstance> instances = discoveryClient.getInstances(url);

		if (Objects.isNull(instances) || instances.isEmpty()) return "not found";
		System.out.println(instances.get(0).getHost());
		String uri=instances.get(0).getUri().toString();		
		log.info("uri" +uri);
		return uri;
	}
	

}
