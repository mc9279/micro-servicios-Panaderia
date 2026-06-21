package cl.duoc.delivery_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@OpenAPIDefinition(
		info = @Info(
				title = "Delivery Service API",
				version = "1.0.0",
				description = """
                        API REST para la gestion de Delivery .

                        Permite:
                        - Almacenar los Delivery
                        - Consultar especialidades de los Delivery
                        - Eliminar Delivery
                        - Actualizar informacion de los Delivery
                        - Agregar Delivery Nuevos
                        """,
				contact = @Contact(
						name = "Gabriel Millán",
						email = "ga.millan@duoc.cl"
				)
		)
)
@SpringBootApplication
@EnableFeignClients
public class DeliveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryServiceApplication.class, args);
	}

}
