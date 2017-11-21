// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: user.proto

package com.lovecws.mumu.netty.serialization.protobuf.user;

public final class User {
  private User() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface UserInfoProtoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.lovecws.mumu.netty.serialization.protobuf.user.UserInfoProto)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 userId = 1;</code>
     */
    int getUserId();

    /**
     * <code>string userName = 2;</code>
     */
    java.lang.String getUserName();
    /**
     * <code>string userName = 2;</code>
     */
    com.google.protobuf.ByteString
        getUserNameBytes();

    /**
     * <code>string password = 3;</code>
     */
    java.lang.String getPassword();
    /**
     * <code>string password = 3;</code>
     */
    com.google.protobuf.ByteString
        getPasswordBytes();
  }
  /**
   * <pre>
   *用户信息
   * </pre>
   *
   * Protobuf type {@code com.lovecws.mumu.netty.serialization.protobuf.user.UserInfoProto}
   */
  public  static final class UserInfoProto extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.lovecws.mumu.netty.serialization.protobuf.user.UserInfoProto)
      UserInfoProtoOrBuilder {
    // Use UserInfoProto.newBuilder() to construct.
    private UserInfoProto(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private UserInfoProto() {
      userId_ = 0;
      userName_ = "";
      password_ = "";
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private UserInfoProto(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 8: {

              userId_ = input.readInt32();
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              userName_ = s;
              break;
            }
            case 26: {
              java.lang.String s = input.readStringRequireUtf8();

              password_ = s;
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
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.lovecws.mumu.netty.serialization.protobuf.user.User.internal_static_com_lovecws_mumu_netty_serialization_protobuf_user_UserInfoProto_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.lovecws.mumu.netty.serialization.protobuf.user.User.internal_static_com_lovecws_mumu_netty_serialization_protobuf_user_UserInfoProto_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto.class, com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto.Builder.class);
    }

    public static final int USERID_FIELD_NUMBER = 1;
    private int userId_;
    /**
     * <code>int32 userId = 1;</code>
     */
    public int getUserId() {
      return userId_;
    }

    public static final int USERNAME_FIELD_NUMBER = 2;
    private volatile java.lang.Object userName_;
    /**
     * <code>string userName = 2;</code>
     */
    public java.lang.String getUserName() {
      java.lang.Object ref = userName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        userName_ = s;
        return s;
      }
    }
    /**
     * <code>string userName = 2;</code>
     */
    public com.google.protobuf.ByteString
        getUserNameBytes() {
      java.lang.Object ref = userName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        userName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PASSWORD_FIELD_NUMBER = 3;
    private volatile java.lang.Object password_;
    /**
     * <code>string password = 3;</code>
     */
    public java.lang.String getPassword() {
      java.lang.Object ref = password_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        password_ = s;
        return s;
      }
    }
    /**
     * <code>string password = 3;</code>
     */
    public com.google.protobuf.ByteString
        getPasswordBytes() {
      java.lang.Object ref = password_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        password_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (userId_ != 0) {
        output.writeInt32(1, userId_);
      }
      if (!getUserNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, userName_);
      }
      if (!getPasswordBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, password_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (userId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, userId_);
      }
      if (!getUserNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, userName_);
      }
      if (!getPasswordBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, password_);
      }
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto)) {
        return super.equals(obj);
      }
      com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto other = (com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto) obj;

      boolean result = true;
      result = result && (getUserId()
          == other.getUserId());
      result = result && getUserName()
          .equals(other.getUserName());
      result = result && getPassword()
          .equals(other.getPassword());
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + USERID_FIELD_NUMBER;
      hash = (53 * hash) + getUserId();
      hash = (37 * hash) + USERNAME_FIELD_NUMBER;
      hash = (53 * hash) + getUserName().hashCode();
      hash = (37 * hash) + PASSWORD_FIELD_NUMBER;
      hash = (53 * hash) + getPassword().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
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
     * <pre>
     *用户信息
     * </pre>
     *
     * Protobuf type {@code com.lovecws.mumu.netty.serialization.protobuf.user.UserInfoProto}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.lovecws.mumu.netty.serialization.protobuf.user.UserInfoProto)
        com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProtoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.lovecws.mumu.netty.serialization.protobuf.user.User.internal_static_com_lovecws_mumu_netty_serialization_protobuf_user_UserInfoProto_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.lovecws.mumu.netty.serialization.protobuf.user.User.internal_static_com_lovecws_mumu_netty_serialization_protobuf_user_UserInfoProto_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto.class, com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto.Builder.class);
      }

      // Construct using com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto.newBuilder()
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
      public Builder clear() {
        super.clear();
        userId_ = 0;

        userName_ = "";

        password_ = "";

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.lovecws.mumu.netty.serialization.protobuf.user.User.internal_static_com_lovecws_mumu_netty_serialization_protobuf_user_UserInfoProto_descriptor;
      }

      public com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto getDefaultInstanceForType() {
        return com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto.getDefaultInstance();
      }

      public com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto build() {
        com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto buildPartial() {
        com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto result = new com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto(this);
        result.userId_ = userId_;
        result.userName_ = userName_;
        result.password_ = password_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto) {
          return mergeFrom((com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto other) {
        if (other == com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto.getDefaultInstance()) return this;
        if (other.getUserId() != 0) {
          setUserId(other.getUserId());
        }
        if (!other.getUserName().isEmpty()) {
          userName_ = other.userName_;
          onChanged();
        }
        if (!other.getPassword().isEmpty()) {
          password_ = other.password_;
          onChanged();
        }
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int userId_ ;
      /**
       * <code>int32 userId = 1;</code>
       */
      public int getUserId() {
        return userId_;
      }
      /**
       * <code>int32 userId = 1;</code>
       */
      public Builder setUserId(int value) {
        
        userId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 userId = 1;</code>
       */
      public Builder clearUserId() {
        
        userId_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object userName_ = "";
      /**
       * <code>string userName = 2;</code>
       */
      public java.lang.String getUserName() {
        java.lang.Object ref = userName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          userName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string userName = 2;</code>
       */
      public com.google.protobuf.ByteString
          getUserNameBytes() {
        java.lang.Object ref = userName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          userName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string userName = 2;</code>
       */
      public Builder setUserName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        userName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string userName = 2;</code>
       */
      public Builder clearUserName() {
        
        userName_ = getDefaultInstance().getUserName();
        onChanged();
        return this;
      }
      /**
       * <code>string userName = 2;</code>
       */
      public Builder setUserNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        userName_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object password_ = "";
      /**
       * <code>string password = 3;</code>
       */
      public java.lang.String getPassword() {
        java.lang.Object ref = password_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          password_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string password = 3;</code>
       */
      public com.google.protobuf.ByteString
          getPasswordBytes() {
        java.lang.Object ref = password_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          password_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string password = 3;</code>
       */
      public Builder setPassword(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        password_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string password = 3;</code>
       */
      public Builder clearPassword() {
        
        password_ = getDefaultInstance().getPassword();
        onChanged();
        return this;
      }
      /**
       * <code>string password = 3;</code>
       */
      public Builder setPasswordBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        password_ = value;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:com.lovecws.mumu.netty.serialization.protobuf.user.UserInfoProto)
    }

    // @@protoc_insertion_point(class_scope:com.lovecws.mumu.netty.serialization.protobuf.user.UserInfoProto)
    private static final com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto();
    }

    public static com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<UserInfoProto>
        PARSER = new com.google.protobuf.AbstractParser<UserInfoProto>() {
      public UserInfoProto parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new UserInfoProto(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<UserInfoProto> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<UserInfoProto> getParserForType() {
      return PARSER;
    }

    public com.lovecws.mumu.netty.serialization.protobuf.user.User.UserInfoProto getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_lovecws_mumu_netty_serialization_protobuf_user_UserInfoProto_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_lovecws_mumu_netty_serialization_protobuf_user_UserInfoProto_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\nuser.proto\0222com.lovecws.mumu.netty.ser" +
      "ialization.protobuf.user\"C\n\rUserInfoProt" +
      "o\022\016\n\006userId\030\001 \001(\005\022\020\n\010userName\030\002 \001(\t\022\020\n\010p" +
      "assword\030\003 \001(\tB6\n2com.lovecws.mumu.netty." +
      "serialization.protobuf.userP\000b\006proto3"
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
    internal_static_com_lovecws_mumu_netty_serialization_protobuf_user_UserInfoProto_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_lovecws_mumu_netty_serialization_protobuf_user_UserInfoProto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_lovecws_mumu_netty_serialization_protobuf_user_UserInfoProto_descriptor,
        new java.lang.String[] { "UserId", "UserName", "Password", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
