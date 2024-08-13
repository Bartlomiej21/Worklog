package io.calamari.recruit.worklog.api;

import io.calamari.recruit.worklog.domain.WorklogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;

@RestController
@RequestMapping("/worklog")
class WorklogEndpoint {

    private final Clock clock;
    private final WorklogService worklogService;

    WorklogEndpoint(Clock clock, WorklogService worklogService) {
        this.clock = clock;
        this.worklogService = worklogService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/start")
    public void startWorking() {
        worklogService.workStarted(clock.instant());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/finish")
    public void finishWorking() {
        worklogService.workFinished(clock.instant());
    }

    @RequestMapping(method = RequestMethod.GET)
    public Worklog getWorklog() {
        return worklogService.getWorklog();
    }
}
