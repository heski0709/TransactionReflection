# 스프링의 Transactional 어노테이션 임의 구현해보기
수업을 듣던 중 Spring의 @Transactional를 사용하면
내부적으로 DB트랜잭션에 대한 처리를 해준다고 하기에 
어떠한 방식으로 처리를 해주는 것인지 궁금하여 간략하게 구현해보았다
DB와의 연동은 마이바티스를 사용하였다.

## 힘들었던 점
1. 커스텀 어노테이션을 선언한 클래스 찾아내기 
2. 어노테이션을 선언한 클래스에서 원하는 메소드만 실행하기
3. Service 클래스에서 가지고 있어야하는 Mapper 변수에 값 넣어주기
4. Service 클래스의 메소드를 invoke로 강제 실행 후의 반환 타입에 대한 TransactionProxy에서의 처리

## 해결한 방법
1. 프로젝트 폴더부터 순차적으로 모든 폴더에 접근하여 찾아낸 파일 정보를 HashSet에 저장하고, .class파일일 경우에만 Class 클래스의 forName를 사용하여 클래스 정보를 얻어옴. 
   getAnnotations 메소드로 해당 클래스에서 선언한 어노테이션을 얻어온 후, 얻어온 어노테이션 이름과 커스텀 어노테이션의 이름을 비교하여 맞을 경우 transactionClass 변수에 클래스 정보를 대입했다.

2. 클래스의 정보를 가지고 있는 transactionClass를 이용해 getDeclaredMethods로 해당 클래스의 메소드 정보를 가져왔고, 실행하기 원하는 메소드명과 getDeclaredMethods로 가져온 메소드들의 이름과 비교하여 같을 경우 getParameterTypes 메소드를 사용하여 매개변수를 알아냈다.
    그 후 Method method 변수에 trasactionClass.getDeclaredMethod(메소드명, 매개변수)를 사용하여 메소드를 가져오고, invoke 메소드를 통해 원하는 메소드를 강제 실행시켰다.

3. DB트랜잭션 처리를 위해 DBConnect 정보를 TransactionProxy 클래스가 지니고 있었는데, Service에서 Mapper 클래스의 메소드를 실행해야하기 때문에 Mapper 인스턴스를 넣어줘야했다. Service 클래스에서 Mapper 변수에 setter를 생성하고 TransactionProxy 클래스에서 invoke를 통해 Mapper에 대해 주입시켰다.

4. 쿼리 결과에 따라 여러가지의 반환타입이 존재했는데 그 반환값을 어떻게 받을지 고민이 됐었다. 처음에는 반환타입에 따라 메소드를 각각 작성했으나 이 후 제네릭을 사용하여 메소드를 여러개 만들지 않고 하나의 메소드로 통합했다.
   다만 DML 쿼리 경우에는 리턴값이 int고 리턴값에 따라 rollback과 commit를 해줘야했기 때문에 리턴값이 int일 경우에만 결과값에 따라 commit과 rollback이 되도록 추가해줬다
   
## 느낀점
스프링의 Transaction Manager가 이렇게 만들어지진 않겠지만, 직접 구현을 해보면서 스프링 내부적으로 어떤 식으로 돌아가고 있는지에 대해 좀 더 상세하게 이해할 수 있는 기회가 되었던 것 같다.
