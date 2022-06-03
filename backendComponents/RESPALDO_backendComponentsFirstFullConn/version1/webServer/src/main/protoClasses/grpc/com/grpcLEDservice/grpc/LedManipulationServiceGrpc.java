package com.grpcLEDservice.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.1)",
    comments = "Source: node_conn.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class LedManipulationServiceGrpc {

  private LedManipulationServiceGrpc() {}

  public static final String SERVICE_NAME = "LedManipulationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.grpcLEDservice.grpc.TextMessage,
      com.grpcLEDservice.grpc.TextMessage> getStartLedPerformanceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StartLedPerformance",
      requestType = com.grpcLEDservice.grpc.TextMessage.class,
      responseType = com.grpcLEDservice.grpc.TextMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.grpcLEDservice.grpc.TextMessage,
      com.grpcLEDservice.grpc.TextMessage> getStartLedPerformanceMethod() {
    io.grpc.MethodDescriptor<com.grpcLEDservice.grpc.TextMessage, com.grpcLEDservice.grpc.TextMessage> getStartLedPerformanceMethod;
    if ((getStartLedPerformanceMethod = LedManipulationServiceGrpc.getStartLedPerformanceMethod) == null) {
      synchronized (LedManipulationServiceGrpc.class) {
        if ((getStartLedPerformanceMethod = LedManipulationServiceGrpc.getStartLedPerformanceMethod) == null) {
          LedManipulationServiceGrpc.getStartLedPerformanceMethod = getStartLedPerformanceMethod =
              io.grpc.MethodDescriptor.<com.grpcLEDservice.grpc.TextMessage, com.grpcLEDservice.grpc.TextMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StartLedPerformance"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.grpcLEDservice.grpc.TextMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.grpcLEDservice.grpc.TextMessage.getDefaultInstance()))
              .build();
        }
      }
    }
    return getStartLedPerformanceMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LedManipulationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LedManipulationServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LedManipulationServiceStub>() {
        @java.lang.Override
        public LedManipulationServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LedManipulationServiceStub(channel, callOptions);
        }
      };
    return LedManipulationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LedManipulationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LedManipulationServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LedManipulationServiceBlockingStub>() {
        @java.lang.Override
        public LedManipulationServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LedManipulationServiceBlockingStub(channel, callOptions);
        }
      };
    return LedManipulationServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LedManipulationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LedManipulationServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LedManipulationServiceFutureStub>() {
        @java.lang.Override
        public LedManipulationServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LedManipulationServiceFutureStub(channel, callOptions);
        }
      };
    return LedManipulationServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class LedManipulationServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void startLedPerformance(com.grpcLEDservice.grpc.TextMessage request,
        io.grpc.stub.StreamObserver<com.grpcLEDservice.grpc.TextMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStartLedPerformanceMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getStartLedPerformanceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.grpcLEDservice.grpc.TextMessage,
                com.grpcLEDservice.grpc.TextMessage>(
                  this, METHODID_START_LED_PERFORMANCE)))
          .build();
    }
  }

  /**
   */
  public static final class LedManipulationServiceStub extends io.grpc.stub.AbstractAsyncStub<LedManipulationServiceStub> {
    private LedManipulationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LedManipulationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LedManipulationServiceStub(channel, callOptions);
    }

    /**
     */
    public void startLedPerformance(com.grpcLEDservice.grpc.TextMessage request,
        io.grpc.stub.StreamObserver<com.grpcLEDservice.grpc.TextMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStartLedPerformanceMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class LedManipulationServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<LedManipulationServiceBlockingStub> {
    private LedManipulationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LedManipulationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LedManipulationServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.grpcLEDservice.grpc.TextMessage startLedPerformance(com.grpcLEDservice.grpc.TextMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStartLedPerformanceMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class LedManipulationServiceFutureStub extends io.grpc.stub.AbstractFutureStub<LedManipulationServiceFutureStub> {
    private LedManipulationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LedManipulationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LedManipulationServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.grpcLEDservice.grpc.TextMessage> startLedPerformance(
        com.grpcLEDservice.grpc.TextMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStartLedPerformanceMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_START_LED_PERFORMANCE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LedManipulationServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LedManipulationServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_START_LED_PERFORMANCE:
          serviceImpl.startLedPerformance((com.grpcLEDservice.grpc.TextMessage) request,
              (io.grpc.stub.StreamObserver<com.grpcLEDservice.grpc.TextMessage>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (LedManipulationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getStartLedPerformanceMethod())
              .build();
        }
      }
    }
    return result;
  }
}
