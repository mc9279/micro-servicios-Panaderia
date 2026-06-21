package cl.duoc.panadero_service.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Panadero {@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    private String nombre;
    private String especialidad;
    private String experiencia;

}
