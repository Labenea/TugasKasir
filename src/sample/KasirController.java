package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class KasirController implements Initializable {
    public int Pegawai;
    public Label namaPegawai;
    private MainScreenController main;

    public void setPegawai(int pegawai) {
        Pegawai = pegawai;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       Koneksi();
    }
    public void setLabel(){
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select * from pegawai where id_pegawai = " + this.Pegawai + ";";
            ResultSet res = stat.executeQuery(sql);
            res.next();
            namaPegawai.setText(res.getString("nama_pegawai"));
        } catch (SQLException throwables) {
            Alert gagal = new Alert(Alert.AlertType.INFORMATION);
            gagal.setTitle("Login Gagal");
            gagal.setHeaderText("Info");
            gagal.setContentText(String.valueOf(throwables));

            gagal.showAndWait();
        }
    }


    public static Connection Koneksi(){
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/kasir","root","");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
