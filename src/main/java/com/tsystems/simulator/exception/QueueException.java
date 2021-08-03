package com.tsystems.simulator.exception;

public class QueueException extends Exception{
    public QueueException(String message) {
        super(message, null, false, false);
    }
}
