package io.calamari.recruit.worklog.domain;

import java.time.Instant;
import java.util.Optional;

sealed interface Shift {

    Instant getStart();

    Optional<Instant> getEnd();

    static StartedShift newShift(Instant startedAt) {
        return new StartedShift(startedAt);
    }


    record StartedShift(Instant start) implements Shift {

        FinishedShift finish(Instant finishedAt) {
            return new FinishedShift(start, finishedAt);
        }

        @Override
        public Instant getStart() {
            return start;
        }

        @Override
        public Optional<Instant> getEnd() {
            return Optional.empty();
        }
    }


    record FinishedShift(Instant start, Instant end) implements Shift {

        @Override
        public Instant getStart() {
            return start;
        }

        @Override
        public Optional<Instant> getEnd() {
            return Optional.of(end);
        }

    }
}
