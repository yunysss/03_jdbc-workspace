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

![화면 캡처 2022-12-14 160530](https://user-images.githubusercontent.com/115604544/207528626-07ad83e0-ced0-45d9-aed1-859ed3df0e83.png)

- M (Model) : 데이터 처리 담당
- V (View) : 화면을 담당 (출력 및 입력)
- C (Controller) : 사용자의 요청을 처리해주는 담당 
### 2_2. Statement vs. PreparedStatement
- 둘 다 sql문을 실행하고 결과를 받아내는 객체 (둘 중에 하나 이용)
- Statement가 PreparedStatement의 부모 (상속구조)
- 차이점
  - Statement : sql문을 바로 전달하면서 실행시키는 객체 (sql문을 완성형태로 만들어 둬야함 == 사용자가 입력한 값들이 다 채워진 형태)
    ```
    1) Connection 객체를 통해 Statement 객체 생성
    > stmt = conn.createStatement();
    2) Statement 객체를 통해 sql문 실행 결과 받기
    > 결과 = stmt.executeXXX(완성된sql문);
    ```
  - PreparedStatement : "미완성된 sql문"을 잠시 보관해둘 수 있는 객체 (사용자가 입력한 값들을 채워두지 않고 각각 들어갈 공간만 미리 확보)
    ```
    1) Connection 객체를 통해 PreparedStatement 객체 생성   
    > pstmt = conn.prepareStatement([미]완성된sql문); => ? (== holder) 공간 확보
    2) pstmt에 담긴 sql문이 미완성된 상태일 경우 우선 완성시켜야함
    > pstmt.setXXX(1, "대체할값");   
    > pstmt.setXXX(2, "대체할값");   
        ...   
    => pstmt.setString(홀더순번, 대체할값);	=> '대체할값' (양옆에 홑따옴표 감싸준 상태로 들어감)   
      pstmt.setInt(홀더순번, 대체할값);	=> 대체할값   
    3) 해당 완성된 sql문 실행 결과 받기   
    > 결과 = pstmt.executeXXX();    
    ```
### 2_3. Properties 활용
- 정적코딩방식 : jdbc driver구문, 접속할 db의 url, 계정명, 비번이 자바코드내에 명시적으로 작성 
  - 문제점 : dbms가 변경되었을 경우, 접속할 db의 url, 계정명/비번이 변경될 경우 ⇒ 자바소스코드 수정   
  ⇒ 수정된 내용을 반영시키고자 한다면 프로그램 재구동해야됨 (프로그램이 비정상적으로 종료됐다가 다시 구동)   
  ⇒ 사용자 입장에서 프로그램이 멈추는 상황
- 동적코딩방식 : db관련된 구문들을 별도로 관리하는 외부 파일(.properties)을 만들어서 관리
  - 외부파일로부터 읽어들여서 반영이 되도록 코드 짜놓음    
  ⇒ 수정될 일이 있다면 자바코드가 아닌 외부파일을 열어서 수정 
  ⇒ 프로그램 재구동시킬 필요 없음
  - properties
    - map계열의 컬렉션으로 키+밸류 세트로 저장
    - 주로 키, 밸류 모두 문자열로 기술
    - .properties 또는 .xml 파일과 작업
    - driver.properties
      ```
      driver=oracle.jdbc.driver.OracleDriver
      url=jdbc:oracle:thin:@localhost:1521:xe
      username=JDBC
      password=JDBC
      ```
    - query.xml
      ```xml
      <?xml version="1.0" encoding="UTF-8"?>
      <!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
      <properties>
        <entry key="insertMember">
          INSERT
            INTO MEMBER(USER_NO, USER_ID, USER_PWD, USER_NAME, GENDER, AGE, EMAIL, PHONE, ADDRESS, HOBBY, ENROLL_DATE)
          VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)                       
        </entry>
      </properties>
      ```
    - 전역변수로 Properties 생성 후 기본 생성자에 load 메소드 실행 : 사용자가 어떤 서비스 요청 할때마다 결국 new MemberDao().xxxxxx(); 호출하기 때문에
      ```java
      public class MemberDao {
        private Properties prop = new Properties();

        public MemberDao() {
          try {
            prop.loadFromXML(new FileInputStream("resources/query.xml"));
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
      ```
    - .getproperty(String key)로 접근
### 2_4. JDBCTemplate (공통 템플릿) : 매번 반복적으로 작성될 코드를 메소드로 정의
```java
public class JDBCTemplate {

	// 1. Connection 객체 생성 (DB와 접속) 후 해당 생성된 Connection 반환해주는 메소드
	public static Connection getConnection() {
		
		Properties prop = new Properties();
		Connection conn = null;
		try {
			prop.load(new FileInputStream("resources/driver.properties"));
			
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	// 2. commit 처리해주는 메소드 (Connection 전달받아서)
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 3. rollback 처리해주는 메소드 (Connection 전달받아서)\
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 4. Connection 객체 전달받아서 반납시켜주는 메소드
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 5. Statement 관련 객체 전달받아서 반납시켜주는 메소드
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 6. ResultSet 객체 전달받아서 반납시켜주는 메소드
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
```
- 다른 클래스에서 JDBCTemplate import 시 import static com.br.common.JDBCTemplate.*; 하여 바로 메소드에 접근할 수 있게 하기
