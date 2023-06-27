package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.cliente;

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
public class ClienteServiceImpl implements ClienteService {

	private DiscoveryClient  discoveryClient;
	private RestTemplate 	 restTemplate;
	private String url="ms-ventas-gestion-clientes";
	private CircuitBreaker circuitBreaker;

	public ClienteServiceImpl(
			RestTemplate restTemplate,
			DiscoveryClient discoveryClient,
			CircuitBreakerFactory<?, ?> circuitBreakerFactory) {
		this.discoveryClient = discoveryClient;
		this.restTemplate = restTemplate;
		this.circuitBreaker = circuitBreakerFactory.create("ms-ventas-gestion-clientes");
	}

	@Override
	public ClienteDTO findById(Long id) throws ClienteException {
		return circuitBreaker.run(() -> {
			ResponseEntity<ClienteDTOResponse> rEClienteDTO = restTemplate
					.getForEntity(this.getURI() + "/v1/clientes/" + id, ClienteDTOResponse.class);

			if (!Objects.isNull(rEClienteDTO)) {
				return rEClienteDTO.getBody().getData();
			}
			return null;
		}, throwable -> getClienteDTO());
	}

	@Override
	public List<ClienteDTO> findByLike() throws ClienteException {
		try {
			ResponseEntity<ClienteDTOResponse[]> responseEntity = restTemplate
					.getForEntity(this.getURI() + "/v1/clientes", ClienteDTOResponse[].class);
			ClienteDTOResponse[] clienteDTOResponses = responseEntity.getBody();

			if (!Objects.isNull(clienteDTOResponses)) {
				return Arrays.stream(clienteDTOResponses)
						.map(ClienteDTOResponse::getData)
						.collect(Collectors.toList());
			}

			return Collections.emptyList();
		} catch (Exception e) {
			throw new ClienteException("Error al buscar clientes", e);
		}
	}

	private ClienteDTO getClienteDTO() {
		return ClienteDTO.builder()
				.id(0L)
				.nombres("nombre de cliente por definir...")
				.apellidos("apellido de cliente por definir...")
				.dni("dni de cliente por definir...")
				.direccion("direccion de cliente por definir...")
				.telefono("telefono de cliente por definir...")
				.correo("correo de cliente por definir...")
				.estado("999999999")
				.build();
	}

	private String getURI() {
		if (Objects.isNull(discoveryClient)) {
			log.info("discoveryClient is null");
			return "";
		}
		List<ServiceInstance> instances = discoveryClient.getInstances(url);

		if (Objects.isNull(instances) || instances.isEmpty())
			return "not found";
		System.out.println(instances.get(0).getHost());
		String uri = instances.get(0).getUri().toString();
		log.info("uri" + uri);
		return uri;
	}

}