# Walklog API

## PRD 요약
- Goal: 사용자별 일자별 걸음 수 기록/조회
- In: 기록 저장, 기록 조회, 같은 날짜면 업데이트
- Out: 로그인/권한, 관리자 페이지

## API (예시)
- POST /api/steps
- GET /api/steps/{userId}

## Run
- Java 17
- ./gradlew bootRun
