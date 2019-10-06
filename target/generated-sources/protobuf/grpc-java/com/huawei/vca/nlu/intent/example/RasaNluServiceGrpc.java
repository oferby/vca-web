package com.huawei.vca.nlu.intent.example;

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
    comments = "Source: rasa_nlu.proto")
public final class RasaNluServiceGrpc {

  private RasaNluServiceGrpc() {}

  public static final String SERVICE_NAME = "rasa_nlu.RasaNluService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.huawei.vca.nlu.intent.example.NluRequest,
      com.huawei.vca.nlu.intent.example.NluResponse> getSaveToRasaNluMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "saveToRasaNlu",
      requestType = com.huawei.vca.nlu.intent.example.NluRequest.class,
      responseType = com.huawei.vca.nlu.intent.example.NluResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.huawei.vca.nlu.intent.example.NluRequest,
      com.huawei.vca.nlu.intent.example.NluResponse> getSaveToRasaNluMethod() {
    io.grpc.MethodDescriptor<com.huawei.vca.nlu.intent.example.NluRequest, com.huawei.vca.nlu.intent.example.NluResponse> getSaveToRasaNluMethod;
    if ((getSaveToRasaNluMethod = RasaNluServiceGrpc.getSaveToRasaNluMethod) == null) {
      synchronized (RasaNluServiceGrpc.class) {
        if ((getSaveToRasaNluMethod = RasaNluServiceGrpc.getSaveToRasaNluMethod) == null) {
          RasaNluServiceGrpc.getSaveToRasaNluMethod = getSaveToRasaNluMethod = 
              io.grpc.MethodDescriptor.<com.huawei.vca.nlu.intent.example.NluRequest, com.huawei.vca.nlu.intent.example.NluResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rasa_nlu.RasaNluService", "saveToRasaNlu"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.huawei.vca.nlu.intent.example.NluRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.huawei.vca.nlu.intent.example.NluResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RasaNluServiceMethodDescriptorSupplier("saveToRasaNlu"))
                  .build();
          }
        }
     }
     return getSaveToRasaNluMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RasaNluServiceStub newStub(io.grpc.Channel channel) {
    return new RasaNluServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RasaNluServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new RasaNluServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RasaNluServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new RasaNluServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class RasaNluServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void saveToRasaNlu(com.huawei.vca.nlu.intent.example.NluRequest request,
        io.grpc.stub.StreamObserver<com.huawei.vca.nlu.intent.example.NluResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSaveToRasaNluMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSaveToRasaNluMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.huawei.vca.nlu.intent.example.NluRequest,
                com.huawei.vca.nlu.intent.example.NluResponse>(
                  this, METHODID_SAVE_TO_RASA_NLU)))
          .build();
    }
  }

  /**
   */
  public static final class RasaNluServiceStub extends io.grpc.stub.AbstractStub<RasaNluServiceStub> {
    private RasaNluServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RasaNluServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RasaNluServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RasaNluServiceStub(channel, callOptions);
    }

    /**
     */
    public void saveToRasaNlu(com.huawei.vca.nlu.intent.example.NluRequest request,
        io.grpc.stub.StreamObserver<com.huawei.vca.nlu.intent.example.NluResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveToRasaNluMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class RasaNluServiceBlockingStub extends io.grpc.stub.AbstractStub<RasaNluServiceBlockingStub> {
    private RasaNluServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RasaNluServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RasaNluServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RasaNluServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.huawei.vca.nlu.intent.example.NluResponse saveToRasaNlu(com.huawei.vca.nlu.intent.example.NluRequest request) {
      return blockingUnaryCall(
          getChannel(), getSaveToRasaNluMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class RasaNluServiceFutureStub extends io.grpc.stub.AbstractStub<RasaNluServiceFutureStub> {
    private RasaNluServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RasaNluServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RasaNluServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RasaNluServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.huawei.vca.nlu.intent.example.NluResponse> saveToRasaNlu(
        com.huawei.vca.nlu.intent.example.NluRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveToRasaNluMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAVE_TO_RASA_NLU = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RasaNluServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(RasaNluServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAVE_TO_RASA_NLU:
          serviceImpl.saveToRasaNlu((com.huawei.vca.nlu.intent.example.NluRequest) request,
              (io.grpc.stub.StreamObserver<com.huawei.vca.nlu.intent.example.NluResponse>) responseObserver);
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

  private static abstract class RasaNluServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RasaNluServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.huawei.vca.nlu.intent.example.IntentExampleProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RasaNluService");
    }
  }

  private static final class RasaNluServiceFileDescriptorSupplier
      extends RasaNluServiceBaseDescriptorSupplier {
    RasaNluServiceFileDescriptorSupplier() {}
  }

  private static final class RasaNluServiceMethodDescriptorSupplier
      extends RasaNluServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    RasaNluServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (RasaNluServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RasaNluServiceFileDescriptorSupplier())
              .addMethod(getSaveToRasaNluMethod())
              .build();
        }
      }
    }
    return result;
  }
}
