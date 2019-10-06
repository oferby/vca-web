// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: rasa_nlu.proto

package com.huawei.vca.nlu.intent.example;

/**
 * Protobuf type {@code rasa_nlu.RasaData}
 */
public  final class RasaData extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:rasa_nlu.RasaData)
    RasaDataOrBuilder {
private static final long serialVersionUID = 0L;
  // Use RasaData.newBuilder() to construct.
  private RasaData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private RasaData() {
    commonExamples_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private RasaData(
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
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              commonExamples_ = new java.util.ArrayList<com.huawei.vca.nlu.intent.example.CommonExample>();
              mutable_bitField0_ |= 0x00000001;
            }
            commonExamples_.add(
                input.readMessage(com.huawei.vca.nlu.intent.example.CommonExample.parser(), extensionRegistry));
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
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        commonExamples_ = java.util.Collections.unmodifiableList(commonExamples_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.huawei.vca.nlu.intent.example.IntentExampleProto.internal_static_rasa_nlu_RasaData_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.huawei.vca.nlu.intent.example.IntentExampleProto.internal_static_rasa_nlu_RasaData_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.huawei.vca.nlu.intent.example.RasaData.class, com.huawei.vca.nlu.intent.example.RasaData.Builder.class);
  }

  public static final int COMMON_EXAMPLES_FIELD_NUMBER = 1;
  private java.util.List<com.huawei.vca.nlu.intent.example.CommonExample> commonExamples_;
  /**
   * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
   */
  public java.util.List<com.huawei.vca.nlu.intent.example.CommonExample> getCommonExamplesList() {
    return commonExamples_;
  }
  /**
   * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
   */
  public java.util.List<? extends com.huawei.vca.nlu.intent.example.CommonExampleOrBuilder> 
      getCommonExamplesOrBuilderList() {
    return commonExamples_;
  }
  /**
   * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
   */
  public int getCommonExamplesCount() {
    return commonExamples_.size();
  }
  /**
   * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
   */
  public com.huawei.vca.nlu.intent.example.CommonExample getCommonExamples(int index) {
    return commonExamples_.get(index);
  }
  /**
   * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
   */
  public com.huawei.vca.nlu.intent.example.CommonExampleOrBuilder getCommonExamplesOrBuilder(
      int index) {
    return commonExamples_.get(index);
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
    for (int i = 0; i < commonExamples_.size(); i++) {
      output.writeMessage(1, commonExamples_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < commonExamples_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, commonExamples_.get(i));
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
    if (!(obj instanceof com.huawei.vca.nlu.intent.example.RasaData)) {
      return super.equals(obj);
    }
    com.huawei.vca.nlu.intent.example.RasaData other = (com.huawei.vca.nlu.intent.example.RasaData) obj;

    if (!getCommonExamplesList()
        .equals(other.getCommonExamplesList())) return false;
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
    if (getCommonExamplesCount() > 0) {
      hash = (37 * hash) + COMMON_EXAMPLES_FIELD_NUMBER;
      hash = (53 * hash) + getCommonExamplesList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.huawei.vca.nlu.intent.example.RasaData parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.huawei.vca.nlu.intent.example.RasaData parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.huawei.vca.nlu.intent.example.RasaData parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.huawei.vca.nlu.intent.example.RasaData parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.huawei.vca.nlu.intent.example.RasaData parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.huawei.vca.nlu.intent.example.RasaData parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.huawei.vca.nlu.intent.example.RasaData parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.huawei.vca.nlu.intent.example.RasaData parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.huawei.vca.nlu.intent.example.RasaData parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.huawei.vca.nlu.intent.example.RasaData parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.huawei.vca.nlu.intent.example.RasaData parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.huawei.vca.nlu.intent.example.RasaData parseFrom(
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
  public static Builder newBuilder(com.huawei.vca.nlu.intent.example.RasaData prototype) {
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
   * Protobuf type {@code rasa_nlu.RasaData}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:rasa_nlu.RasaData)
      com.huawei.vca.nlu.intent.example.RasaDataOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.huawei.vca.nlu.intent.example.IntentExampleProto.internal_static_rasa_nlu_RasaData_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.huawei.vca.nlu.intent.example.IntentExampleProto.internal_static_rasa_nlu_RasaData_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.huawei.vca.nlu.intent.example.RasaData.class, com.huawei.vca.nlu.intent.example.RasaData.Builder.class);
    }

    // Construct using com.huawei.vca.nlu.intent.example.RasaData.newBuilder()
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
        getCommonExamplesFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (commonExamplesBuilder_ == null) {
        commonExamples_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        commonExamplesBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.huawei.vca.nlu.intent.example.IntentExampleProto.internal_static_rasa_nlu_RasaData_descriptor;
    }

    @java.lang.Override
    public com.huawei.vca.nlu.intent.example.RasaData getDefaultInstanceForType() {
      return com.huawei.vca.nlu.intent.example.RasaData.getDefaultInstance();
    }

    @java.lang.Override
    public com.huawei.vca.nlu.intent.example.RasaData build() {
      com.huawei.vca.nlu.intent.example.RasaData result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.huawei.vca.nlu.intent.example.RasaData buildPartial() {
      com.huawei.vca.nlu.intent.example.RasaData result = new com.huawei.vca.nlu.intent.example.RasaData(this);
      int from_bitField0_ = bitField0_;
      if (commonExamplesBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          commonExamples_ = java.util.Collections.unmodifiableList(commonExamples_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.commonExamples_ = commonExamples_;
      } else {
        result.commonExamples_ = commonExamplesBuilder_.build();
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
      if (other instanceof com.huawei.vca.nlu.intent.example.RasaData) {
        return mergeFrom((com.huawei.vca.nlu.intent.example.RasaData)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.huawei.vca.nlu.intent.example.RasaData other) {
      if (other == com.huawei.vca.nlu.intent.example.RasaData.getDefaultInstance()) return this;
      if (commonExamplesBuilder_ == null) {
        if (!other.commonExamples_.isEmpty()) {
          if (commonExamples_.isEmpty()) {
            commonExamples_ = other.commonExamples_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureCommonExamplesIsMutable();
            commonExamples_.addAll(other.commonExamples_);
          }
          onChanged();
        }
      } else {
        if (!other.commonExamples_.isEmpty()) {
          if (commonExamplesBuilder_.isEmpty()) {
            commonExamplesBuilder_.dispose();
            commonExamplesBuilder_ = null;
            commonExamples_ = other.commonExamples_;
            bitField0_ = (bitField0_ & ~0x00000001);
            commonExamplesBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getCommonExamplesFieldBuilder() : null;
          } else {
            commonExamplesBuilder_.addAllMessages(other.commonExamples_);
          }
        }
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
      com.huawei.vca.nlu.intent.example.RasaData parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.huawei.vca.nlu.intent.example.RasaData) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<com.huawei.vca.nlu.intent.example.CommonExample> commonExamples_ =
      java.util.Collections.emptyList();
    private void ensureCommonExamplesIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        commonExamples_ = new java.util.ArrayList<com.huawei.vca.nlu.intent.example.CommonExample>(commonExamples_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.huawei.vca.nlu.intent.example.CommonExample, com.huawei.vca.nlu.intent.example.CommonExample.Builder, com.huawei.vca.nlu.intent.example.CommonExampleOrBuilder> commonExamplesBuilder_;

    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public java.util.List<com.huawei.vca.nlu.intent.example.CommonExample> getCommonExamplesList() {
      if (commonExamplesBuilder_ == null) {
        return java.util.Collections.unmodifiableList(commonExamples_);
      } else {
        return commonExamplesBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public int getCommonExamplesCount() {
      if (commonExamplesBuilder_ == null) {
        return commonExamples_.size();
      } else {
        return commonExamplesBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public com.huawei.vca.nlu.intent.example.CommonExample getCommonExamples(int index) {
      if (commonExamplesBuilder_ == null) {
        return commonExamples_.get(index);
      } else {
        return commonExamplesBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public Builder setCommonExamples(
        int index, com.huawei.vca.nlu.intent.example.CommonExample value) {
      if (commonExamplesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureCommonExamplesIsMutable();
        commonExamples_.set(index, value);
        onChanged();
      } else {
        commonExamplesBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public Builder setCommonExamples(
        int index, com.huawei.vca.nlu.intent.example.CommonExample.Builder builderForValue) {
      if (commonExamplesBuilder_ == null) {
        ensureCommonExamplesIsMutable();
        commonExamples_.set(index, builderForValue.build());
        onChanged();
      } else {
        commonExamplesBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public Builder addCommonExamples(com.huawei.vca.nlu.intent.example.CommonExample value) {
      if (commonExamplesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureCommonExamplesIsMutable();
        commonExamples_.add(value);
        onChanged();
      } else {
        commonExamplesBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public Builder addCommonExamples(
        int index, com.huawei.vca.nlu.intent.example.CommonExample value) {
      if (commonExamplesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureCommonExamplesIsMutable();
        commonExamples_.add(index, value);
        onChanged();
      } else {
        commonExamplesBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public Builder addCommonExamples(
        com.huawei.vca.nlu.intent.example.CommonExample.Builder builderForValue) {
      if (commonExamplesBuilder_ == null) {
        ensureCommonExamplesIsMutable();
        commonExamples_.add(builderForValue.build());
        onChanged();
      } else {
        commonExamplesBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public Builder addCommonExamples(
        int index, com.huawei.vca.nlu.intent.example.CommonExample.Builder builderForValue) {
      if (commonExamplesBuilder_ == null) {
        ensureCommonExamplesIsMutable();
        commonExamples_.add(index, builderForValue.build());
        onChanged();
      } else {
        commonExamplesBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public Builder addAllCommonExamples(
        java.lang.Iterable<? extends com.huawei.vca.nlu.intent.example.CommonExample> values) {
      if (commonExamplesBuilder_ == null) {
        ensureCommonExamplesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, commonExamples_);
        onChanged();
      } else {
        commonExamplesBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public Builder clearCommonExamples() {
      if (commonExamplesBuilder_ == null) {
        commonExamples_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        commonExamplesBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public Builder removeCommonExamples(int index) {
      if (commonExamplesBuilder_ == null) {
        ensureCommonExamplesIsMutable();
        commonExamples_.remove(index);
        onChanged();
      } else {
        commonExamplesBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public com.huawei.vca.nlu.intent.example.CommonExample.Builder getCommonExamplesBuilder(
        int index) {
      return getCommonExamplesFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public com.huawei.vca.nlu.intent.example.CommonExampleOrBuilder getCommonExamplesOrBuilder(
        int index) {
      if (commonExamplesBuilder_ == null) {
        return commonExamples_.get(index);  } else {
        return commonExamplesBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public java.util.List<? extends com.huawei.vca.nlu.intent.example.CommonExampleOrBuilder> 
         getCommonExamplesOrBuilderList() {
      if (commonExamplesBuilder_ != null) {
        return commonExamplesBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(commonExamples_);
      }
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public com.huawei.vca.nlu.intent.example.CommonExample.Builder addCommonExamplesBuilder() {
      return getCommonExamplesFieldBuilder().addBuilder(
          com.huawei.vca.nlu.intent.example.CommonExample.getDefaultInstance());
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public com.huawei.vca.nlu.intent.example.CommonExample.Builder addCommonExamplesBuilder(
        int index) {
      return getCommonExamplesFieldBuilder().addBuilder(
          index, com.huawei.vca.nlu.intent.example.CommonExample.getDefaultInstance());
    }
    /**
     * <code>repeated .rasa_nlu.CommonExample common_examples = 1;</code>
     */
    public java.util.List<com.huawei.vca.nlu.intent.example.CommonExample.Builder> 
         getCommonExamplesBuilderList() {
      return getCommonExamplesFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.huawei.vca.nlu.intent.example.CommonExample, com.huawei.vca.nlu.intent.example.CommonExample.Builder, com.huawei.vca.nlu.intent.example.CommonExampleOrBuilder> 
        getCommonExamplesFieldBuilder() {
      if (commonExamplesBuilder_ == null) {
        commonExamplesBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            com.huawei.vca.nlu.intent.example.CommonExample, com.huawei.vca.nlu.intent.example.CommonExample.Builder, com.huawei.vca.nlu.intent.example.CommonExampleOrBuilder>(
                commonExamples_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        commonExamples_ = null;
      }
      return commonExamplesBuilder_;
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


    // @@protoc_insertion_point(builder_scope:rasa_nlu.RasaData)
  }

  // @@protoc_insertion_point(class_scope:rasa_nlu.RasaData)
  private static final com.huawei.vca.nlu.intent.example.RasaData DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.huawei.vca.nlu.intent.example.RasaData();
  }

  public static com.huawei.vca.nlu.intent.example.RasaData getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RasaData>
      PARSER = new com.google.protobuf.AbstractParser<RasaData>() {
    @java.lang.Override
    public RasaData parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new RasaData(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<RasaData> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<RasaData> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.huawei.vca.nlu.intent.example.RasaData getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

