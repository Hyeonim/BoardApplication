package kr.or.yi.board.DAO;

import javafx.scene.image.Image;
import kr.or.yi.board.DTO.Board;

//import DTO.Board;

import java.io.*;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BoardDAO extends JDBCConnection {
    SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    FileInputStream fis;
    private Image image;


    public int insert(Board board) throws SQLException {
        int result = 0;
        String sql = "INSERT INTO Board(title, writer, content,file) "
                + "VALUES(?,?,?,?)";

        Savepoint savepoint = null;
        try {
            savepoint = con.setSavepoint("insertSavePoint");
            psmt = con.prepareStatement(sql);
            psmt.setString(1, board.getTitle());
            psmt.setString(2, board.getWriter());
            psmt.setString(3, board.getContent());

            if (board.getFile() != null) {
                fis = new FileInputStream(board.getFile());
                psmt.setBinaryStream(4, (InputStream) fis);
            } else {
                psmt.setBinaryStream(4, null);
            }

            result = psmt.executeUpdate(); //db와 연동되어 들어감

            if (result > 0) {
                con.commit();
            }
        } catch (SQLException e) {
            try {
                con.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.out.println("게시글 등록 시, 예외가 발생");
            e.printStackTrace();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;

    }

    public List<Board> selectList() {
        LinkedList<Board> boardList = new LinkedList<>();

        String sql = " SELECT * FROM Board ORDER BY board_no ";


        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Board board = new Board();
                board.setBoard_no(rs.getInt("board_no"));
                board.setTitle(rs.getString("title"));
                board.setWriter(rs.getString("writer"));
                board.setContent(rs.getString("content"));
                board.setReg_date(dataFormat.format(rs.getTimestamp("reg_date")));
                board.setUpd_date(dataFormat.format(rs.getTimestamp("upd_date")));
                boardList.add(board);
            }

        } catch (Exception e) {
            System.out.println("게시글 목록 조회시 예외 발생");
            e.printStackTrace();

        }
        return boardList;

    }

    public Board select(int board_no) {
        Board board = new Board();
        String sql = "SELECT * FROM Board WHERE board_no = ? ";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, board_no);
            rs = psmt.executeQuery();
            if (rs.next()) {
                board.setBoard_no(rs.getInt("board_no"));
                board.setTitle(rs.getString("title"));
                board.setWriter(rs.getString("writer"));
                board.setContent(rs.getString("content"));
                board.setReg_date(dataFormat.format(rs.getTimestamp("reg_date")));
                board.setUpd_date(dataFormat.format(rs.getTimestamp("upd_date")));
                InputStream is = rs.getBinaryStream("file");
                if(is != null){
                    board.setIsfile(is);
                    is.close();

                }
//                OutputStream os = new FileOutputStream("photo.jpg");
//                byte[] fileBuffer = new byte[1024];
//                int size = 0;
//                while ((size = is.read(fileBuffer)) != -1) {
//                    os.write(fileBuffer, 0, size);
//
//                }
//                os.close();
//                is.close();
//                File file = new File("file:photo.jpg");
                board.setIsfile(is);



            } else {
                System.out.println(board_no + "번 게시물이 없습니다.");
                board = null;
            }
        } catch (Exception e) {
            System.out.println("게시물 조회 예외 발생");
            e.printStackTrace();
        }
        return board;
    }

    public int update(Board board) throws SQLException {
        int result = 0;
        String sql = " UPDATE Board SET title=?, writer = ?, content = ?, upd_date = now() where board_no = ?";
        Savepoint savepoint = null;

        try {
            savepoint = con.setSavepoint("UpdateSavePoint");
            psmt = con.prepareStatement(sql);
            //prepareSt이게 ? ? ? 들어가는 것
            psmt.setString(1, board.getTitle());
            psmt.setString(2, board.getWriter());
            psmt.setString(3, board.getContent());
            psmt.setInt(4, board.getBoard_no());
            result = psmt.executeUpdate();

            if (result > 0) {
                con.commit();
            }
        } catch (SQLException e) {
            try {
                con.rollback(savepoint);

            } catch (SQLException e1) {
                System.out.println("게시글 수정시 예외 발생");
                e1.printStackTrace();
            }
            e.printStackTrace();

        }
        return result;
    }

    public int delete(int board_no) {
        int result = 0;
        String sql = " Delete from Board where board_no = ?";
        Savepoint savepoint = null;

        try {
            savepoint = con.setSavepoint("DeleteSavePoint");
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, board_no);
            result = psmt.executeUpdate();

            if (result > 0) {
                con.commit();
            }

        } catch (SQLException e) {
            try {
                con.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return result;
    }

    public int selectTotalCount() {
        String sql = "SELECT count(*) from Board Order by reg_date desc";
        int count = 0;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                count = rs.getInt(1);
            }

//            System.out.println("총 게시물 : " + rs.getInt(1));
        } catch (Exception e) {
            System.out.println("게시물 카운터 조회시 예외 발생");
            e.printStackTrace();
        }
        System.out.println(count);
        return count;
    }

    public List<Board> selectPageList(int pageNo) {
        LinkedList<Board> boardList = new LinkedList<>();

        String sql = "SELECT * from Board Order by board_no limit 10 offset ?";
        try {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, pageNo * 10);
            rs = psmt.executeQuery();
            while (rs.next()) {
                Board board = new Board();
                board.setBoard_no(rs.getInt("board_no"));
                board.setTitle(rs.getString("title"));
                board.setWriter(rs.getString("writer"));
                board.setContent(rs.getString("content"));
                board.setReg_date(dataFormat.format(rs.getTimestamp("reg_date")));
                board.setUpd_date(dataFormat.format(rs.getTimestamp("upd_date")));

                boardList.add(board);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return boardList;
    }

}
