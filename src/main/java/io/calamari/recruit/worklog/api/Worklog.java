package io.calamari.recruit.worklog.api;

import java.time.Instant;
import java.util.List;

public record Worklog(List<Entry> shifts) {


    public record Entry(Instant start, Instant end) {
    }
}
