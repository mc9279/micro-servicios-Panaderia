package cl.duoc.proveedores_service.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proveedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proveedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la empresa no puede estar vacío")
    @Size(min = 3, max = 100, message = "Debe tener entre 3 y 100 caracteres")
    @Column(name = "nombre_empresa", nullable = false)
    private String nombreEmpresa;

    @NotBlank(message = "El rut no puede estar vacío")
    @Size(min = 8, max = 12, message = "RUT inválido")
    @Column(nullable = false, unique = true)
    private String rut;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(min = 8, max = 15, message = "Teléfono inválido")
    @Column(nullable = false)
    private String telefono;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Formato de email inválido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(min = 5, max = 200, message = "Dirección inválida")
    @Column(nullable = false)
    private String direccion;
}