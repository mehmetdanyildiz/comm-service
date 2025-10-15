package com.example.genericcoms.service;

import com.example.genericcoms.model.Counters;
import org.springframework.stereotype.Service;

@Service
public class StatsService {
    private final Counters counters = new Counters();

    public Counters.Snapshot getSnapshot() {
        return counters.snapshot();
    }

    public void incHttpSuccess() { counters.getHttpSuccess().incrementAndGet(); }
    public void incHttpFailure() { counters.getHttpFailure().incrementAndGet(); }
    public void incRabbitSuccess() { counters.getRabbitSuccess().incrementAndGet(); }
    public void incRabbitFailure() { counters.getRabbitFailure().incrementAndGet(); }
    public void incWsSuccess() { counters.getWsSuccess().incrementAndGet(); }
    public void incWsFailure() { counters.getWsFailure().incrementAndGet(); }
}
