package com.walklog.api.dto;

import com.walklog.api.entity.Step;
import java.time.LocalDate;

public class StepResponse {

    private Long id;
    private LocalDate date;
    private Integer steps;

    // 기본 생성자
    public StepResponse() {
    }

    // 생성자
    public StepResponse(Long id, LocalDate date, Integer steps) {
        this.id = id;
        this.date = date;
        this.steps = steps;
    }

    // Entity -> DTO 변환 (정적 팩토리 메서드)
    public static StepResponse from(Step step) {
        return new StepResponse(
            step.getId(),
            step.getDate(),
            step.getSteps()
        );
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }
}
