package com.walklog.api.service;

import com.walklog.api.dto.StepRequest;
import com.walklog.api.dto.StepResponse;
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
}
