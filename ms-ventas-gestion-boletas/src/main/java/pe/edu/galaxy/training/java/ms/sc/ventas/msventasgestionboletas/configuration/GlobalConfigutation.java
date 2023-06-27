package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.json.JsonMapper;

@Configuration
public class GlobalConfigutation {

	@Bean
	public JsonMapper getJsonMapper() {
		return new JsonMapper();
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
