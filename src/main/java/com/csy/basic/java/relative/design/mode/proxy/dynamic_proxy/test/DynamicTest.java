package com.csy.basic.java.relative.design.mode.proxy.dynamic_proxy.test;


import org.springframework.cglib.core.DebuggingClassWriter;

/**
 * 场景：通过代理售票处购买车票
 * @author chensy
 * @date 2019-05-18 14:02
 */
public class DynamicTest {
    public static void main(String[] args) throws Exception {
        RailywayStation railywayStation = new RailywayStation(50.00);
        Object proxy = new DynamicProxyFactory(railywayStation).getProxyInstance();
        //System.out.println(proxy.toString());
        TrainTicket trainTicket = (TrainTicket) proxy;
        System.out.println(trainTicket.buyTicket(102.00));

        System.in.read(); // 按任意键退出
    }

    /**
     * 目标对象必须实现接口
     * 不然动态代理对象不能转换
     * java.lang.ClassCastException: com.sun.proxy.$Proxy0 cannot be cast to ... TrainTicket
     */
}
