# walklog-api

산책 기록 API 서비스.

## 요구 사항 (실행 재현성)

- **Runtime**: Node.js 20.x LTS (또는 프로젝트에서 사용하는 버전)
- **Package manager**: npm 10.x / pnpm 9.x (선택)

## 로컬 실행 방법

```bash
# 저장소 클론 후
cd walklog-api

# 의존성 설치
npm install

# 환경 변수 설정 (필요 시 .env.example 참고 후 .env 생성)
# 예: DATABASE_URL=... PORT=3000

# 개발 서버 실행
npm run dev
```

| 환경 변수 | 필수 | 설명 |
|-----------|------|------|
| `PORT` | N | 서버 포트 (기본값: 3000) |
| `DATABASE_URL` | Y | DB 연결 문자열 (실제 값으로 교체) |

## API 명세

| Method | Path | 설명 |
|--------|------|------|
| (추가 예정) | | |

### 공통 에러 응답 형식

- **4xx/5xx** 시 본문 형식: `{ "error": "메시지", "code": "ERROR_CODE" }`
- **400**: 잘못된 요청 (입력 검증 실패 등)
- **404**: 리소스 없음
- **500**: 서버 내부 오류

## 입력 검증 규칙 (참고)

- 요청 바디는 JSON, Content-Type: `application/json` 필수.
- 필수 필드 누락·타입 불일치 시 400 반환.

## PRD / 요구사항

- (별도 PRD 문서 또는 요구사항 목록 링크 추가 권장)

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
