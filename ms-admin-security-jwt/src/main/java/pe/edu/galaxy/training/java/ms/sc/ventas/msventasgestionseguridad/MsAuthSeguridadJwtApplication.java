package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsAuthSeguridadJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAuthSeguridadJwtApplication.class, args);
	}


}
