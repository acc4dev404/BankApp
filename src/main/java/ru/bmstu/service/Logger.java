package ru.bmstu.service;

import java.time.LocalDateTime;

public class Logger {
    public static void log(String event, String msg) {
        System.out.printf("[%s] %s: %s%n",
                LocalDateTime.now(), event, msg);
    }
}
