package com.walklog.api.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "steps")
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate date;

    @Column(nullable = false)
    private Integer steps;

    // 기본 생성자 (JPA 필수)
    protected Step() {
    }

    // 생성자
    public Step(LocalDate date, Integer steps) {
        this.date = date;
        this.steps = steps;
    }

    // Getter
    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getSteps() {
        return steps;
    }

    // Setter (필요시)
    public void setSteps(Integer steps) {
        this.steps = steps;
    }
}
