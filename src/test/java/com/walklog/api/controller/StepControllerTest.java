package com.walklog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walklog.api.dto.StepRequest;
import com.walklog.api.dto.StepResponse;
import com.walklog.api.dto.StepStatisticsResponse;
import com.walklog.api.dto.StepUpdateRequest;
import com.walklog.api.service.StepService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StepController.class)
class StepControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StepService stepService;

    @Test
    @DisplayName("걸음 수 기록 API 테스트")
    void createStep() throws Exception {
        // given
        LocalDate date = LocalDate.of(2026, 1, 31);
        StepRequest request = new StepRequest(date, 10000);
        StepResponse response = new StepResponse(1L, date, 10000);
        
        when(stepService.createStep(any(StepRequest.class))).thenReturn(response);

        // when & then
        mockMvc.perform(post("/api/steps")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.date").value("2026-01-31"))
            .andExpect(jsonPath("$.steps").value(10000));
        
        verify(stepService, times(1)).createStep(any(StepRequest.class));
    }

    @Test
    @DisplayName("걸음 수 기록 API 실패 - Validation 오류")
    void createStep_Fail_Validation() throws Exception {
        // given
        StepRequest request = new StepRequest(LocalDate.of(2026, 1, 31), -100);

        // when & then
        mockMvc.perform(post("/api/steps")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
        
        verify(stepService, never()).createStep(any(StepRequest.class));
    }

    @Test
    @DisplayName("특정 날짜 조회 API 테스트")
    void getStepByDate() throws Exception {
        // given
        LocalDate date = LocalDate.of(2026, 1, 31);
        StepResponse response = new StepResponse(1L, date, 10000);
        
        when(stepService.getStepByDate(date)).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/steps/2026-01-31"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.date").value("2026-01-31"))
            .andExpect(jsonPath("$.steps").value(10000));
        
        verify(stepService, times(1)).getStepByDate(date);
    }

    @Test
    @DisplayName("전체 기록 조회 API 테스트")
    void getAllSteps() throws Exception {
        // given
        List<StepResponse> responses = Arrays.asList(
            new StepResponse(1L, LocalDate.of(2026, 1, 31), 10000),
            new StepResponse(2L, LocalDate.of(2026, 1, 30), 8500)
        );
        
        when(stepService.getAllSteps()).thenReturn(responses);

        // when & then
        mockMvc.perform(get("/api/steps"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].steps").value(10000))
            .andExpect(jsonPath("$[1].steps").value(8500));
        
        verify(stepService, times(1)).getAllSteps();
    }

    @Test
    @DisplayName("걸음 수 수정 API 테스트")
    void updateStep() throws Exception {
        // given
        Long id = 1L;
        StepUpdateRequest request = new StepUpdateRequest(15000);
        StepResponse response = new StepResponse(id, LocalDate.of(2026, 1, 31), 15000);
        
        when(stepService.updateStep(eq(id), any(StepUpdateRequest.class))).thenReturn(response);

        // when & then
        mockMvc.perform(put("/api/steps/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.steps").value(15000));
        
        verify(stepService, times(1)).updateStep(eq(id), any(StepUpdateRequest.class));
    }

    @Test
    @DisplayName("걸음 수 삭제 API 테스트")
    void deleteStep() throws Exception {
        // given
        Long id = 1L;
        doNothing().when(stepService).deleteStep(id);

        // when & then
        mockMvc.perform(delete("/api/steps/1"))
            .andExpect(status().isNoContent());
        
        verify(stepService, times(1)).deleteStep(id);
    }

    @Test
    @DisplayName("날짜 범위 조회 API 테스트")
    void getStepsByDateRange() throws Exception {
        // given
        LocalDate start = LocalDate.of(2026, 1, 28);
        LocalDate end = LocalDate.of(2026, 1, 30);
        List<StepResponse> responses = Arrays.asList(
            new StepResponse(1L, LocalDate.of(2026, 1, 28), 7500),
            new StepResponse(2L, LocalDate.of(2026, 1, 29), 12000),
            new StepResponse(3L, LocalDate.of(2026, 1, 30), 8500)
        );
        
        when(stepService.getStepsByDateRange(start, end)).thenReturn(responses);

        // when & then
        mockMvc.perform(get("/api/steps/range")
                .param("start", "2026-01-28")
                .param("end", "2026-01-30"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(3))
            .andExpect(jsonPath("$[0].date").value("2026-01-28"))
            .andExpect(jsonPath("$[2].date").value("2026-01-30"));
        
        verify(stepService, times(1)).getStepsByDateRange(start, end);
    }

    @Test
    @DisplayName("통계 조회 API 테스트")
    void getStatistics() throws Exception {
        // given
        StepStatisticsResponse response = new StepStatisticsResponse(
            52000L, 10400.0, 15000, 7500, 5L
        );
        
        when(stepService.getStatistics()).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/steps/statistics"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalSteps").value(52000))
            .andExpect(jsonPath("$.averageSteps").value(10400.0))
            .andExpect(jsonPath("$.maxSteps").value(15000))
            .andExpect(jsonPath("$.minSteps").value(7500))
            .andExpect(jsonPath("$.totalDays").value(5));
        
        verify(stepService, times(1)).getStatistics();
    }
}
