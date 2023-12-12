package kr.or.yi.board.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import kr.or.yi.board.DTO.User;
import kr.or.yi.board.Service.UserService;
import kr.or.yi.board.Service.UserServiceImpl;
import kr.or.yi.board.Utils.SceneUtil;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField tfid;
    @FXML
    private TextField tfpw;
    private UserService userService = new UserServiceImpl();


    public void login(ActionEvent event) throws IOException {
        System.out.println(tfid.getText());
        System.out.println(tfpw.getText());

        String originalPassword = tfpw.getText();
        String hashedPassword = PasswordHashing.hashPassword(originalPassword);
        System.out.println("Original Password: " + originalPassword);
        System.out.println("Hashed Password: " + hashedPassword);

        if (tfid.getText().isEmpty() || tfpw.getText().isEmpty()) {
            errorIdPw();
        } else {
            User user = userService.select(tfid.getText());
            if (user.getUserid().isEmpty()) {
                isIdEmpty();
            } else {
                if (!Objects.equals(user.getPassword(), tfpw.getText())) {
                    errorPW();

                } else {
                    loginSuccess();
                    SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
                }
            }
        }
    }

    public void close() {

        System.exit(0);

    }

    public void errorIdPw() {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("error!");
        alert.setHeaderText(null);
        alert.setContentText("Id and Password check!");
        alert.showAndWait();
    }

    public void isIdEmpty() {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("error!");
        alert.setHeaderText(null);
        alert.setContentText("no id");
        alert.showAndWait();
    }

    public void loginSuccess() {
        Alert alert;
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("success");
        alert.setHeaderText(null);
        alert.setContentText("login success");
        alert.showAndWait();
    }

    public void errorPW() {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("error!");
        alert.setHeaderText(null);
        alert.setContentText("wrong password");
        alert.showAndWait();
    }

    public class PasswordHashing {

        public static String hashPassword(String password) {
            try {
                // MessageDigest를 사용하여 SHA-256 해시 함수 선택
                MessageDigest md = MessageDigest.getInstance("SHA-256");

                // 비밀번호를 바이트 배열로 변환하여 해시 계산
                byte[] hashBytes = md.digest(password.getBytes());

                // Base64를 사용하여 바이트 배열을 문자열로 변환
                return Base64.getEncoder().encodeToString(hashBytes);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}