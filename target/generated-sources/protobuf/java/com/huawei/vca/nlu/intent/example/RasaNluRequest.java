// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: rasa_nlu.proto

package com.huawei.vca.nlu.intent.example;

/**
 * Protobuf type {@code rasa_nlu.RasaNluRequest}
 */
public  final class RasaNluRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:rasa_nlu.RasaNluRequest)
    RasaNluRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use RasaNluRequest.newBuilder() to construct.
  private RasaNluRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private RasaNluRequest() {
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private RasaNluRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            com.huawei.vca.nlu.intent.example.RasaData.Builder subBuilder = null;
            if (rasaNluData_ != null) {
              subBuilder = rasaNluData_.toBuilder();
            }
            rasaNluData_ = input.readMessage(com.huawei.vca.nlu.intent.example.RasaData.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(rasaNluData_);
              rasaNluData_ = subBuilder.buildPartial();
            }

            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.huawei.vca.nlu.intent.example.IntentExampleProto.internal_static_rasa_nlu_RasaNluRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.huawei.vca.nlu.intent.example.IntentExampleProto.internal_static_rasa_nlu_RasaNluRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.huawei.vca.nlu.intent.example.RasaNluRequest.class, com.huawei.vca.nlu.intent.example.RasaNluRequest.Builder.class);
  }

  public static final int RASA_NLU_DATA_FIELD_NUMBER = 1;
  private com.huawei.vca.nlu.intent.example.RasaData rasaNluData_;
  /**
   * <code>.rasa_nlu.RasaData rasa_nlu_data = 1;</code>
   */
  public boolean hasRasaNluData() {
    return rasaNluData_ != null;
  }
  /**
   * <code>.rasa_nlu.RasaData rasa_nlu_data = 1;</code>
   */
  public com.huawei.vca.nlu.intent.example.RasaData getRasaNluData() {
    return rasaNluData_ == null ? com.huawei.vca.nlu.intent.example.RasaData.getDefaultInstance() : rasaNluData_;
  }
  /**
   * <code>.rasa_nlu.RasaData rasa_nlu_data = 1;</code>
   */
  public com.huawei.vca.nlu.intent.example.RasaDataOrBuilder getRasaNluDataOrBuilder() {
    return getRasaNluData();
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (rasaNluData_ != null) {
      output.writeMessage(1, getRasaNluData());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (rasaNluData_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getRasaNluData());
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.huawei.vca.nlu.intent.example.RasaNluRequest)) {
      return super.equals(obj);
    }
    com.huawei.vca.nlu.intent.example.RasaNluRequest other = (com.huawei.vca.nlu.intent.example.RasaNluRequest) obj;

    if (hasRasaNluData() != other.hasRasaNluData()) return false;
    if (hasRasaNluData()) {
      if (!getRasaNluData()
          .equals(other.getRasaNluData())) return false;
    }
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasRasaNluData()) {
      hash = (37 * hash) + RASA_NLU_DATA_FIELD_NUMBER;
      hash = (53 * hash) + getRasaNluData().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.huawei.vca.nlu.intent.example.RasaNluRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.huawei.vca.nlu.intent.example.RasaNluRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.huawei.vca.nlu.intent.example.RasaNluRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.huawei.vca.nlu.intent.example.RasaNluRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.huawei.vca.nlu.intent.example.RasaNluRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.huawei.vca.nlu.intent.example.RasaNluRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.huawei.vca.nlu.intent.example.RasaNluRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.huawei.vca.nlu.intent.example.RasaNluRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.huawei.vca.nlu.intent.example.RasaNluRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.huawei.vca.nlu.intent.example.RasaNluRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.huawei.vca.nlu.intent.example.RasaNluRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.huawei.vca.nlu.intent.example.RasaNluRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.huawei.vca.nlu.intent.example.RasaNluRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code rasa_nlu.RasaNluRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:rasa_nlu.RasaNluRequest)
      com.huawei.vca.nlu.intent.example.RasaNluRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.huawei.vca.nlu.intent.example.IntentExampleProto.internal_static_rasa_nlu_RasaNluRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.huawei.vca.nlu.intent.example.IntentExampleProto.internal_static_rasa_nlu_RasaNluRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.huawei.vca.nlu.intent.example.RasaNluRequest.class, com.huawei.vca.nlu.intent.example.RasaNluRequest.Builder.class);
    }

    // Construct using com.huawei.vca.nlu.intent.example.RasaNluRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (rasaNluDataBuilder_ == null) {
        rasaNluData_ = null;
      } else {
        rasaNluData_ = null;
        rasaNluDataBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.huawei.vca.nlu.intent.example.IntentExampleProto.internal_static_rasa_nlu_RasaNluRequest_descriptor;
    }

    @java.lang.Override
    public com.huawei.vca.nlu.intent.example.RasaNluRequest getDefaultInstanceForType() {
      return com.huawei.vca.nlu.intent.example.RasaNluRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.huawei.vca.nlu.intent.example.RasaNluRequest build() {
      com.huawei.vca.nlu.intent.example.RasaNluRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.huawei.vca.nlu.intent.example.RasaNluRequest buildPartial() {
      com.huawei.vca.nlu.intent.example.RasaNluRequest result = new com.huawei.vca.nlu.intent.example.RasaNluRequest(this);
      if (rasaNluDataBuilder_ == null) {
        result.rasaNluData_ = rasaNluData_;
      } else {
        result.rasaNluData_ = rasaNluDataBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.huawei.vca.nlu.intent.example.RasaNluRequest) {
        return mergeFrom((com.huawei.vca.nlu.intent.example.RasaNluRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.huawei.vca.nlu.intent.example.RasaNluRequest other) {
      if (other == com.huawei.vca.nlu.intent.example.RasaNluRequest.getDefaultInstance()) return this;
      if (other.hasRasaNluData()) {
        mergeRasaNluData(other.getRasaNluData());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.huawei.vca.nlu.intent.example.RasaNluRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.huawei.vca.nlu.intent.example.RasaNluRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.huawei.vca.nlu.intent.example.RasaData rasaNluData_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.huawei.vca.nlu.intent.example.RasaData, com.huawei.vca.nlu.intent.example.RasaData.Builder, com.huawei.vca.nlu.intent.example.RasaDataOrBuilder> rasaNluDataBuilder_;
    /**
     * <code>.rasa_nlu.RasaData rasa_nlu_data = 1;</code>
     */
    public boolean hasRasaNluData() {
      return rasaNluDataBuilder_ != null || rasaNluData_ != null;
    }
    /**
     * <code>.rasa_nlu.RasaData rasa_nlu_data = 1;</code>
     */
    public com.huawei.vca.nlu.intent.example.RasaData getRasaNluData() {
      if (rasaNluDataBuilder_ == null) {
        return rasaNluData_ == null ? com.huawei.vca.nlu.intent.example.RasaData.getDefaultInstance() : rasaNluData_;
      } else {
        return rasaNluDataBuilder_.getMessage();
      }
    }
    /**
     * <code>.rasa_nlu.RasaData rasa_nlu_data = 1;</code>
     */
    public Builder setRasaNluData(com.huawei.vca.nlu.intent.example.RasaData value) {
      if (rasaNluDataBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        rasaNluData_ = value;
        onChanged();
      } else {
        rasaNluDataBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.rasa_nlu.RasaData rasa_nlu_data = 1;</code>
     */
    public Builder setRasaNluData(
        com.huawei.vca.nlu.intent.example.RasaData.Builder builderForValue) {
      if (rasaNluDataBuilder_ == null) {
        rasaNluData_ = builderForValue.build();
        onChanged();
      } else {
        rasaNluDataBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.rasa_nlu.RasaData rasa_nlu_data = 1;</code>
     */
    public Builder mergeRasaNluData(com.huawei.vca.nlu.intent.example.RasaData value) {
      if (rasaNluDataBuilder_ == null) {
        if (rasaNluData_ != null) {
          rasaNluData_ =
            com.huawei.vca.nlu.intent.example.RasaData.newBuilder(rasaNluData_).mergeFrom(value).buildPartial();
        } else {
          rasaNluData_ = value;
        }
        onChanged();
      } else {
        rasaNluDataBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.rasa_nlu.RasaData rasa_nlu_data = 1;</code>
     */
    public Builder clearRasaNluData() {
      if (rasaNluDataBuilder_ == null) {
        rasaNluData_ = null;
        onChanged();
      } else {
        rasaNluData_ = null;
        rasaNluDataBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.rasa_nlu.RasaData rasa_nlu_data = 1;</code>
     */
    public com.huawei.vca.nlu.intent.example.RasaData.Builder getRasaNluDataBuilder() {
      
      onChanged();
      return getRasaNluDataFieldBuilder().getBuilder();
    }
    /**
     * <code>.rasa_nlu.RasaData rasa_nlu_data = 1;</code>
     */
    public com.huawei.vca.nlu.intent.example.RasaDataOrBuilder getRasaNluDataOrBuilder() {
      if (rasaNluDataBuilder_ != null) {
        return rasaNluDataBuilder_.getMessageOrBuilder();
      } else {
        return rasaNluData_ == null ?
            com.huawei.vca.nlu.intent.example.RasaData.getDefaultInstance() : rasaNluData_;
      }
    }
    /**
     * <code>.rasa_nlu.RasaData rasa_nlu_data = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.huawei.vca.nlu.intent.example.RasaData, com.huawei.vca.nlu.intent.example.RasaData.Builder, com.huawei.vca.nlu.intent.example.RasaDataOrBuilder> 
        getRasaNluDataFieldBuilder() {
      if (rasaNluDataBuilder_ == null) {
        rasaNluDataBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.huawei.vca.nlu.intent.example.RasaData, com.huawei.vca.nlu.intent.example.RasaData.Builder, com.huawei.vca.nlu.intent.example.RasaDataOrBuilder>(
                getRasaNluData(),
                getParentForChildren(),
                isClean());
        rasaNluData_ = null;
      }
      return rasaNluDataBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:rasa_nlu.RasaNluRequest)
  }

  // @@protoc_insertion_point(class_scope:rasa_nlu.RasaNluRequest)
  private static final com.huawei.vca.nlu.intent.example.RasaNluRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.huawei.vca.nlu.intent.example.RasaNluRequest();
  }

  public static com.huawei.vca.nlu.intent.example.RasaNluRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RasaNluRequest>
      PARSER = new com.google.protobuf.AbstractParser<RasaNluRequest>() {
    @java.lang.Override
    public RasaNluRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new RasaNluRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<RasaNluRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<RasaNluRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.huawei.vca.nlu.intent.example.RasaNluRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
