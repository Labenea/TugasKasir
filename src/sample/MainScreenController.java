package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable  {
    public TextField usernameText;
    public PasswordField passwordText;
    public Button loginButton;
    public int pegawai;
    public AnchorPane mainPane;
    private PreparedStatement pst;
    private Main main;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Koneksi();

    }

    public static Connection Koneksi(){
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/kasir","root","");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void loginClicked(ActionEvent actionEvent) {
       loginCheck();
    }

    public void enterKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)){
            loginCheck();
        }
    }

    public void loginCheck(){
        String sql;
        try{
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            sql = "select * from pegawai where username_pegawai = '"
                    + usernameText.getText() + "' and password_pegawai = '"
                    + passwordText.getText() + "';";
            ResultSet res = stat.executeQuery(sql);
            if(res.next())
            {
                pegawai = res.getInt("id_pegawai");

                if(usernameText.getText().equals(res.getString("username_pegawai")) && passwordText.getText().equals(res.getString("password_pegawai"))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Berhasil");
                    alert.setHeaderText("Info");
                    alert.setContentText("Login Berhasil");

                    sql = "insert into transaksi value(null,"
                            + this.pegawai + ",now());";
                    pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
                    alert.showAndWait();
                    Stage thisState = (Stage) loginButton.getScene().getWindow();
                    thisState.close();
                    Stage primaryStage = new Stage();
                    FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource("Kasir.fxml"));
                    Parent root = fxmlLoader.load();

                    System.out.println(pegawai);

                    KasirController kasirController = fxmlLoader.getController();
                    kasirController.setPegawai(pegawai);
                    kasirController.setLabel();

                    primaryStage.setTitle("Kasir");
                    primaryStage.setScene(new Scene(root, 600, 400));
                    primaryStage.setMaximized(true);
                    primaryStage.show();
                }
            }  else
            {
                Alert gagal = new Alert(Alert.AlertType.INFORMATION);
                gagal.setTitle("Login Gagal");
                gagal.setHeaderText("Info");
                gagal.setContentText("Login Gagal");

                gagal.showAndWait();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Terjadi Permasalahan di "+e);

            alert.showAndWait();
        }
    }
}
