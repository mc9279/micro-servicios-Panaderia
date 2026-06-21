package cl.duoc.clientes_service.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class Cliente {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Size(max = 20, message = "Nombre solo puede tener un maximo de 20 caracteres")
        private String nombre;
        @NotBlank
        @Size(max = 20, message = "Apellido solo puede tener un maximo de 20 caracteres")
        private String apellido;
        @NotBlank
        @Size(max = 20, message = "Nombre solo puede tener un maximo de 20 caracteres")
        private String rut;
        @Email
        @NotBlank
        private String email;
        @NotBlank
        private String telefono;
        @NotBlank
        private String direccion;
    }
