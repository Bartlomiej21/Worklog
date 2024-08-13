package io.calamari.recruit.worklog.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static io.calamari.recruit.worklog.domain.TestShiftFactory.FINISHED_SHIFT;
import static io.calamari.recruit.worklog.domain.TestShiftFactory.STARTED_SHIFT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryShiftsRepositoryTest {

    private InMemoryShiftsRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryShiftsRepository();
    }

    @Test
    void shouldGetShiftsWithCorrectType() {
        // Given
        List<Shift> expectedShifts = List.of(FINISHED_SHIFT, STARTED_SHIFT);

        // When
        repository.store(FINISHED_SHIFT);
        repository.store(STARTED_SHIFT);
        List<Shift> shifts = repository.getShifts().toList();

        // Then
        assertEquals(2, shifts.size());
        assertEquals(expectedShifts, shifts);
        assertInstanceOf(Shift.FinishedShift.class, shifts.get(0));
        assertInstanceOf(Shift.StartedShift.class, shifts.get(1));
    }

    @Test
    void shouldStoreAndRetrieveStartedShift() {
        // When
        repository.store(STARTED_SHIFT);
        Optional<Shift.StartedShift> currentShift = repository.getCurrentShift();

        // Then
        assertTrue(currentShift.isPresent());
        assertEquals(STARTED_SHIFT, currentShift.get());
    }

    @Test
    void shouldStoreAndRetrieveFinishedShifts() {
        // Given
        List<Shift> expectedShifts = List.of(FINISHED_SHIFT);

        // When
        repository.store(FINISHED_SHIFT);

        // Then
        assertEquals(expectedShifts, repository.getShifts().toList());
    }
}
