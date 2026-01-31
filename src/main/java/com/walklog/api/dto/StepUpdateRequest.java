package com.walklog.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class StepUpdateRequest {

    @NotNull(message = "걸음 수는 필수입니다.")
    @Min(value = 0, message = "걸음 수는 0 이상이어야 합니다.")
    private Integer steps;

    // 기본 생성자
    public StepUpdateRequest() {
    }

    // 생성자
    public StepUpdateRequest(Integer steps) {
        this.steps = steps;
    }

    // Getter & Setter
    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }
}
