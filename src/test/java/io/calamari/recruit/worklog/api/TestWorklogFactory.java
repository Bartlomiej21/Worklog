package io.calamari.recruit.worklog.api;

import java.time.Instant;
import java.util.List;

public class TestWorklogFactory {
    public static final String FIRST_ENTRY_START = "2024-08-13T09:00:00Z";
    public static final String FIRST_ENTRY_END = "2024-08-13T17:00:00Z";
    public static final String SECOND_ENTRY_START = "2024-08-14T00:00:00Z";
    public static final Instant FIRST_SHIFT_START = Instant.parse(FIRST_ENTRY_START);
    public static final Instant FIRST_SHIFT_END = Instant.parse(FIRST_ENTRY_END);
    public static final Instant SECOND_SHIFT_START = Instant.parse(SECOND_ENTRY_START);
    private static final Worklog.Entry finishedShiftEntry = new Worklog.Entry(FIRST_SHIFT_START, FIRST_SHIFT_END);
    private static final Worklog.Entry startedShiftEntry = new Worklog.Entry(SECOND_SHIFT_START, null);
    public static final Worklog TEST_WORKLOG = new Worklog(List.of(finishedShiftEntry, startedShiftEntry));
}
