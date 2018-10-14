package com.huihuang.Client;


public class Client {

    public static void main(String[] args){
        Receiver receiver = new Receiver(new ClientUI());
        receiver.start();
    }
}
