package com.walklog.api.controller;

import com.walklog.api.dto.StepRequest;
import com.walklog.api.dto.StepResponse;
import com.walklog.api.dto.StepStatisticsResponse;
import com.walklog.api.dto.StepUpdateRequest;
import com.walklog.api.service.StepService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/steps")
public class StepController {

    private final StepService stepService;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    // 걸음 수 기록
    @PostMapping
    public ResponseEntity<StepResponse> createStep(@Valid @RequestBody StepRequest request) {
        StepResponse response = stepService.createStep(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 특정 날짜 조회
    @GetMapping("/{date}")
    public ResponseEntity<StepResponse> getStepByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        StepResponse response = stepService.getStepByDate(localDate);
        return ResponseEntity.ok(response);
    }

    // 전체 기록 조회
    @GetMapping
    public ResponseEntity<List<StepResponse>> getAllSteps() {
        List<StepResponse> responses = stepService.getAllSteps();
        return ResponseEntity.ok(responses);
    }

    // ========================================
    // 추가 기능
    // ========================================

    // 걸음 수 수정
    @PutMapping("/{id}")
    public ResponseEntity<StepResponse> updateStep(
            @PathVariable Long id,
            @Valid @RequestBody StepUpdateRequest request) {
        StepResponse response = stepService.updateStep(id, request);
        return ResponseEntity.ok(response);
    }

    // 걸음 수 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStep(@PathVariable Long id) {
        stepService.deleteStep(id);
        return ResponseEntity.noContent().build();
    }

    // 날짜 범위 조회
    @GetMapping("/range")
    public ResponseEntity<List<StepResponse>> getStepsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        List<StepResponse> responses = stepService.getStepsByDateRange(start, end);
        return ResponseEntity.ok(responses);
    }

    // 통계 조회
    @GetMapping("/statistics")
    public ResponseEntity<StepStatisticsResponse> getStatistics() {
        StepStatisticsResponse response = stepService.getStatistics();
        return ResponseEntity.ok(response);
    }
}
