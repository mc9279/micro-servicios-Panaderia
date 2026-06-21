package cl.duoc.proveedores_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(
		info = @Info(
				title = "Proveedores Service API",
				version = "1.0.0",
				description = """
                        API REST para la gestion de proveedores .

                        Permite:
                        - Almacenar los Proveedores
                        - Consultar datos de Proveedores
                        - Eliminar Proveedores
                        - Actualizar informacion de los Proveedores
                        - Agregar Proveedores Nuevos
                        """,
				contact = @Contact(
						name = "Gabriel Millán",
						email = "ga.millan@duoc.cl"
				)
		)
)

@SpringBootApplication
public class ProveedoresServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProveedoresServiceApplication.class, args);
	}

}
