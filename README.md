# Walklog API

매일 걸음 수를 기록하고 조회하는 REST API

---

## 📋 PRD (Product Requirements Document)

### 1. 프로젝트 개요

**목적:**  
사용자가 매일 걸음 수를 기록하고, 날짜별로 조회할 수 있는 간단한 API 제공

**핵심 기능:**
- 걸음 수 기록 (날짜, 걸음 수)
- 특정 날짜 걸음 수 조회
- 전체 기록 조회

---

### 2. 기능 요구사항

#### 2.1 걸음 수 기록 (POST)

**엔드포인트:** `POST /api/steps`

**요청 Body:**
```json
{
  "date": "2026-01-31",
  "steps": 8500
}
```

**응답 (201 Created):**
```json
{
  "id": 1,
  "date": "2026-01-31",
  "steps": 8500
}
```

**검증 규칙:**
- `date`: 필수, 날짜 형식 (yyyy-MM-dd)
- `steps`: 필수, 0 이상의 정수

---

#### 2.2 특정 날짜 걸음 수 조회 (GET)

**엔드포인트:** `GET /api/steps/{date}`

**예시:** `GET /api/steps/2026-01-31`

**응답 (200 OK):**
```json
{
  "id": 1,
  "date": "2026-01-31",
  "steps": 8500
}
```

**응답 (404 Not Found):**
```json
{
  "error": "해당 날짜의 기록을 찾을 수 없습니다."
}
```

---

#### 2.3 전체 기록 조회 (GET)

**엔드포인트:** `GET /api/steps`

**응답 (200 OK):**
```json
[
  {
    "id": 1,
    "date": "2026-01-31",
    "steps": 8500
  },
  {
    "id": 2,
    "date": "2026-01-30",
    "steps": 10200
  }
]
```

---

### 3. 기술 스택

| 항목 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.2.2 |
| Build Tool | Gradle |
| Database | H2 (In-Memory) |
| ORM | Spring Data JPA |
| Validation | Spring Validation |

---

### 4. 데이터 모델

#### Step Entity

| 필드 | 타입 | 제약조건 | 설명 |
|------|------|----------|------|
| id | Long | PK, Auto Increment | 고유 ID |
| date | LocalDate | NOT NULL, UNIQUE | 날짜 |
| steps | Integer | NOT NULL, >= 0 | 걸음 수 |

---

### 5. API 명세

| Method | Endpoint | 설명 | 상태 |
|--------|----------|------|------|
| GET | `/` | API 실행 확인 | ✅ 완료 |
| GET | `/api/health` | 헬스 체크 | ✅ 완료 |
| POST | `/api/steps` | 걸음 수 기록 | 🔜 예정 |
| GET | `/api/steps/{date}` | 특정 날짜 조회 | 🔜 예정 |
| GET | `/api/steps` | 전체 기록 조회 | 🔜 예정 |

---

### 6. 프로젝트 구조

```
src/main/java/com/walklog/api/
├── controller/      # REST API 컨트롤러
├── service/         # 비즈니스 로직
├── repository/      # 데이터 접근 계층
├── entity/          # JPA 엔티티
├── dto/             # 요청/응답 DTO
└── WalklogApiApplication.java
```

---

### 7. 개발 단계

- [x] **Phase 1**: 프로젝트 초기 설정
  - [x] Spring Boot 프로젝트 생성
  - [x] 기본 패키지 구조 생성
  - [x] HealthController 추가
  - [x] README PRD 작성

- [ ] **Phase 2**: 도메인 모델 구현
  - [ ] Step Entity 생성
  - [ ] StepRepository 생성
  - [ ] 기본 CRUD 테스트

- [ ] **Phase 3**: API 구현
  - [ ] StepService 생성
  - [ ] StepController 생성
  - [ ] DTO 클래스 생성
  - [ ] Validation 추가

- [ ] **Phase 4**: 테스트 & 문서화
  - [ ] 단위 테스트 작성
  - [ ] 통합 테스트 작성
  - [ ] API 문서화

---

## 🚀 실행 방법

### 1. 프로젝트 클론
```bash
git clone https://github.com/ylovepk67/walklog-api.git
cd walklog-api
```

### 2. 실행
```bash
# Windows
gradlew.bat bootRun

# Mac/Linux
./gradlew bootRun
```

### 3. 확인
- API: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:walklogdb`
  - Username: `sa`
  - Password: (비워두기)

---

## 🧪 테스트

```bash
# 전체 테스트 실행
gradlew.bat test

# 특정 테스트 실행
gradlew.bat test --tests "HealthControllerTest"
```

---

## 📝 Git 브랜치 전략

- `main`: 프로덕션 브랜치
- `feat/*`: 새 기능 개발
- `fix/*`: 버그 수정
- `docs/*`: 문서 수정
- `test/*`: 테스트 코드

---

## 📄 라이선스
MIT License
=======
- (별도 PRD 문서 또는 요구사항 목록 링크 추가 권장)

---

## 👤 Author
ylovepk67

## Run
- Java 17
- ./gradlew bootRun

