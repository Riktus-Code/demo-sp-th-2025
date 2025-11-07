package org.iesvdm.demospth2025.modelo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class Comercial {
    private int id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private float comision;

}
