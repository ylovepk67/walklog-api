package com.walklog.api.controller;

import com.walklog.api.dto.StepRequest;
import com.walklog.api.dto.StepResponse;
import com.walklog.api.service.StepService;
import jakarta.validation.Valid;
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
}
