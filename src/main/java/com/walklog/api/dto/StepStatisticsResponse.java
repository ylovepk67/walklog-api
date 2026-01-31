package com.walklog.api.dto;

public class StepStatisticsResponse {

    private Long totalSteps;
    private Double averageSteps;
    private Integer maxSteps;
    private Integer minSteps;
    private Long totalDays;

    // 기본 생성자
    public StepStatisticsResponse() {
    }

    // 생성자
    public StepStatisticsResponse(Long totalSteps, Double averageSteps, 
                                   Integer maxSteps, Integer minSteps, Long totalDays) {
        this.totalSteps = totalSteps;
        this.averageSteps = averageSteps;
        this.maxSteps = maxSteps;
        this.minSteps = minSteps;
        this.totalDays = totalDays;
    }

    // Getter & Setter
    public Long getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(Long totalSteps) {
        this.totalSteps = totalSteps;
    }

    public Double getAverageSteps() {
        return averageSteps;
    }

    public void setAverageSteps(Double averageSteps) {
        this.averageSteps = averageSteps;
    }

    public Integer getMaxSteps() {
        return maxSteps;
    }

    public void setMaxSteps(Integer maxSteps) {
        this.maxSteps = maxSteps;
    }

    public Integer getMinSteps() {
        return minSteps;
    }

    public void setMinSteps(Integer minSteps) {
        this.minSteps = minSteps;
    }

    public Long getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Long totalDays) {
        this.totalDays = totalDays;
    }
}
