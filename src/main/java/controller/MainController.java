package controller;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// http://localhost:8080/myapp/main.do

@Controller
public class MainController {
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rsExecuteQuery;
	private static int rsExecuteUpdate;
	// ResultSetMetadata 객체 변수 선언
	private static ResultSetMetaData rsmd;

	@RequestMapping("main.do")
	public ModelAndView main() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main");
		return mav;
	}// end main()

	public void exit() throws SQLException {
		if (rsExecuteQuery != null)
			rsExecuteQuery.close();
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();
	}// end exit()

	@RequestMapping("process.do")
	public ModelAndView dbProcess(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Properties properties = new Properties();
		String resource = "C:\\Users\\SoluLink\\eclipse-workspace\\SQL-Client\\src\\main\\webapp\\resources\\dbconf.properties";

		try {
			Reader reader = new FileReader(resource);
			// properties객체가 properties파일을 읽어와서 Key와 Value형태로 분리해서 Map에 저장한다.
			properties.load(reader);
			String system = request.getParameter("system");
			// Key에 해당하는 Value값 얻어오기
			String url = properties.getProperty("rdbms." + system + ".url");
			String userid = properties.getProperty("rdbms." + system + ".userid");
			String password = properties.getProperty("rdbms." + system + ".password");
			String driver = properties.getProperty("rdbms." + system + ".driver");

			System.out.println("url : " + url);
			System.out.println("userid : " + userid);
			System.out.println("password : " + password);

			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				System.out.println("db 드라이버를 찾을 수 없습니다.");
			}
			try {
				conn = DriverManager.getConnection(url, userid, password);
				System.out.println("db 접속 성공");
			} catch (SQLException e) {
				System.out.println("db 접속 실패");
				mav.addObject("error", e);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("db설정 파일을 찾을 수 없습니다.");
			mav.addObject("error", e);
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////////

		String sql = request.getParameter("input");
		System.out.println("sql: " + sql);
		System.out.println("-------------------------------------");
		mav.addObject("sql", sql);
		long start = System.currentTimeMillis();

		String[] words = sql.split("\\s");
		if (words[0].equals("select") || words[0].equals("SELECT")) {
			try {
				pstmt = conn.prepareStatement(sql);
				// 쿼리문 실행
				rsExecuteQuery = pstmt.executeQuery();
				// 테이블의 메타데이터 가져오기
				rsmd = rsExecuteQuery.getMetaData();
				mav.addObject("rsmd", rsmd);
				// ResultSetMetaData의 getColumnCount() 메서드는 칼럼의 개수를 반환한다.
				int columnCount = rsmd.getColumnCount();
				mav.addObject("columnCount", columnCount);

				List<Integer> listIndex = new ArrayList<Integer>();
				for (int index = 1; index <= columnCount; index++) {
					// 인덱스
					System.out.println("index:" + index);
					listIndex.add(index);
				}
				mav.addObject("listIndex", listIndex);
				System.out.println();
				List<String> listColumn = new ArrayList<String>();
				for (int i = 1; i <= columnCount; i++) {
					// 칼럼명을 반환한다.
					String strColumn = rsmd.getColumnName(i);
					// 리스트에 칼럼들 추가
					listColumn.add(strColumn);
				}
				mav.addObject("columnName", listColumn);
				request.setAttribute("listColumn", listColumn);
				System.out.println();
				List<String> listType = new ArrayList<String>();
				for (int i = 1; i <= columnCount; i++) {
					// 지정된 열의 SQL형식을 검색한다.
					String type = rsmd.getColumnTypeName(i);
					System.out.println("tpye : " + type);
					listType.add(type);
				}
				mav.addObject("listType", listType);
				System.out.println();
				List<Integer> listPrecision = new ArrayList<Integer>();
				for (int i = 1; i <= columnCount; i++) {
					// 컬럼의 크기를 반환한다.
					int precision = rsmd.getPrecision(i);
					System.out.println("precision : " + precision);
					listPrecision.add(precision);
				}
				mav.addObject("listPrecision", listPrecision);
				System.out.println();
				List<Integer> listNullable = new ArrayList<Integer>();
				for (int i = 1; i <= columnCount; i++) {
					// 데이터의 허용여부를 나타낸다.
					int nullable = rsmd.isNullable(i);
					System.out.println("nullable : " + nullable);
					listNullable.add(nullable);
				}
				mav.addObject("listNullable", listNullable);
				System.out.println();
				List<Integer> listScale = new ArrayList<Integer>();
				for (int i = 1; i <= columnCount; i++) {
					// 데이터의 허용여부를 나타낸다.
					int scale = rsmd.getScale(i);
					System.out.println("scale : " + scale);
					listScale.add(scale);
				}
				mav.addObject("listScale", listScale);
				// 칼럼에 해당하는 데이터
				List<Object> listData = null;
				List<List> listData2 = new ArrayList<List>();
				Object objData = new Object();

				// 수행건수 세는 변수
				int count = 0;
				while (rsExecuteQuery.next()) {
					// mav.addObject("listData", listData);
					listData = new ArrayList<Object>();
					count++;

					for (int i = 1; i <= columnCount; i++) {
						objData = rsExecuteQuery.getObject(i);
						System.out.println("data: " + objData);
						listData.add(objData);
					}
					listData2.add(listData);
					System.out.println();
				}
				mav.addObject("listData2", listData2);
				System.out.println(listData);
				mav.addObject("count", count);

			} catch (SQLException e1) {
				System.out.println("SQL관련 에러");
				e1.printStackTrace();
				mav.addObject("error", e1);
				request.setAttribute("e1", e1);
			} finally {
				try {
					exit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} // end if
		else if (words[0].equals("insert") || words[0].equals("INSERT") || words[0].equals("delete")
				|| words[0].equals("DELETE") || words[0].equals("update") || words[0].equals("UPDATE")) {
			try {
				pstmt = conn.prepareStatement(sql);
			} catch (SQLException e2) {
				e2.printStackTrace();
				request.setAttribute("e2", e2);
				mav.addObject("error", e2);
			}
			try {
				// 쿼리문 실행
				rsExecuteUpdate = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				mav.addObject("error", e);
			} finally {
				try {
					exit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		long end = System.currentTimeMillis();
		double executeTime = ((end - start) / 1000.0);
		System.out.println("수행시간: " + executeTime);
		mav.addObject("executeTime", executeTime);
		mav.setViewName("process");

		return mav;
	}// end dbProcess()


	/*
	 * @RequestMapping("process3.do") public ModelAndView
	 * mysqlSelect(HttpServletRequest request) { ModelAndView mav = new
	 * ModelAndView();
	 *
	 * // conn = getConnectionMySQL(); String sql = request.getParameter("input");
	 * try { pstmt = conn.prepareStatement(sql); } catch (SQLException e) {
	 * System.out.println("MySQL쿼리문에서 예외 발생"); e.printStackTrace(); } try { //
	 * select일때 executeQuery 사용 rs = pstmt.executeQuery(); System.out.println("id "
	 * + " name"); while (rs.next()) { int id = rs.getInt(1); // String id =
	 * rs.getObject(1); String name = rs.getString(2); mav.addObject("id", id);
	 * mav.addObject("name", name); System.out.println(id + "   " + name); } } catch
	 * (SQLException e) { e.printStackTrace(); } finally { try { exit(); } catch
	 * (SQLException e) { e.printStackTrace(); } } return mav; }
	 */

	/*
	 * @RequestMapping("process2.do") public String oracleExecute(HttpServletRequest
	 * request) { try { // conn = getConnectionOracle(); String sql =
	 * request.getParameter("input"); System.out.println("sql : " + sql); pstmt =
	 * conn.prepareStatement(sql); rs = pstmt.executeQuery();
	 *
	 * while (rs.next()) { String name = rs.getString(1);
	 *
	 * System.out.println("name: " + name); } } catch (SQLException e) {
	 * System.out.println("Oracle쿼리문에서 예외 발생"); e.printStackTrace(); } finally { try
	 * { // DB종료 exit(); } catch (SQLException e) { e.printStackTrace(); } } return
	 * "process"; }
	 */

}// end class


