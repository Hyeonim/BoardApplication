package kr.or.yi.board.DAO;

import java.sql.*;

public class JDBCConnection {
    public Connection con;
    public Statement stmt;
    public PreparedStatement psmt;
    public ResultSet rs;

    public JDBCConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/Board?serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&useSSL=false";
            String id = "db-id";
            String pw = "db-password";

            con = DriverManager.getConnection(url, id, pw);
            con.setAutoCommit(false);
            System.out.println("DB연결 성공");
        } catch (Exception e) {
            System.out.println("연결 실패");
            e.printStackTrace();
        }
    }
}
