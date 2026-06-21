package cl.duoc.ventas_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@OpenAPIDefinition(
		info = @Info(
				title = "Ventas Service API",
				version = "1.0.0",
				description = """
                        API REST para la gestion de Ventas .

                        Permite:
                        - Almacenar los Ventas
                        - Consultar datos de Ventas
                        - Eliminar Ventas
                        - Actualizar informacion de los Ventas
                        - Agregar Ventas Nuevos
                        """,
				contact = @Contact(
						name = "Gabriel Millán",
						email = "ga.millan@duoc.cl"
				)
		)
)

@SpringBootApplication
@EnableFeignClients
public class VentasServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VentasServiceApplication.class, args);
	}

}
