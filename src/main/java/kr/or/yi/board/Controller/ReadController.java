package kr.or.yi.board.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kr.or.yi.board.DTO.Board;
import kr.or.yi.board.Service.BoardService;
import kr.or.yi.board.Service.BoardServiceImpl;
import kr.or.yi.board.Utils.SceneUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReadController implements Initializable {
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfWriter;
    @FXML
    private TextArea taContent;

    @FXML
    private ImageView imageView;

    private Image image;

    Stage stage;

    Scene scene;
    Parent root;
    private BoardService boardService = new BoardServiceImpl();

    int boardNo; //게시물 번
    List<Integer> numArr;
    int targetValue = boardNo;
    int preValue = -1;
    int nextValue= -1;

    public void read(int boardNo) {
        numArr = listNum();
        this.boardNo = boardNo;
        Board board = boardService.select(boardNo);
        tfTitle.setText(board.getTitle());
        tfWriter.setText(board.getWriter());
        taContent.setText(board.getContent());
        if (board.getIsfile() != null) {
            image = new Image(board.getIsfile(), 345, 300, true, true);
            imageView.setImage(image);
        }
        else{

            imageView.setImage(null);
        }
    }

    public void moveToList(ActionEvent event) throws IOException {

        SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());

    }

    public void moveToUpdate(ActionEvent event) throws IOException {

        UpdateController updateController = (UpdateController) SceneUtil.getInstance().getController(UI.UPDATE.getPath());
        updateController.read(boardNo);
        Parent root = SceneUtil.getInstance().getRoot();

        SceneUtil.getInstance().switchScene(event, UI.UPDATE.getPath(), root);

    }

    public void deleteToList(ActionEvent event) throws IOException {
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
        if (alert.showAndWait().get() == ButtonType.OK) {
            result = boardService.delete(boardNo);
        }
        if (result > 0) {
            System.out.println("삭제");
        }
        SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());

    }

    public void moveToPre(ActionEvent event) {

        numArr = listNum();
        read(preValue);
    }

    public void moveToNext(ActionEvent event) {

        numArr = listNum();
        read(nextValue);
    }
    public List<Integer> listNum() {
        List<Board> boardList = new ArrayList<>();
        boardList = boardService.list();
        numArr = new ArrayList<>();
        for (Board board : boardList) {
            numArr.add(board.getBoard_no());
//            System.out.println(board.getBoard_no());
        }
        System.out.println(numArr);
        targetValue = boardNo;

        for(int i =0; i< numArr.size(); i++){
            if (numArr.get(i) == targetValue){
                if (i>0){
                    preValue = numArr.get(i-1);
                }
                if (i<numArr.size() -1){
                    nextValue = numArr.get(i+1);
                }
                break;
            }
        }
        return numArr;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            stage = (Stage) rootPane.getScene().getWindow();
            rootPane.setOnMousePressed((event1) ->{
                x =event1.getSceneX();
                y =event1.getSceneY();
            });

            rootPane.setOnMouseDragged((event1)->{
                stage.setX(event1.getScreenX() -x);
                stage.setY(event1.getScreenY() -y);
                stage.setOpacity(0.8f);
            });
//            rootPane.setOnDragDone((event1)->{
//                stage.setOpacity(1.0f);
//            });
//            rootPane.setOnMouseReleased((event1)->{
//                stage.setOpacity(1.0f);
//            });
        });

    }


    private double x = 0;
    private double y = 0;
    @FXML
    private AnchorPane rootPane;
    @FXML
    public void handleMouseClicked(MouseEvent event){
//        Platform.runLater(()->{
//            rootPane = (AnchorPane) event.getTarget();
//            stage = (Stage) rootPane.getScene().getWindow();
//            rootPane.setOnMousePressed((event1) ->{
//                x =event1.getSceneX();
//                y =event1.getSceneY();
//            });
//
//            rootPane.setOnMouseDragged((event1)->{
//                stage.setX(event1.getScreenX() -x);
//                stage.setY(event1.getScreenY() -y);
//                stage.setOpacity(0.8f);
//            });
////            rootPane.setOnDragDone((event1)->{
////                stage.setOpacity(1.0f);
////            });
////            rootPane.setOnMouseReleased((event1)->{
////                stage.setOpacity(1.0f);
////            });
//        });
    }

    public void close() {

        System.exit(0);

    }
}

