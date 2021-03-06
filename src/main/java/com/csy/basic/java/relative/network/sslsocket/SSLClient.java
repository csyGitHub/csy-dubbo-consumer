package com.csy.basic.java.relative.network.sslsocket;

/**
 *
 * OpenSSL生成根证书CA及签发子证书
 * https://my.oschina.net/itblog/blog/651434
 *
 * SSL通信客户端
 * @author chensy
 * @date 2019-05-24 00:23
 */
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class SSLClient {
    private SSLSocket sslSocket;
    public static void main(String[] args) throws Exception {
        SSLClient client = new SSLClient();
        client.init();
        System.out.println("SSLClient initialized.");
        client.process();
    }

    //客户端将要使用到client.keystore和ca-trust.keystore
    public void init() throws Exception {
        String host = "127.0.0.1";
        int port = 1234;
        String keystorePath = "/Users/chenshengyong/selfPro/dubbo_demo/consumer_demo/src/main/java/com/csy/basic/java/relative/network/sslsocket/client.keystore";
        String trustKeystorePath = "/Users/chenshengyong/selfPro/dubbo_demo/consumer_demo/src/main/java/com/csy/basic/java/relative/network/sslsocket/ca-trust.keystore";
        String keystorePassword = "123456";
        SSLContext context = SSLContext.getInstance("SSL");
        //客户端证书库
        KeyStore clientKeystore = KeyStore.getInstance("pkcs12");
        FileInputStream keystoreFis = new FileInputStream(keystorePath);
        clientKeystore.load(keystoreFis, keystorePassword.toCharArray());
        //信任证书库
        KeyStore trustKeystore = KeyStore.getInstance("jks");
        FileInputStream trustKeystoreFis = new FileInputStream(trustKeystorePath);
        trustKeystore.load(trustKeystoreFis, keystorePassword.toCharArray());

        //密钥库
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("sunx509");
        kmf.init(clientKeystore, keystorePassword.toCharArray());

        //信任库
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");
        tmf.init(trustKeystore);

        //初始化SSL上下文
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        sslSocket = (SSLSocket)context.getSocketFactory().createSocket(host, port);
    }

    public void process() throws Exception {
        //往SSLSocket中写入数据
        String hello = "hello boy";
        OutputStream out = sslSocket.getOutputStream();
        out.write(hello.getBytes(), 0, hello.getBytes().length);
        out.flush();

        //从SSLSocket中读取数据
        InputStream in = sslSocket.getInputStream();
        byte[] buffer = new byte[50];
        in.read(buffer);
        System.out.println(new String(buffer));
    }
}
