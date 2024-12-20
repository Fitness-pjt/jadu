![header](https://capsule-render.vercel.app/api?type=waving&height=300&color=gradient&text=JADU&descAlign=60)

# 💡프로젝트 소개

![JADU.PNG](./image/JADU.png)

- 프로젝트 명 : 자두 (JADU)
- 프로젝트 기간 : 2024년 11월 10일 ~ 2024년 11월 27일 (2주)
- 프로젝트 소개 : AI 기반 맞춤형 운동 프로그램과 투두 리스트 관리, 커뮤니티 기능으로 사용자에게 체계적이고 동기부여되는 운동 경험을 제공합니다.

- 배포 링크 : http://jadu.today

  <br><br>

# PROJECT 팀원

|                                                    김민성                                                     |                                                              김현진                                                              |
| :-----------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------------: |
| <p align="center"><img src="https://avatars.githubusercontent.com/u/163739681?v=4" style="width:60px;" /></p> | <p align="center"><img src="https://avatars.githubusercontent.com/u/154870548?v=4" style="width:60px; border-radius: 50%" /></p> |
|                                [@MINSSUNGKIM](https://github.com/MINSSUNGKIM)                                 |                                             [@hyun0zin](https://github.com/hyun0zin)                                             |

<br><br>

# 💻 TECH STACKS

<h3>Environment</h3> 
<div><img src="https://img.shields.io/badge/visual studio code-007ACC?style=for-the-badge&logo=visualstudiocode&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"></div>

<h3>BACKEND</h3> 
<div>
<img src="https://img.shields.io/badge/java-000000?style=for-the-badge&logo=java&logoColor=black"> <img src="https://img.shields.io/badge/spring Boot-6DB33F?style=for-the-badge&logo=spring Boot&logoColor=white">
<img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=spring security&logoColor=white">
</div>

<h3>FRONTEND</h3> 
<div>
<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
<img src="https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white">
</div>

<h3>DATABASE</h3> 
<div>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
</div>

<h3>Deployment</h3> 
<div>
<img src="https://img.shields.io/badge/amazon-FF9900?style=for-the-badge&logo=amazon&logoColor=white">
 <img src="https://img.shields.io/badge/amazon ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"> <img src="https://img.shields.io/badge/amazon s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">
</div>

</div>

<br><br>

# 🎯 주요 기능

## 1. **사용자 맞춤형 운동 프로그램 생성**

- 생성형 AI를 활용한 사용자 정보 기반 맞춤형 운동 프로그램 추천
- YouTube API를 활용한 운동 영상 검색 및 선택
- 프로그램 기간 및 난이도 설정

## 2. **커뮤니티 시스템**

- 프로그램별 리뷰 작성
- Q&A 게시판
- 댓글 시스템
- 좋아요 기능

## 3. **Todo List 관리**

- 프로그램 기반 자동 Todo List 생성
- 운동 완료 체크 기능
- 캘린더 뷰 제공

## 4. **다양한 로그인 기능**

- KAKAO, GOOGLE AUTH 로그인 기능 구현
- JWT 토큰 기반 이메일 로그인 기능 구현

## 5. 외부 API 사용

- YouTube Data API v3
- ChatGPT API

<br><br>

![다양한_로그인_기능.PNG](./image/다양한%20로그인%20기능.png)
![맞춤형_프로그램_생성.PNG](./image/맞춤형%20프로그램%20생성.png)
![개인_맞춤형_프로그램.PNG](./image/개인%20맞춤형%20프로그램%20제공.png)
![일정_관리_시스템.PNG](./image/일정관리%20시스템.png)
![커뮤니티_시스템.PNG](./image/커뮤니티%20시스템.png)

<br><br>

# 기술적 의사 결정

## AI PROMPT 기술 도입 배경

- 유저 운동 정보를 받아 맞춤형 프로그램을 제작 시, 임의로 프로그램 생성하는데 어려움을 겪음.

<br>

> AI PROMPT 작성을 통해, 생성형 AI로부터 맞춤형 프로그램 정보 응답 받기
>
> ![AI 응답.PNG](./image/AI%20응답.png)

<br>

- 페르소나 설정 및 응답 규칙과 응답 형태를 JSON 형태로 반환하도록 규칙 설정
- 이후, JSON 파싱하여 DTO로 응답 데이터 관리

<br><br>

# ERD Diagram

![EDR_Diagram.PNG](./image/ERD_diagram.png)

<br><br>

# 프로젝트 후기

| 이름   | 내용                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| ------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 김민성 | 이번 프로젝트를 계기로 기획부터 배포까지 모든 작업을 되짚어가며, 여러 기술들과 개념들을 복습할 수 있었고, 앞으로의 다양한 프로젝트 개발 과정에서도 큰 흐름을 알고, 협업 과정이 원활하게 이뤄질 수 있을 것 같습니다. 또한 프로젝트에서 기존의 기술에 한계점을 느낄 때, 필요한 기술들을 새로 찾아보고 적용하는 방법도 익힐 수 있었고, 앞으로 다양한 기술을 적용함에 있어 겁먹지 않고 추진할 용기를 얻을 수 있었습니다. 이번 프로젝트에서 얻은 것들을 잘 정리하여 추후에 다른 프로젝트를 진행하는 데 큰 추진력을 얻고, 나아가 한 명의 개발자로 자리 잡을 동력을 얻었다 생각합니다. |
| 김현진 | 프로젝트를 통해 프론트엔드, 백엔드, 데이터베이스 등 웹 개발에 필요한 전반적인 기술을 접목하여 실무 감각을 익힐 수 있었고, 실제 서비스를 개발할 수 있는 좋은 기회였습니다. 프로젝트를 진행하면서 발생한 다양한 문제를 해결하면서 한층 더 성장할 수 있었다고 생각합니다. 또한 팀원과 함께 서로 아이디어를 공유하고 문제를 해결하는 과정을 통해 협업과 기획의 중요성에 대해서 다시 느낄 수 있었습니다.                                                                                                                                                                             |
