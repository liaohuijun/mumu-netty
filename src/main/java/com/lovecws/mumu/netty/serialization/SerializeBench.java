package com.lovecws.mumu.netty.serialization;

import com.lovecws.mumu.netty.serialization.marshalling.JbossMarshallingUtil;
import com.lovecws.mumu.netty.serialization.protobuf.user.User;
import com.lovecws.mumu.netty.serialization.serialize.SerializeUtil;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 序列化工具测试
 * @date 2017-11-20 16:57
 */
public class SerializeBench {

    private static UserInfo userInfo = new UserInfo(1, "lovecws", "5211314");

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void javaSerialize() {
        SerializeUtil.toBytes(userInfo);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void marshallingSerialize() {
        JbossMarshallingUtil.toBytes(userInfo);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void protobufSerialize() {
        User.UserInfoProto.Builder builder = User.UserInfoProto.newBuilder();
        builder.setUserId(1);
        builder.setUserName("lovecws");
        builder.setPassword("5211314");
        User.UserInfoProto userInfoProto = builder.build();
        userInfoProto.toByteArray();
    }

    public static void main(String[] args) {
        Options options = new OptionsBuilder()
                .warmupIterations(10)
                .measurementIterations(10)
                .shouldFailOnError(true)
                .threads(1)
                .forks(1)
                .include(SerializeBench.class.getSimpleName() + ".javaSerialize$")
                .include(SerializeBench.class.getSimpleName() + ".marshallingSerialize$")
                .include(SerializeBench.class.getSimpleName() + ".protobufSerialize$")
                .shouldDoGC(false)
                .build();
        Runner runner = new Runner(options);
        try {
            runner.run();
        } catch (RunnerException e) {
            e.printStackTrace();
        }
    }
}
