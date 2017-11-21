package com.lovecws.mumu.netty.serialization.marshalling;

import com.lovecws.mumu.netty.serialization.UserInfo;
import org.jboss.marshalling.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: jboss marshalling工具类
 * @date 2017-11-20 16:20
 */
public class JbossMarshallingUtil {

    public static byte[] toBytes(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        OutputStreamByteOutput output = null;
        Marshaller marshaller = null;

        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
        marshallingConfiguration.setVersion(5);
        try {
            marshaller = marshallerFactory.createMarshaller(marshallingConfiguration);
            byteArrayOutputStream = new ByteArrayOutputStream();
            output = new OutputStreamByteOutput(byteArrayOutputStream);

            marshaller.start(output);
            marshaller.writeObject(object);
            marshaller.finish();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                marshaller.close();
                byteArrayOutputStream.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object toObject(byte[] bytes) {
        InputStreamByteInput inputStreamByteInput = null;
        ByteArrayInputStream byteArrayInputStream = null;
        Unmarshaller unmarshaller = null;

        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
        marshallingConfiguration.setVersion(5);
        try {
            unmarshaller = marshallerFactory.createUnmarshaller(marshallingConfiguration);
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            inputStreamByteInput = new InputStreamByteInput(byteArrayInputStream);

            unmarshaller.start(inputStreamByteInput);
            Object object = unmarshaller.readObject();
            unmarshaller.finish();
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                unmarshaller.close();
                byteArrayInputStream.close();
                inputStreamByteInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo(1, "lovecws", "5211314");
        System.out.println("序列化之前:" + userInfo);
        byte[] bytes = toBytes(userInfo);
        System.out.println("序列化字节数组:" + new String(bytes));
        System.out.println("序列化之后:" + toObject(bytes));
    }
}
