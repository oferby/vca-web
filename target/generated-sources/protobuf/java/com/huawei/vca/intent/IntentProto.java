// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: intent.proto

package com.huawei.vca.intent;

public final class IntentProto {
  private IntentProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_intent_NluRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_intent_NluRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_intent_Intent_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_intent_Intent_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_intent_Entity_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_intent_Entity_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_intent_Rank_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_intent_Rank_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_intent_NluResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_intent_NluResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014intent.proto\022\006intent\"-\n\nNluRequest\022\014\n\004" +
      "text\030\001 \001(\t\022\021\n\tsessionId\030\002 \001(\t\"*\n\006Intent\022" +
      "\014\n\004name\030\001 \001(\t\022\022\n\nconfidence\030\002 \001(\002\"j\n\006Ent" +
      "ity\022\r\n\005start\030\001 \001(\005\022\013\n\003end\030\002 \001(\005\022\r\n\005value" +
      "\030\003 \001(\t\022\016\n\006entity\030\004 \001(\t\022\022\n\nconfidence\030\005 \001" +
      "(\002\022\021\n\textractor\030\006 \001(\t\"(\n\004Rank\022\014\n\004name\030\001 " +
      "\001(\t\022\022\n\nconfidence\030\002 \001(\002\"\201\001\n\013NluResponse\022" +
      "\021\n\tsessionId\030\001 \001(\t\022\036\n\006intent\030\002 \001(\0132\016.int" +
      "ent.Intent\022 \n\010entities\030\003 \003(\0132\016.intent.En" +
      "tity\022\035\n\007ranking\030\004 \003(\0132\014.intent.Rank2I\n\nN" +
      "luService\022;\n\016getNluResponse\022\022.intent.Nlu" +
      "Request\032\023.intent.NluResponse\"\000B&\n\025com.hu" +
      "awei.vca.intentB\013IntentProtoP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_intent_NluRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_intent_NluRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_intent_NluRequest_descriptor,
        new java.lang.String[] { "Text", "SessionId", });
    internal_static_intent_Intent_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_intent_Intent_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_intent_Intent_descriptor,
        new java.lang.String[] { "Name", "Confidence", });
    internal_static_intent_Entity_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_intent_Entity_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_intent_Entity_descriptor,
        new java.lang.String[] { "Start", "End", "Value", "Entity", "Confidence", "Extractor", });
    internal_static_intent_Rank_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_intent_Rank_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_intent_Rank_descriptor,
        new java.lang.String[] { "Name", "Confidence", });
    internal_static_intent_NluResponse_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_intent_NluResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_intent_NluResponse_descriptor,
        new java.lang.String[] { "SessionId", "Intent", "Entities", "Ranking", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
