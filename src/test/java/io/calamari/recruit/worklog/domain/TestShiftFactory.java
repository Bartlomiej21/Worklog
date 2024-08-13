package io.calamari.recruit.worklog.domain;

import static io.calamari.recruit.worklog.api.TestWorklogFactory.FIRST_SHIFT_END;
import static io.calamari.recruit.worklog.api.TestWorklogFactory.FIRST_SHIFT_START;
import static io.calamari.recruit.worklog.api.TestWorklogFactory.SECOND_SHIFT_START;

public class TestShiftFactory {
    static final Shift.FinishedShift FINISHED_SHIFT = new Shift.FinishedShift(FIRST_SHIFT_START, FIRST_SHIFT_END);
    static final Shift.StartedShift STARTED_SHIFT = new Shift.StartedShift(SECOND_SHIFT_START);
}
