// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: intent.proto

package com.huawei.vca.intent;

public interface EntityOrBuilder extends
    // @@protoc_insertion_point(interface_extends:intent.Entity)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 start = 1;</code>
   */
  int getStart();

  /**
   * <code>int32 end = 2;</code>
   */
  int getEnd();

  /**
   * <code>string value = 3;</code>
   */
  java.lang.String getValue();
  /**
   * <code>string value = 3;</code>
   */
  com.google.protobuf.ByteString
      getValueBytes();

  /**
   * <code>string entity = 4;</code>
   */
  java.lang.String getEntity();
  /**
   * <code>string entity = 4;</code>
   */
  com.google.protobuf.ByteString
      getEntityBytes();

  /**
   * <code>float confidence = 5;</code>
   */
  float getConfidence();

  /**
   * <code>string extractor = 6;</code>
   */
  java.lang.String getExtractor();
  /**
   * <code>string extractor = 6;</code>
   */
  com.google.protobuf.ByteString
      getExtractorBytes();
}
