# 스프링의 Transactional 어노테이션 임의 구현해보기
---
국비에서 수업을 듣던 중 Spring의 @Transactional를 사용하면
내부적으로 DB트랜잭션에 대한 처리를 해준다고 하기에 
어떠한 방식으로 처리를 해주는 것인지 궁금하여 구현해보았다

---
## 진행과정
Transaction이라는 커스텀 어노테이션을 만들고 (Target은 CLASS로 선언)
만들어진 어노테이션을 ElementTestService 클래스 위에 선언했다.
