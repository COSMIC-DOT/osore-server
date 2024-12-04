## 📚 프로젝트 소개
> 어려운 코드를 살펴 볼 때, 학습 내용을 기록하고 모르는 것이 있으면 AI에게 물어보자!

충북대학교 컴퓨터공학과 1조 졸업 작품 결과물입니다.<br/>
충북대학교 컴퓨터공학과 졸업작품 및 오픈소스 동아리 대회 장려상 수상작입니다.

<br>

## 🔖 기능

<p align="center"> 
  <img width="45%" alt="스크린샷 2024-12-04 오후 12 19 45" src="https://github.com/user-attachments/assets/9f0cbc06-8a11-44ba-b1fa-ae266e86243a">
  <img width="45%" alt="스크린샷 2024-12-04 오후 12 19 54" src="https://github.com/user-attachments/assets/bfe9e6f0-3cb5-40d5-a69a-828da798a9c3">
</p>

<br>

### 소셜 로그인
- OAuth2.0을 기능을 활용해 Github, Google 로그인을 할 수 있습니다.
- 이를 Spring Security를 사용하기 보다 직접 경로를 매핑하는 방식을 채택했습니다.

<br>

### 코드 불러오기
> 사용자는 레포지토리의 파일을 확인한다.
- 사용자는 Github 내의 코드를 불러올 수 있습니다.
- 사용자는 레포지토리의 URI과 브랜치를 통해 원하는 코드 내역을 확인할 수 있습니다. 
- 코드를 불러올 때에는 API와 Clone 방식 중 신뢰성을 위해 Clone 방식을 채택했습니다.

<br>

### 학습 질문
> 사용자는 AI에게 질문을 던질 수 있다.
- 사용자는 AI에게 레포지토리에 대한 질문을 할 수 있습니다.
- RAG 방식을 활용하여 해당 레포지토리에 대한 질문이 가능합니다.
- 현재에는 답변 생성 시간동안 Connection을 유지하지만 보완할 계획입니다. 

<br>

### 학습 메모
> 사용자는 학습에 대한 메모를 작성한다.
- 사용자는 메모 탭을 눌러 자신의 학습 내역을 정리할 수 있습니다.
- 메모는 여러 페이지를 만들 수 있고 각각의 순서를 변경할 수 있습니다.

<br>

## 🏛️ 아키텍처

### 서버 및 데이터베이스 아키텍처
<p align="center">
  <img width="40%" alt="스크린샷 2024-12-04 오후 12 49 39" src="https://github.com/user-attachments/assets/4c340fbb-77d5-4f57-bc6c-8526dc4c07a0">
  <img width="35%" alt="스크린샷 2024-12-04 오후 12 43 34" src="https://github.com/user-attachments/assets/ea4b759c-7fdb-4c5c-b182-26af79b02270">
</p>

<br>

### 기술 스택
**Language** | Java 17

**Framework** | Spring Boot 3.2.4, Spring Data JPA

**Database** | MySQL, H2

**Build Tool** | Gradle 8.8

**Deploy** | Docker, Docker Compose

**API** | Github, OpenAI GPT-4

<br><br>

## 🤔 기술적 고민

### 🤨 OAuth2.0 로그인

- 

<details>
<summary>자세히보기</summary>

**문제 상황**

**해결 방안**

</details>
<br>

### 🔗 AI 답변이 오기까지 기다리기

- 

<details>
<summary>자세히보기</summary>

**문제 상황**

**해결 방안**

</details>

<br>
