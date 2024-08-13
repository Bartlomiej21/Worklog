package io.calamari.recruit.worklog.api;

import io.calamari.recruit.worklog.domain.WorklogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static io.calamari.recruit.worklog.api.TestWorklogFactory.FIRST_ENTRY_END;
import static io.calamari.recruit.worklog.api.TestWorklogFactory.FIRST_ENTRY_START;
import static io.calamari.recruit.worklog.api.TestWorklogFactory.SECOND_ENTRY_START;
import static io.calamari.recruit.worklog.api.TestWorklogFactory.TEST_WORKLOG;

@SpringBootTest
@AutoConfigureMockMvc
class WorklogEndpointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorklogService worklogService;

    @MockBean
    private Clock clock;

    @Test
    void shouldPostStartedShifts() throws Exception {
        // Given
        Instant now = Instant.now();
        when(clock.instant()).thenReturn(now);

        // When
        mockMvc.perform(post("/worklog/start"))
                .andExpect(status().isOk());

        // Then
        verify(worklogService).workStarted(now);
    }

    @Test
    void shouldPostFinishedShifts() throws Exception {
        // Given
        Instant now = Instant.now();
        when(clock.instant()).thenReturn(now);

        // When
        mockMvc.perform(post("/worklog/finish"))
                .andExpect(status().isOk());

        // Then
        verify(worklogService).workFinished(now);
    }

    @Test
    void shouldGetWorklog() throws Exception {
        // Given
        when(worklogService.getWorklog()).thenReturn(TEST_WORKLOG);

        // When & Then
        mockMvc.perform(get("/worklog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shifts[0].start").value(FIRST_ENTRY_START))
                .andExpect(jsonPath("$.shifts[0].end").value(FIRST_ENTRY_END))
                .andExpect(jsonPath("$.shifts[1].start").value(SECOND_ENTRY_START))
                .andExpect(jsonPath("$.shifts[1].end").isEmpty());
    }
}
