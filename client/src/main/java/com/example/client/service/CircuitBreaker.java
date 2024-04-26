package com.example.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
class CircuitBreaker {


    private enum State {
        CLOSED, OPEN, HALF_OPEN
    }

    private State state = State.CLOSED;
    private int consecutiveErrors = 0;
    private int consecutiveOk = 0;
    private static final int MAX_CONSECUTIVE_ERRORS_OPEN = 3;
    private static final int MAX_CONSECUTIVE_ERRORS_HALF_OPEN = 1;
    private static final int CONSECUTIVE_OK = 3;
    private ScheduledExecutorService scheduler;


    private final RestTemplate restTemplate;


    public boolean checkState() {
        return switch (state) {
            case CLOSED, HALF_OPEN -> true;
            case OPEN -> false;
            default -> throw new IllegalStateException("Unknown state");
        };
    }

    public void checkStatus(ResponseEntity<String> status) {
        this.printingState();
        if (status.getStatusCode().is5xxServerError()) {
            consecutiveErrors++;
            if (state == State.CLOSED) {
                this.closedStateEroor();
            }
            if (state == State.HALF_OPEN) {
                this.halfOpenStateError();
            }

        } else {
            consecutiveErrors = 0;
            consecutiveOk++;
            if (state == State.HALF_OPEN && consecutiveOk >= CONSECUTIVE_OK) {
                this.state = State.CLOSED;
            }
        }

    }

    private void closedStateEroor() {
        if (consecutiveErrors >= MAX_CONSECUTIVE_ERRORS_OPEN) {
            this.state = State.OPEN;
            this.timeout();
        }
    }

    private void halfOpenStateError() {
        if (consecutiveErrors >= MAX_CONSECUTIVE_ERRORS_HALF_OPEN) {
            this.state = State.OPEN;
            this.timeout();
        }

    }

    private void timeout() {
        consecutiveErrors = 0;
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(this::setHalfOpen, 10, TimeUnit.SECONDS);
    }

    private void setHalfOpen() {
        state = State.HALF_OPEN;
    }

    public void printingState() {
        System.out.println("State: " + state);
    }
}

