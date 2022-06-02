package com.backendCore.gRPCcore.utilsClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
public class grpcTextMessage {

    @Getter @Setter
    private String message;
}
