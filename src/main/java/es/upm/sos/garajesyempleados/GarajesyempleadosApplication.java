package es.upm.sos.garajesyempleados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GarajesyempleadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GarajesyempleadosApplication.class, args);
	}

}
