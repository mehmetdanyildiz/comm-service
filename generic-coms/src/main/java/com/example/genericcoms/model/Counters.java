package com.example.genericcoms.model;

import lombok.Getter;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class Counters {
    private final AtomicLong httpSuccess = new AtomicLong();
    private final AtomicLong httpFailure = new AtomicLong();
    private final AtomicLong rabbitSuccess = new AtomicLong();
    private final AtomicLong rabbitFailure = new AtomicLong();
    private final AtomicLong wsSuccess = new AtomicLong();
    private final AtomicLong wsFailure = new AtomicLong();

    public record Snapshot(
            long httpSuccess, long httpFailure,
            long rabbitSuccess, long rabbitFailure,
            long wsSuccess, long wsFailure
    ) {}

    public Snapshot snapshot() {
        return new Snapshot(
                httpSuccess.get(), httpFailure.get(),
                rabbitSuccess.get(), rabbitFailure.get(),
                wsSuccess.get(), wsFailure.get()
        );
    }
}
