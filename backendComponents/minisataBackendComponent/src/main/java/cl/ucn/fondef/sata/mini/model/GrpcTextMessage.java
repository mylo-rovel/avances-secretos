/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.model;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString @EqualsAndHashCode
public class GrpcTextMessage {

    /**
     * The Name.
     */
    @Getter
    @Setter
    @NonNull
    private String message;

}

