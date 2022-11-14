package org.example;


import org.apache.log4j.Logger;
import org.example.layout.VelocityLayout;

import java.util.Scanner;

public class MyAppenderTest {
    private static final Logger logger = Logger.getLogger(MyAppenderTest.class.getName());
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100_000_000; i++) {
                    logger.info("Test! ");
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100_000_000; i++) {
                    logger.info("Test! ");
                }
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

    }
}
