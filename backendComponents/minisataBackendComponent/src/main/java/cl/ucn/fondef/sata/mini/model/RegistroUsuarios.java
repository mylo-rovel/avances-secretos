/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "registrousuarios")
@ToString
@EqualsAndHashCode
@IdClass(RegistroUsuariosId.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroUsuarios{

    @Id
    @Getter
    @Setter
    @Column(name = "rut_administrador")
    private String rutAdministrador;

    @Id
    @Getter
    @Setter
    @Column(name = "rut_usuario")
    private String rutUsuario;
}
