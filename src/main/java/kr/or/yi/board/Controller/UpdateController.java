package kr.or.yi.board.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kr.or.yi.board.DTO.Board;
import kr.or.yi.board.Service.BoardService;
import kr.or.yi.board.Service.BoardServiceImpl;
import kr.or.yi.board.Utils.SceneUtil;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateController {

    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfWriter;
    @FXML
    private TextArea taContent;

    private BoardService boardService = new BoardServiceImpl();
    int boardNo;

    public void read(int boardNo) {
        this.boardNo = boardNo;
        Board board = boardService.select(boardNo);
        tfTitle.setText(board.getTitle());
        tfWriter.setText(board.getWriter());
        taContent.setText(board.getContent());
    }

    public void moveToList(ActionEvent event) throws IOException {

        SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());

    }

    public void update(ActionEvent event) throws SQLException, IOException {

        Board board = new Board(tfTitle.getText(), tfWriter.getText(), taContent.getText());
        board.setBoard_no(boardNo);

        int result = boardService.update(board);
        if(result >0){

            System.out.println("글 수정 완료");
            SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
        }
    }

    public void delete(ActionEvent event) throws IOException {
//        Board board = new Board(tfTitle.getText(), tfWriter.getText(), taContent.getText());
//        board.setBoard_no(boardNo);
//
//        int result = boardService.delete(board.getBoard_no());
//        if(result >0){
//            System.out.println("삭제");
//        }
//
//        SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("List Delete");
        alert.setHeaderText("delete?");

        int result = 0;
        if(alert.showAndWait().get() == ButtonType.OK){
            result = boardService.delete(boardNo);
        }
        if(result >0){
            System.out.println("삭제");
        }

        SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());

    }
    public void close() {

        System.exit(0);

    }

}
