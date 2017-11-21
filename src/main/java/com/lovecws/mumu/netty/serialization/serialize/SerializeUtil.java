package com.lovecws.mumu.netty.serialization.serialize;

import com.lovecws.mumu.netty.serialization.UserInfo;

import java.io.*;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 序列化工具
 * @date 2017-11-20 15:23
 */
public class SerializeUtil {

    /**
     * 将对象序列化为字节数据
     *
     * @param object
     * @return
     */
    public static byte[] toBytes(Object object) {
        if (object == null) throw new IllegalArgumentException();
        ByteArrayOutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(object);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                objectOutputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将序列化字节数组转化为序列化对象
     *
     * @param bs
     * @return
     */
    public static Object toObject(byte[] bs) {
        if (bs == null) throw new IllegalArgumentException();
        ObjectInputStream objectInputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bs);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
