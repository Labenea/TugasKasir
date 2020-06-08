package sample;

import Hashing.Md5Hasing;
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
import javafx.stage.StageStyle;
import sun.security.provider.MD5;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

import static Connection.Koneksi.Koneksi;

public class LoginScreen implements Initializable  {
    public TextField usernameText;
    public PasswordField passwordText;
    public Button loginButton;
    public int pegawai,jabatan;
    public AnchorPane mainPane;
    private PreparedStatement pst;
    private Main main;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Koneksi();

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
        Md5Hasing md5Hasing = new Md5Hasing();
        String pass = md5Hasing.generatedPassword(passwordText.getText());
        try{
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            sql = "select * from pegawai where username_pegawai = '"
                    + usernameText.getText() + "' and password_pegawai = '"
                    + pass + "' and status_pegawai = 1;";
            ResultSet res = stat.executeQuery(sql);
            if(res.next())
            {
                pegawai = res.getInt("id_pegawai");
                jabatan = res.getInt("id_jabatan");

                if(usernameText.getText().equals(res.getString("username_pegawai")) && pass.equals(res.getString("password_pegawai")))
                {
                    if(jabatan == 1){
                        sql = "select COUNT(*) FROM transaksi";
                        res = stat.executeQuery(sql);
                        res.next();
                        if(res.getInt("COUNT(*)") == 0){
                            sql = "insert into transaksi value(null,"
                                    + pegawai + ",now(),0);";
                            pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
                            pst.execute();
                        }else{
                            sql = "select * from transaksi order by id_transaksi desc limit 1 ";
                            res = stat.executeQuery(sql);
                            res.next();
                            if(res.getInt("status_transaksi") == 1 ){
                                sql = "insert into transaksi value(null,"
                                        + pegawai + ",now(),0);";
                                pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
                                pst.execute();
                            }
                        }
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
                        primaryStage.setResizable(false);
                        primaryStage.show();
                    }else if(jabatan == 2){
                        Stage thisState = (Stage) loginButton.getScene().getWindow();
                        thisState.close();
                        Stage primaryStage = new Stage();
                        FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource("Gudang.fxml"));
                        Parent root = fxmlLoader.load();

                        System.out.println(pegawai);

                        GudangController gudangController = fxmlLoader.getController();
                        gudangController.setIdPegawai(pegawai);
                        gudangController.setPegawaiLabel();

                        primaryStage.setTitle("Gudang");
                        primaryStage.setScene(new Scene(root, 600, 400));
                        primaryStage.setMaximized(true);
                        primaryStage.setResizable(false);
                        primaryStage.show();
                    }else if(jabatan == 3){
                        Stage thisState = (Stage) loginButton.getScene().getWindow();
                        thisState.close();
                        Stage primaryStage = new Stage();
                        FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource("Manager.fxml"));
                        Parent root = fxmlLoader.load();

                        System.out.println(pegawai);

                        ManagerController managerController = fxmlLoader.getController();
                        managerController.setPegawai(pegawai);
                        managerController.setManagerName();

                        primaryStage.setTitle("Manager");
                        primaryStage.setScene(new Scene(root, 600, 400));
                        primaryStage.setMaximized(true);
                        primaryStage.setResizable(false);
                        primaryStage.show();
                    }

                }
            }else
            {
                Alert gagal = new Alert(Alert.AlertType.INFORMATION);
                gagal.setTitle("Login Gagal");
                gagal.setHeaderText("Info");
                gagal.setContentText("Login Gagal");

                gagal.showAndWait();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
