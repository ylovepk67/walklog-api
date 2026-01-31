package com.walklog.api.service;

import com.walklog.api.dto.StepRequest;
import com.walklog.api.dto.StepResponse;
import com.walklog.api.dto.StepStatisticsResponse;
import com.walklog.api.dto.StepUpdateRequest;
import com.walklog.api.entity.Step;
import com.walklog.api.repository.StepRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class StepService {

    private final StepRepository stepRepository;

    public StepService(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    // 걸음 수 기록
    @Transactional
    public StepResponse createStep(StepRequest request) {
        // 중복 날짜 체크
        if (stepRepository.existsByDate(request.getDate())) {
            throw new IllegalArgumentException("해당 날짜의 기록이 이미 존재합니다.");
        }

        // Entity 생성 & 저장
        Step step = new Step(request.getDate(), request.getSteps());
        Step savedStep = stepRepository.save(step);

        // DTO 변환 & 반환
        return StepResponse.from(savedStep);
    }

    // 특정 날짜 조회
    public StepResponse getStepByDate(LocalDate date) {
        Step step = stepRepository.findByDate(date)
            .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 기록을 찾을 수 없습니다."));
        
        return StepResponse.from(step);
    }

    // 전체 기록 조회
    public List<StepResponse> getAllSteps() {
        return stepRepository.findAll().stream()
            .map(StepResponse::from)
            .collect(Collectors.toList());
    }

    // ========================================
    // 추가 기능
    // ========================================

    // 걸음 수 수정
    @Transactional
    public StepResponse updateStep(Long id, StepUpdateRequest request) {
        Step step = stepRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 기록을 찾을 수 없습니다."));
        
        step.setSteps(request.getSteps());
        
        return StepResponse.from(step);
    }

    // 걸음 수 삭제
    @Transactional
    public void deleteStep(Long id) {
        if (!stepRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 ID의 기록을 찾을 수 없습니다.");
        }
        
        stepRepository.deleteById(id);
    }

    // 날짜 범위 조회
    public List<StepResponse> getStepsByDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작 날짜는 종료 날짜보다 이전이어야 합니다.");
        }
        
        return stepRepository.findByDateBetweenOrderByDateAsc(startDate, endDate).stream()
            .map(StepResponse::from)
            .collect(Collectors.toList());
    }

    // 통계 조회
    public StepStatisticsResponse getStatistics() {
        Long totalSteps = stepRepository.sumAllSteps();
        Double averageSteps = stepRepository.avgAllSteps();
        Integer maxSteps = stepRepository.maxSteps();
        Integer minSteps = stepRepository.minSteps();
        Long totalDays = stepRepository.count();

        // null 처리
        totalSteps = (totalSteps != null) ? totalSteps : 0L;
        averageSteps = (averageSteps != null) ? averageSteps : 0.0;
        maxSteps = (maxSteps != null) ? maxSteps : 0;
        minSteps = (minSteps != null) ? minSteps : 0;

        return new StepStatisticsResponse(totalSteps, averageSteps, maxSteps, minSteps, totalDays);
    }
}
