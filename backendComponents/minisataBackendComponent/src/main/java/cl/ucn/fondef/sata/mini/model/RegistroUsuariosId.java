/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.model;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
// https://www.baeldung.com/jpa-composite-primary-keys
// este archivo es necesario para implementar una doble PK en el modelo RegistroUsuarios
// va de la mano con eses archivo
@NoArgsConstructor
@Builder
public class RegistroUsuariosId implements Serializable {
    private String rutAdministrador;
    private String rutUsuario;

    public RegistroUsuariosId(String rutAdministrador, String rutUsuario) {
        this.rutAdministrador = rutAdministrador;
        this.rutUsuario = rutUsuario;
    }
}
