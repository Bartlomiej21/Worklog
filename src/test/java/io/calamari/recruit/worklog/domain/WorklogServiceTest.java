package io.calamari.recruit.worklog.domain;

import io.calamari.recruit.worklog.api.Worklog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.stream.Stream;

import static io.calamari.recruit.worklog.api.TestWorklogFactory.FIRST_SHIFT_END;
import static io.calamari.recruit.worklog.api.TestWorklogFactory.FIRST_SHIFT_START;
import static io.calamari.recruit.worklog.api.TestWorklogFactory.TEST_WORKLOG;
import static io.calamari.recruit.worklog.domain.TestShiftFactory.FINISHED_SHIFT;
import static io.calamari.recruit.worklog.domain.TestShiftFactory.STARTED_SHIFT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WorklogServiceTest {

    @Mock
    private ShiftsRepository repository;

    @InjectMocks
    private WorklogService worklogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldStartShiftIfNoCurrentShift() {
        // Given
        when(repository.getCurrentShift()).thenReturn(Optional.empty());

        // When
        worklogService.workStarted(FIRST_SHIFT_START);

        // Then
        verify(repository).store(any(Shift.StartedShift.class));
    }

    @Test
    void shouldNotStartShiftIfCurrentShiftExists() {
        // Given
        when(repository.getCurrentShift()).thenReturn(Optional.of(STARTED_SHIFT));

        // When
        worklogService.workStarted(FIRST_SHIFT_START);

        // Then
        verify(repository, never()).store(any(Shift.StartedShift.class));
    }

    @Test
    void shouldFinishWorkIfCurrentShiftExists() {
        // Given
        when(repository.getCurrentShift()).thenReturn(Optional.of(STARTED_SHIFT));

        // When
        worklogService.workFinished(FIRST_SHIFT_END);

        // Then
        verify(repository).store(any(Shift.FinishedShift.class));
    }

    @Test
    void shouldNotFinishWorkIfNoCurrentShift() {
        // Given
        when(repository.getCurrentShift()).thenReturn(Optional.empty());

        // When
        worklogService.workFinished(FIRST_SHIFT_END);

        // Then
        verify(repository, never()).store(any(Shift.FinishedShift.class));
    }

    @Test
    void shouldGetWorklog() {
        // Given
        when(repository.getShifts()).thenReturn(Stream.of(FINISHED_SHIFT, STARTED_SHIFT));

        // When
        Worklog actualWorklog = worklogService.getWorklog();

        // Then
        assertEquals(TEST_WORKLOG, actualWorklog);
    }
}
