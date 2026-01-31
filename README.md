# PRD: Walklog API

## 1. 목표(Goal)
- 사용자(userId)가 날짜(date)별로 걸음 수(steps)를 저장하고 조회한다.

## 2. 사용자 시나리오(User Story)
- 사용자는 오늘 걸음 수를 저장하고, 기간/날짜별 기록을 확인하고 싶다.

## 3. 범위(Scope)
### In
- 기록 저장(같은 userId+date면 업데이트)
- 사용자별 기록 조회
### Out
- 로그인/권한, 관리자 기능, 통계 대시보드

## 4. 기능 요구사항(FR)
- FR1: userId, date, steps를 저장한다.
- FR2: userId로 기록 목록을 조회한다.
- FR3: steps는 0 이상의 정수만 허용한다.

## 5. 수용 기준(Acceptance Criteria)
- Given: userId=1, date=2026-01-28
- When: steps=1000을 저장하면
- Then: 조회 시 해당 날짜 steps=1000이 반환된다.

## 6. 기술/제약(Constraints)
- Java 17, Spring Boot, (DB는 수업 상황에 맞춰 H2/메모리/기존 설정)
