package com.walklog.api.repository;

import com.walklog.api.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {
    
    // 날짜로 조회
    Optional<Step> findByDate(LocalDate date);
    
    // 날짜 존재 여부 확인
    boolean existsByDate(LocalDate date);
}
