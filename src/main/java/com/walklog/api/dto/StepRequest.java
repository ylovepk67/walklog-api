package com.walklog.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class StepRequest {

    @NotNull(message = "날짜는 필수입니다.")
    private LocalDate date;

    @NotNull(message = "걸음 수는 필수입니다.")
    @Min(value = 0, message = "걸음 수는 0 이상이어야 합니다.")
    private Integer steps;

    // 기본 생성자
    public StepRequest() {
    }

    // 생성자
    public StepRequest(LocalDate date, Integer steps) {
        this.date = date;
        this.steps = steps;
    }

    // Getter & Setter
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
