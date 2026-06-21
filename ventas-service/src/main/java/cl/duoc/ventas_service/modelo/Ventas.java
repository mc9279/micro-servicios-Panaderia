package cl.duoc.ventas_service.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Ventas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;
    private Double total;

    @Column(name="metodo_pago")
    private String metodoPago;

    private String estado;


    private Long clienteId;
    private Long productoId;
}
