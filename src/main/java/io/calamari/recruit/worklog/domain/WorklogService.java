package io.calamari.recruit.worklog.domain;

import io.calamari.recruit.worklog.api.Worklog;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class WorklogService {

    private final ShiftsRepository repository;

    WorklogService(ShiftsRepository repository) {
        this.repository = repository;
    }

    public void workStarted(Instant startedAt) {
        if (repository.getCurrentShift().isPresent()) {
            return;
        }
        repository.store(Shift.newShift(startedAt));
    }

    public void workFinished(Instant finishedAt) {
        repository.getCurrentShift()
                .map(shift -> shift.finish(finishedAt))
                .ifPresent(repository::store);
    }

    public Worklog getWorklog() {
        List<Worklog.Entry> shifts = repository.getShifts()
                .map(shift -> new Worklog.Entry(shift.getStart(), shift.getEnd().orElse(null)))
                .toList();
        return new Worklog(shifts);
    }
}
