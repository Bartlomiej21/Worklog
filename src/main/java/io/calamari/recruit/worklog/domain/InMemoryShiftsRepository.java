package io.calamari.recruit.worklog.domain;

import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
class InMemoryShiftsRepository implements ShiftsRepository {

    private final List<Shift.FinishedShift> storage = new LinkedList<>();

    private Shift.StartedShift current;

    @Override
    public Optional<Shift.StartedShift> getCurrentShift() {
        return Optional.ofNullable(current);
    }

    @Override
    public Stream<Shift> getShifts() {
        return Stream.concat(storage.stream(), getCurrentShift().stream());
    }

    @Override
    public void store(Shift shift) {
        if (shift instanceof Shift.FinishedShift finished) {
            current = null;
            storage.add(finished);
        } else if (shift instanceof Shift.StartedShift started) {
            current = started;
        }
    }
}
