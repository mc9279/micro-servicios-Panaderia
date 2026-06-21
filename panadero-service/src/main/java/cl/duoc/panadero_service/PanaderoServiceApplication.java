package cl.duoc.panadero_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(
		info = @Info(
				title = "Panadero Service API",
				version = "1.0.0",
				description = """
                        API REST para la gestion de Panaderos .

                        Permite:
                        - Almacenar los Panaderos
                        - Consultar especialidades de los Panaderos
                        - Eliminar Panaderos
                        - Actualizar informacion de los Panaderos
                        - Agregar Panaderos Nuevos
                        """,
				contact = @Contact(
						name = "Gabriel Millán",
						email = "ga.millan@duoc.cl"
				)
		)
)

@SpringBootApplication
public class PanaderoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PanaderoServiceApplication.class, args);
	}

}
