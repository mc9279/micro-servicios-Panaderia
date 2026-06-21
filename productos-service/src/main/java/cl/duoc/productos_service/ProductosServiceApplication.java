package cl.duoc.productos_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(
		info = @Info(
				title = "Productos Service API",
				version = "1.0.0",
				description = """
                        API REST para la gestion de productos .

                        Permite:
                        - Almacenar los Producots
                        - Consultar datos de Producots
                        - Eliminar Producots
                        - Actualizar informacion de los Producots
                        - Agregar Producots Nuevos
                        """,
				contact = @Contact(
						name = "Gabriel Millán",
						email = "ga.millan@duoc.cl"
				)
		)
)

@SpringBootApplication
public class ProductosServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductosServiceApplication.class, args);
	}

}
