# JDBC
## 1. JDBC_Basic
## 1_1. JDBC(Java DataBase Connectivity)
- 자바에서 데이터베이스에 접근할 수 있게 해주는 Programming API

![Untitled](https://user-images.githubusercontent.com/115604544/207484629-b055f003-8988-42a8-8b88-629375ce403e.png)

## 1_2. JDBC 사용 객체
- DriverManager : Connection 객체를 생성하기 위한 객체
- Connection : DB에 접속해서 DB의 연결정보를 담고 있는 객체 (+ Statement 객체를 생성하기 위한 객체)
- [Prepared]Statement : 연결된 DB에 sql문을 전달해서 실행하고 그 결과를 받아내는 객체 ***
- ResultSet : select문 실행 후 조회된 결과물들이 담겨있는 객체
## 1_3. JDBC 과정 (순서 중요**)
- 1. JDBC Driver 등록 : 해당 DBMS (오라클)가 제공하는 클래스 등록
  ```java
  Class.forName("oracle.jdbc.driver.OracleDriver");
  ```
  => ojdbc6.jar 파일 추가 (프로젝트 우클릭 - Properties -  Java Build Path - Libraries - Modulepath - Add External Jars - ojdbc6열기 - Apply)   
  => 예외 처리 ClassNotFound ⇒ ojdbc6.jar 파일 추가 안 했거나 오타 있을 경우 발생
- 2. Connection 객체 생성 : 연결하고자 하는 DB정보를 제시해서 해당 DB와 연결된 Connection 생성
  ```java
  Connection conn = DriverManager.getConnection(“url”, “계정명”, “비밀번호”);       
  ```
  => url = jdbc:oracle:thin:@localhost:1521:xe
- 3. Statement 생성 : Connection 객체를 이용해서 생성 (sql문 실행 및 결과 받는 객체)
  ```java
  Statement stmt = conn.createStatement();
  ```
- 4. sql문 전달하면서 실행 : Statement 객체를 이용해서 sql문 실행
- 5. 결과 받기
  - SELECT문 실행 ⇒ ResultSet객체 (조회된 데이터들이 담겨 있음) => 6_1)
    ```java
    String sql = "SELECT TNO, TNAME, TDATE FROM TEST WHERE TNO = 1";
    ResultSet rset = stmt.executeQuery(sql);
    ```
    => stmt.executeQuery(쿼리문) : ResultSet
  - DML문 실행 => int (처리된 행 수) => 6_2)
    ```java
    String sql = "INSERT INTO TEST VALUES(SEQ_TESTNO.NEXTVAL, '테스트4', SYSDATE)";
    int result = stmt.executeUpdate(sql);
    ```
    => stmt.executeUpdate(dml문) : int
- 6_1.

  ![Untitled](https://user-images.githubusercontent.com/115604544/207487742-fe6a9652-62ff-4924-b9a6-5844727d827d.png)

  => rset.next() : 행 커서 움직여주는 역할, 해당 행이 있으면 true, 없으면  false 반환   
  => rset으로부터 “어떤 컬럼”의 값을 “어떤 타입”을 뽑을 것인지 제시   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rset.getInt(”컬럼명”), rset.getString(”컬럼명”), rset.getDate(”컬럼명”)   
  => 컬럼명 대소문자 상관 없음 / 컬럼 순번 제시 가능 (권장 X)   
  - ResultSet에 담겨 있는 데이터들을 하나씩 뽑아서 vo객체에 옮겨 담기
    ```java
    if(rset.next()){
      Test t = new Test(rset.getInt("TNO"), rset.getString("tname"), rset.getDate(3));
    }
    ```
  - 여러행 조회 시 ArrayList에 옮겨 담기
    ```java
    while(rset.next()) {
      list.add(new Test(rset.getInt("tno"), 
                        rset.getString("tname"), 
                        rset.getDate("tdate")));
    }
    ```
- 트랜잭션 처리 (성공시 commit, 실패시 rollback)
  ```java
  if(result > 0) {
    conn.commit();
  } else {
    conn.rollback();
  }
  ```
- 7. 다 사용한 JDBC용 객체들 반드시 자원 반납 (생성된 역순으로 반납)
  ```java
  rset.close();
  stmt.close();
  conn.close();
  ```
  => DML 문은 ResultSet 생성 안했으므로 반납 안해도 됨
- 8. 조회 결과 출력
  - 한 행 조회
    ```java
    if(t == null) {
      System.out.println("조회 결과가 없습니다.");
    } else { 
      System.out.println(t);
    }
    ```
  - 여러 행 조회
    ```java
    if(list.isEmpty()) {
      System.out.println("조회 결과가 없습니다.");
    } else {
      for(int i=0; i<list.size(); i++) {
        System.out.println(list.get(i));
      }
    }
    ```
[참고] OJDBC 추가   
C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib\ojdbc6 복사 후 C:드라이브에 dev 폴더 생성 후 붙여넣기 ⇒ eclipse project 우클릭 ⇒ Properties ⇒ Java Build Path ⇒ Libraries ⇒ Add Externaml JARs에서 추가
## 2. MVC
### 2_1. MVC
