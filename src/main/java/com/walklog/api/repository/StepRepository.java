package com.walklog.api.repository;

import com.walklog.api.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {
    
    // 날짜로 조회
    Optional<Step> findByDate(LocalDate date);
    
    // 날짜 존재 여부 확인
    boolean existsByDate(LocalDate date);
    
    // 날짜 범위 조회 (추가)
    List<Step> findByDateBetweenOrderByDateAsc(LocalDate startDate, LocalDate endDate);
    
    // 통계용 쿼리 (추가)
    @Query("SELECT SUM(s.steps) FROM Step s")
    Long sumAllSteps();
    
    @Query("SELECT AVG(s.steps) FROM Step s")
    Double avgAllSteps();
    
    @Query("SELECT MAX(s.steps) FROM Step s")
    Integer maxSteps();
    
    @Query("SELECT MIN(s.steps) FROM Step s")
    Integer minSteps();
}
