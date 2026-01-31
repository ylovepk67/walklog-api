package com.walklog.api.service;

import com.walklog.api.dto.StepRequest;
import com.walklog.api.dto.StepResponse;
import com.walklog.api.dto.StepStatisticsResponse;
import com.walklog.api.dto.StepUpdateRequest;
import com.walklog.api.entity.Step;
import com.walklog.api.repository.StepRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StepServiceTest {

    @Mock
    private StepRepository stepRepository;

    @InjectMocks
    private StepService stepService;

    @Test
    @DisplayName("걸음 수 기록 성공 테스트")
    void createStep_Success() {
        // given
        LocalDate date = LocalDate.of(2026, 1, 31);
        StepRequest request = new StepRequest(date, 10000);
        Step step = new Step(date, 10000);
        
        when(stepRepository.existsByDate(date)).thenReturn(false);
        when(stepRepository.save(any(Step.class))).thenReturn(step);

        // when
        StepResponse response = stepService.createStep(request);

        // then
        assertThat(response.getDate()).isEqualTo(date);
        assertThat(response.getSteps()).isEqualTo(10000);
        verify(stepRepository, times(1)).existsByDate(date);
        verify(stepRepository, times(1)).save(any(Step.class));
    }

    @Test
    @DisplayName("걸음 수 기록 실패 - 중복 날짜")
    void createStep_Fail_DuplicateDate() {
        // given
        LocalDate date = LocalDate.of(2026, 1, 31);
        StepRequest request = new StepRequest(date, 10000);
        
        when(stepRepository.existsByDate(date)).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> stepService.createStep(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당 날짜의 기록이 이미 존재합니다.");
        
        verify(stepRepository, times(1)).existsByDate(date);
        verify(stepRepository, never()).save(any(Step.class));
    }

    @Test
    @DisplayName("특정 날짜 조회 성공 테스트")
    void getStepByDate_Success() {
        // given
        LocalDate date = LocalDate.of(2026, 1, 31);
        Step step = new Step(date, 10000);
        
        when(stepRepository.findByDate(date)).thenReturn(Optional.of(step));

        // when
        StepResponse response = stepService.getStepByDate(date);

        // then
        assertThat(response.getDate()).isEqualTo(date);
        assertThat(response.getSteps()).isEqualTo(10000);
        verify(stepRepository, times(1)).findByDate(date);
    }

    @Test
    @DisplayName("특정 날짜 조회 실패 - 데이터 없음")
    void getStepByDate_Fail_NotFound() {
        // given
        LocalDate date = LocalDate.of(2026, 1, 31);
        
        when(stepRepository.findByDate(date)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> stepService.getStepByDate(date))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당 날짜의 기록을 찾을 수 없습니다.");
        
        verify(stepRepository, times(1)).findByDate(date);
    }

    @Test
    @DisplayName("전체 기록 조회 테스트")
    void getAllSteps() {
        // given
        List<Step> steps = Arrays.asList(
            new Step(LocalDate.of(2026, 1, 28), 7500),
            new Step(LocalDate.of(2026, 1, 29), 12000)
        );
        
        when(stepRepository.findAll()).thenReturn(steps);

        // when
        List<StepResponse> responses = stepService.getAllSteps();

        // then
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getSteps()).isEqualTo(7500);
        assertThat(responses.get(1).getSteps()).isEqualTo(12000);
        verify(stepRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("걸음 수 수정 성공 테스트")
    void updateStep_Success() {
        // given
        Long id = 1L;
        StepUpdateRequest request = new StepUpdateRequest(15000);
        Step step = new Step(LocalDate.of(2026, 1, 31), 10000);
        
        when(stepRepository.findById(id)).thenReturn(Optional.of(step));

        // when
        StepResponse response = stepService.updateStep(id, request);

        // then
        assertThat(response.getSteps()).isEqualTo(15000);
        verify(stepRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("걸음 수 수정 실패 - ID 없음")
    void updateStep_Fail_NotFound() {
        // given
        Long id = 999L;
        StepUpdateRequest request = new StepUpdateRequest(15000);
        
        when(stepRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> stepService.updateStep(id, request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당 ID의 기록을 찾을 수 없습니다.");
        
        verify(stepRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("걸음 수 삭제 성공 테스트")
    void deleteStep_Success() {
        // given
        Long id = 1L;
        
        when(stepRepository.existsById(id)).thenReturn(true);
        doNothing().when(stepRepository).deleteById(id);

        // when
        stepService.deleteStep(id);

        // then
        verify(stepRepository, times(1)).existsById(id);
        verify(stepRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("걸음 수 삭제 실패 - ID 없음")
    void deleteStep_Fail_NotFound() {
        // given
        Long id = 999L;
        
        when(stepRepository.existsById(id)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> stepService.deleteStep(id))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당 ID의 기록을 찾을 수 없습니다.");
        
        verify(stepRepository, times(1)).existsById(id);
        verify(stepRepository, never()).deleteById(id);
    }

    @Test
    @DisplayName("날짜 범위 조회 성공 테스트")
    void getStepsByDateRange_Success() {
        // given
        LocalDate start = LocalDate.of(2026, 1, 28);
        LocalDate end = LocalDate.of(2026, 1, 30);
        List<Step> steps = Arrays.asList(
            new Step(start, 7500),
            new Step(LocalDate.of(2026, 1, 29), 12000),
            new Step(end, 8500)
        );
        
        when(stepRepository.findByDateBetweenOrderByDateAsc(start, end)).thenReturn(steps);

        // when
        List<StepResponse> responses = stepService.getStepsByDateRange(start, end);

        // then
        assertThat(responses).hasSize(3);
        assertThat(responses.get(0).getDate()).isEqualTo(start);
        assertThat(responses.get(2).getDate()).isEqualTo(end);
        verify(stepRepository, times(1)).findByDateBetweenOrderByDateAsc(start, end);
    }

    @Test
    @DisplayName("날짜 범위 조회 실패 - 잘못된 날짜 범위")
    void getStepsByDateRange_Fail_InvalidRange() {
        // given
        LocalDate start = LocalDate.of(2026, 1, 31);
        LocalDate end = LocalDate.of(2026, 1, 28);

        // when & then
        assertThatThrownBy(() -> stepService.getStepsByDateRange(start, end))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("시작 날짜는 종료 날짜보다 이전이어야 합니다.");
        
        verify(stepRepository, never()).findByDateBetweenOrderByDateAsc(any(), any());
    }

    @Test
    @DisplayName("통계 조회 테스트")
    void getStatistics() {
        // given
        when(stepRepository.sumAllSteps()).thenReturn(52000L);
        when(stepRepository.avgAllSteps()).thenReturn(10400.0);
        when(stepRepository.maxSteps()).thenReturn(15000);
        when(stepRepository.minSteps()).thenReturn(7500);
        when(stepRepository.count()).thenReturn(5L);

        // when
        StepStatisticsResponse response = stepService.getStatistics();

        // then
        assertThat(response.getTotalSteps()).isEqualTo(52000L);
        assertThat(response.getAverageSteps()).isEqualTo(10400.0);
        assertThat(response.getMaxSteps()).isEqualTo(15000);
        assertThat(response.getMinSteps()).isEqualTo(7500);
        assertThat(response.getTotalDays()).isEqualTo(5L);
        
        verify(stepRepository, times(1)).sumAllSteps();
        verify(stepRepository, times(1)).avgAllSteps();
        verify(stepRepository, times(1)).maxSteps();
        verify(stepRepository, times(1)).minSteps();
        verify(stepRepository, times(1)).count();
    }

    @Test
    @DisplayName("통계 조회 테스트 - 데이터 없음")
    void getStatistics_NoData() {
        // given
        when(stepRepository.sumAllSteps()).thenReturn(null);
        when(stepRepository.avgAllSteps()).thenReturn(null);
        when(stepRepository.maxSteps()).thenReturn(null);
        when(stepRepository.minSteps()).thenReturn(null);
        when(stepRepository.count()).thenReturn(0L);

        // when
        StepStatisticsResponse response = stepService.getStatistics();

        // then
        assertThat(response.getTotalSteps()).isEqualTo(0L);
        assertThat(response.getAverageSteps()).isEqualTo(0.0);
        assertThat(response.getMaxSteps()).isEqualTo(0);
        assertThat(response.getMinSteps()).isEqualTo(0);
        assertThat(response.getTotalDays()).isEqualTo(0L);
    }
}
