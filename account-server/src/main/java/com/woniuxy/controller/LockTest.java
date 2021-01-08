package com.woniuxy.controller;

import org.springframework.web.client.RestTemplate;

import java.util.Random;

public class LockTest {
    public static class Thread1 extends Thread{
        @Override
        public void run() {
            RestTemplate restTemplate = new RestTemplate();
            for (int i = 0; i < 100; i++) {
//                int key = new Random().nextInt(2);
//                if(key == 1)
//                    restTemplate.getForObject("http://localhost:9001/account/add",String.class);
//                else
//                    restTemplate.getForObject("http://localhost:9000/account/add",String.class);
                restTemplate.getForObject("http://localhost:9001/account/addI",String.class);
            }
        }
    }

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread1 thread2 = new Thread1();
        thread1.start();
        thread2.start();
    }

}
