package com.lovecws.mumu.netty.serialization.marshalling;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: jboss marshalling工具类
 * @date 2017-11-20 16:20
 */
public class JbossMarshallingFactory {

    public static MarshallingDecoder decoder() {
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
        marshallingConfiguration.setVersion(5);
        MarshallingDecoder marshallingDecoder = new MarshallingDecoder(new DefaultUnmarshallerProvider(marshallerFactory, marshallingConfiguration));
        return marshallingDecoder;
    }

    public static MarshallingEncoder encoder() {
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
        marshallingConfiguration.setVersion(5);
        MarshallingEncoder marshallingEncoder = new MarshallingEncoder(new DefaultMarshallerProvider(marshallerFactory, marshallingConfiguration));
        return marshallingEncoder;
    }
}
