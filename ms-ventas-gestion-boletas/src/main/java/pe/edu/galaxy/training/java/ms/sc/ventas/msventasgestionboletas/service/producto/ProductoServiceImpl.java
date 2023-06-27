package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.producto;

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
public class ProductoServiceImpl implements ProductoService {

	private DiscoveryClient discoveryClient;

	private RestTemplate restTemplate;

	private String url = "ms-ventas-gestion-productos";

	private CircuitBreaker circuitBreaker;

	public ProductoServiceImpl(RestTemplate restTemplate, DiscoveryClient discoveryClient,
			CircuitBreakerFactory<?, ?> circuitBreakerFactory) {
		this.discoveryClient = discoveryClient;
		this.restTemplate = restTemplate;
		// log.info("this.getURI() "+this.getURI());
		this.circuitBreaker = circuitBreakerFactory.create("ms-ventas-gestion-productos");
	}

	@Override
	public ProductoDTO findById(Long id) throws ProductoException {
		return circuitBreaker.run(() -> {
			ResponseEntity<ProductoDTOResponse> rEProductoDTO = restTemplate
					.getForEntity(this.getURI() + "/v1/productos/" + id, ProductoDTOResponse.class);
			if (!Objects.isNull(rEProductoDTO)) {
				return rEProductoDTO.getBody().getData();
			}
			return null;
		}, throwable -> getProductoDTO());
	}

	@Override
	public List<ProductoDTO> findByLike() throws ProductoException {
		try {
			ResponseEntity<ProductoDTOResponse[]> responseEntity = restTemplate
					.getForEntity(this.getURI() + "/v1/productos", ProductoDTOResponse[].class);
			ProductoDTOResponse[] productoDTOResponses = responseEntity.getBody();

			if (!Objects.isNull(productoDTOResponses)) {
				return Arrays.stream(productoDTOResponses).map(ProductoDTOResponse::getData)
						.collect(Collectors.toList());
			}

			return Collections.emptyList();
		} catch (Exception e) {
			throw new ProductoException("Error al buscar productos", e);
		}
	}

	private ProductoDTO getProductoDTO() {
		return ProductoDTO
				.builder()
				.id(0L)
				.nombre("Producto por definir")
				.descripcion("descripcion de este producto es...")
				.precio(9999.999)
				.img("img de este producto es...")
				.idcategoria(0L)
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