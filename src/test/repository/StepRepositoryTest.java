package com.walklog.api.repository;

import com.walklog.api.entity.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StepRepositoryTest {

    @Autowired
    private StepRepository stepRepository;

    @Test
    @DisplayName("걸음 수 저장 테스트")
    void saveStep() {
        // given
        LocalDate date = LocalDate.of(2026, 1, 31);
        Step step = new Step(date, 10000);

        // when
        Step savedStep = stepRepository.save(step);

        // then
        assertThat(savedStep.getId()).isNotNull();
        assertThat(savedStep.getDate()).isEqualTo(date);
        assertThat(savedStep.getSteps()).isEqualTo(10000);
    }

    @Test
    @DisplayName("날짜로 조회 테스트")
    void findByDate() {
        // given
        LocalDate date = LocalDate.of(2026, 1, 31);
        Step step = new Step(date, 10000);
        stepRepository.save(step);

        // when
        Optional<Step> foundStep = stepRepository.findByDate(date);

        // then
        assertThat(foundStep).isPresent();
        assertThat(foundStep.get().getDate()).isEqualTo(date);
        assertThat(foundStep.get().getSteps()).isEqualTo(10000);
    }

    @Test
    @DisplayName("날짜 존재 여부 확인 테스트")
    void existsByDate() {
        // given
        LocalDate date = LocalDate.of(2026, 1, 31);
        Step step = new Step(date, 10000);
        stepRepository.save(step);

        // when
        boolean exists = stepRepository.existsByDate(date);
        boolean notExists = stepRepository.existsByDate(LocalDate.of(2026, 1, 30));

        // then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("날짜 범위 조회 테스트")
    void findByDateBetween() {
        // given
        stepRepository.save(new Step(LocalDate.of(2026, 1, 28), 7500));
        stepRepository.save(new Step(LocalDate.of(2026, 1, 29), 12000));
        stepRepository.save(new Step(LocalDate.of(2026, 1, 30), 8500));
        stepRepository.save(new Step(LocalDate.of(2026, 1, 31), 10000));

        // when
        List<Step> steps = stepRepository.findByDateBetweenOrderByDateAsc(
            LocalDate.of(2026, 1, 29),
            LocalDate.of(2026, 1, 30)
        );

        // then
        assertThat(steps).hasSize(2);
        assertThat(steps.get(0).getDate()).isEqualTo(LocalDate.of(2026, 1, 29));
        assertThat(steps.get(1).getDate()).isEqualTo(LocalDate.of(2026, 1, 30));
    }

    @Test
    @DisplayName("총 걸음 수 합계 테스트")
    void sumAllSteps() {
        // given
        stepRepository.save(new Step(LocalDate.of(2026, 1, 28), 7500));
        stepRepository.save(new Step(LocalDate.of(2026, 1, 29), 12000));
        stepRepository.save(new Step(LocalDate.of(2026, 1, 30), 8500));

        // when
        Long totalSteps = stepRepository.sumAllSteps();

        // then
        assertThat(totalSteps).isEqualTo(28000L);
    }

    @Test
    @DisplayName("평균 걸음 수 테스트")
    void avgAllSteps() {
        // given
        stepRepository.save(new Step(LocalDate.of(2026, 1, 28), 6000));
        stepRepository.save(new Step(LocalDate.of(2026, 1, 29), 9000));
        stepRepository.save(new Step(LocalDate.of(2026, 1, 30), 12000));

        // when
        Double avgSteps = stepRepository.avgAllSteps();

        // then
        assertThat(avgSteps).isEqualTo(9000.0);
    }

    @Test
    @DisplayName("최대 걸음 수 테스트")
    void maxSteps() {
        // given
        stepRepository.save(new Step(LocalDate.of(2026, 1, 28), 7500));
        stepRepository.save(new Step(LocalDate.of(2026, 1, 29), 15000));
        stepRepository.save(new Step(LocalDate.of(2026, 1, 30), 8500));

        // when
        Integer maxSteps = stepRepository.maxSteps();

        // then
        assertThat(maxSteps).isEqualTo(15000);
    }

    @Test
    @DisplayName("최소 걸음 수 테스트")
    void minSteps() {
        // given
        stepRepository.save(new Step(LocalDate.of(2026, 1, 28), 7500));
        stepRepository.save(new Step(LocalDate.of(2026, 1, 29), 5000));
        stepRepository.save(new Step(LocalDate.of(2026, 1, 30), 8500));

        // when
        Integer minSteps = stepRepository.minSteps();

        // then
        assertThat(minSteps).isEqualTo(5000);
    }
}
