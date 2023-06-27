package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.service.receipt;

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
public class BoletaServiceImpl implements BoletaService {
	
private DiscoveryClient discoveryClient;
	
	private RestTemplate restTemplate;
	
	private String url = "ms-ventas-gestion-boletas";
	
	private CircuitBreaker circuitBreaker;
	
	public BoletaServiceImpl(
			RestTemplate restTemplate,
			DiscoveryClient discoveryClient,
			CircuitBreakerFactory<?, ?> circuitBreakerFactory) {
		this.discoveryClient = discoveryClient;
		this.restTemplate = restTemplate;
		this.circuitBreaker = circuitBreakerFactory.create("ms-ventas-gestion-boletas");
	}

	@Override
	public BoletaDTO findById(Long id) throws BoletaException {
		return circuitBreaker.run(() -> {
			ResponseEntity<BoletaDTOResponse> responseEntity = restTemplate.getForEntity(this.getURI() + "/v1/boletas/" + id, BoletaDTOResponse.class);
			if (!Objects.isNull(responseEntity)) {
				return responseEntity.getBody().getData();
			}
			return null;
		}, throwable -> getBoletaDTO());
	}

	@Override
	public List<BoletaDTO> findByLike() throws BoletaException {
		try {
			ResponseEntity<BoletaDTOResponse[]> responseEntity = restTemplate.getForEntity(this.getURI() + "/v1/boletas", BoletaDTOResponse[].class);
			BoletaDTOResponse[] boletaDTOResponses = responseEntity.getBody();

			if (!Objects.isNull(boletaDTOResponses)) {
				return Arrays.stream(boletaDTOResponses)
						.map(BoletaDTOResponse::getData)
						.collect(Collectors.toList());
			}

			return Collections.emptyList();
		} catch (Exception e) {
			throw new BoletaException("Error al buscar boletas", e);
		}
	}

	private BoletaDTO getBoletaDTO() {
		return BoletaDTO.builder()
				.id(0L)
				.nombre("Boleta por definir")
				.fecha("La fecha de esta boleta es...")
				.id_cliente(0L)
				.build();
	}

	private String getURI() {
		if (Objects.isNull(discoveryClient)) {
			log.info("discoveryClient is null");
			return "";
		}
		List<ServiceInstance> instances = discoveryClient.getInstances(url);

		if (Objects.isNull(instances) || instances.isEmpty()) return "not found";
		System.out.println(instances.get(0).getHost());
		String uri = instances.get(0).getUri().toString();
		log.info("uri" + uri);
		return uri;
	}

}
