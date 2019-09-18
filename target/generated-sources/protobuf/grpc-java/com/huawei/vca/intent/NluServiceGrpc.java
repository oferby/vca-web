package com.huawei.vca.intent;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.19.0)",
    comments = "Source: intent.proto")
public final class NluServiceGrpc {

  private NluServiceGrpc() {}

  public static final String SERVICE_NAME = "intent.NluService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.huawei.vca.intent.NluRequest,
      com.huawei.vca.intent.NluResponse> getGetNluResponseMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getNluResponse",
      requestType = com.huawei.vca.intent.NluRequest.class,
      responseType = com.huawei.vca.intent.NluResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.huawei.vca.intent.NluRequest,
      com.huawei.vca.intent.NluResponse> getGetNluResponseMethod() {
    io.grpc.MethodDescriptor<com.huawei.vca.intent.NluRequest, com.huawei.vca.intent.NluResponse> getGetNluResponseMethod;
    if ((getGetNluResponseMethod = NluServiceGrpc.getGetNluResponseMethod) == null) {
      synchronized (NluServiceGrpc.class) {
        if ((getGetNluResponseMethod = NluServiceGrpc.getGetNluResponseMethod) == null) {
          NluServiceGrpc.getGetNluResponseMethod = getGetNluResponseMethod = 
              io.grpc.MethodDescriptor.<com.huawei.vca.intent.NluRequest, com.huawei.vca.intent.NluResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "intent.NluService", "getNluResponse"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.huawei.vca.intent.NluRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.huawei.vca.intent.NluResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new NluServiceMethodDescriptorSupplier("getNluResponse"))
                  .build();
          }
        }
     }
     return getGetNluResponseMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NluServiceStub newStub(io.grpc.Channel channel) {
    return new NluServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NluServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new NluServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NluServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new NluServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class NluServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getNluResponse(com.huawei.vca.intent.NluRequest request,
        io.grpc.stub.StreamObserver<com.huawei.vca.intent.NluResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetNluResponseMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetNluResponseMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.huawei.vca.intent.NluRequest,
                com.huawei.vca.intent.NluResponse>(
                  this, METHODID_GET_NLU_RESPONSE)))
          .build();
    }
  }

  /**
   */
  public static final class NluServiceStub extends io.grpc.stub.AbstractStub<NluServiceStub> {
    private NluServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NluServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NluServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NluServiceStub(channel, callOptions);
    }

    /**
     */
    public void getNluResponse(com.huawei.vca.intent.NluRequest request,
        io.grpc.stub.StreamObserver<com.huawei.vca.intent.NluResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetNluResponseMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class NluServiceBlockingStub extends io.grpc.stub.AbstractStub<NluServiceBlockingStub> {
    private NluServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NluServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NluServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NluServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.huawei.vca.intent.NluResponse getNluResponse(com.huawei.vca.intent.NluRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetNluResponseMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class NluServiceFutureStub extends io.grpc.stub.AbstractStub<NluServiceFutureStub> {
    private NluServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NluServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NluServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NluServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.huawei.vca.intent.NluResponse> getNluResponse(
        com.huawei.vca.intent.NluRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetNluResponseMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_NLU_RESPONSE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final NluServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(NluServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_NLU_RESPONSE:
          serviceImpl.getNluResponse((com.huawei.vca.intent.NluRequest) request,
              (io.grpc.stub.StreamObserver<com.huawei.vca.intent.NluResponse>) responseObserver);
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

  private static abstract class NluServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    NluServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.huawei.vca.intent.IntentProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("NluService");
    }
  }

  private static final class NluServiceFileDescriptorSupplier
      extends NluServiceBaseDescriptorSupplier {
    NluServiceFileDescriptorSupplier() {}
  }

  private static final class NluServiceMethodDescriptorSupplier
      extends NluServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    NluServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (NluServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NluServiceFileDescriptorSupplier())
              .addMethod(getGetNluResponseMethod())
              .build();
        }
      }
    }
    return result;
  }
}
