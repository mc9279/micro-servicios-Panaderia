package cl.duoc.clientes_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Clientes Service API",
				version = "1.0.0",
				description = """
                        API REST para la gestion de Clientes .

                        Permite:
                        - Almacenar los Clientes
                        - Consultar datos de Clientes
                        - Eliminar Clientes
                        - Actualizar informacion de los Clientes
                        - Agregar Clientes Nuevos
                        """,
				contact = @Contact(
						name = "Gabriel Millán",
						email = "ga.millan@duoc.cl"
				)
		)
)
@SpringBootApplication
public class ClientesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientesServiceApplication.class, args);
	}

}
