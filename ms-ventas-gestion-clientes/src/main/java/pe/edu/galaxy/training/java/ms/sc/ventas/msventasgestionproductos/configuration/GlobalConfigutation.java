package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.json.JsonMapper;

@Configuration
public class GlobalConfigutation {

	public GlobalConfigutation() {
		
	}
	
	@Bean
	public JsonMapper getJsonMapper() {
		return new JsonMapper();
	}

}
