package io.calamari.recruit.worklog.domain;

import java.util.Optional;
import java.util.stream.Stream;

interface ShiftsRepository {
    Optional<Shift.StartedShift> getCurrentShift();

    Stream<Shift> getShifts();

    void store(Shift shift);
}
