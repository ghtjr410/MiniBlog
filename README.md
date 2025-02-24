# MiniBlog (Monolithic Version)

개인의 일상을 공유하는 블로그 플랫폼

## 🚀 기술 스택

- **Backend**: Java, Spring Boot, Spring Security, JPA, QueryDSL
- **Database**: MySQL, Redis
- **Infra**: Docker
- **Frontend**: React

## 👥 팀 구성

- **팀장** : 최호석 - REST API 설계, JWT 인증 인가 구현, 비즈니스 로직 개발
- **팀원** : 노유정 - Back 
- **팀원** : 김용현 - Front


## ✨ 주요 기능

### 📝 회원 기능
- **회원가입**
  - ID, 닉네임, 이메일 중복 체크
  - 이메일 인증을 통한 가입 승인
- **로그인**
  - Access Token & Refresh Token 발급
- **로그아웃**
  - Refresh Token 폐기 및 블랙리스트 등록

### 📰 게시글 기능
- **게시글 작성 및 수정**
- **게시글 삭제**
- **게시글 조회 (페이징 포함)**

### 💬 댓글 기능
- **댓글 작성 및 수정**
- **댓글 삭제**
- **댓글 조회 (페이징 포함)**

### ❤️ 좋아요 기능
- **게시글 좋아요 및 취소 (토글 방식)**


