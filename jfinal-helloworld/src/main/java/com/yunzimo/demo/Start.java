package com.yunzimo.demo;

import com.jfinal.server.undertow.UndertowServer;

public class Start {
    public static void main(String[] args) {
        UndertowServer.start(DemoConfig.class,80,true);
    }
}
