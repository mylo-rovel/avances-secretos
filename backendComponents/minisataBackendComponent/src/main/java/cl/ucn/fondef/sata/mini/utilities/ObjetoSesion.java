/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.utilities;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ObjetoSesion {
    /*      OBJETO RPC QUE SE RECIBIRÍA DESDE EL CENTRAL CORE AL INICIAR SESION
            message ObjetoSesion {
                bool sesion_iniciada = 1;
                string Json_Web_Token = 2;
                string rol_usuario = 3;
            }
    */
    @Getter
    @Setter
    @NonNull
    private boolean sesionIniciada;

    @Getter
    @Setter
    private String jsonWebToken;

    @Getter
    @Setter
    private String rolUsuario;
}
