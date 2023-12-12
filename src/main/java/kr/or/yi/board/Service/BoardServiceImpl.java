package kr.or.yi.board.Service;

import kr.or.yi.board.DAO.BoardDAO;
import kr.or.yi.board.DTO.Board;

import java.sql.SQLException;
import java.util.List;

public class BoardServiceImpl implements BoardService{
    private BoardDAO boardDAO = new BoardDAO();


    @Override
    public List<Board> list() {
        List<Board> boardList = (List<Board>)boardDAO.selectList();
        return boardList;
    }

    @Override
    public Board select(int boardNo) {
        Board board = boardDAO.select(boardNo);
        return board;
    }

    @Override
    public int insert(Board board) throws SQLException {
        int result = boardDAO.insert(board);
        return result;
    }

    @Override
    public int update(Board board) throws SQLException {
        int result = boardDAO.update(board);
        return result;
    }

    @Override
    public int delete(int boardNo) {
        int result = boardDAO.delete(boardNo);
        return result;

    }

    @Override
    public List<Board> pageList(int pageNo) {
        List<Board> boardList = (List<Board>) boardDAO.selectPageList(pageNo);
        return boardList;
    }

    @Override
    public int totalListCount() {
        int count =(int) boardDAO.selectTotalCount();
        return count;
    }


}
