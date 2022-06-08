package com.backendCore.gRPCcore.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
public class GrpcTextMessage {

    @Getter @Setter
    private String message;
}
