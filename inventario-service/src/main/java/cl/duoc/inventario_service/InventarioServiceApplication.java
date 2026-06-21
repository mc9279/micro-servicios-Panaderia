package cl.duoc.inventario_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@OpenAPIDefinition(
		info = @Info(
				title = "Inventario Service API",
				version = "1.0.0",
				description = """
                        API REST para la gestion de Inventario .

                        Permite:
                        - Almacenar los Inventario
                        - Consultar datos de Inventario
                        - Eliminar Inventario
                        - Actualizar informacion de los Inventario
                        - Agregar Inventario nuevos
                        """,
				contact = @Contact(
						name = "Gabriel Millán",
						email = "ga.millan@duoc.cl"
				)
		)
)

@SpringBootApplication
@EnableFeignClients
public class InventarioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventarioServiceApplication.class, args);
	}

}
