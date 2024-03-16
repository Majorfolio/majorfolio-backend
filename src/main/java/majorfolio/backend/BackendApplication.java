package majorfolio.backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@OpenAPIDefinition(servers = {@Server(url = "https://majorfolio-server.shop/", description = "Default Server URL")})
//@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})

@EnableScheduling
@SpringBootApplication
public class BackendApplication {
	@PostConstruct
	public void setTimeZone(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}