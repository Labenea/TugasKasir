package sample;

import Model.JenisCombo;
import Model.TypeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;
import  static Connection.Koneksi.Koneksi;

public class Tagihan implements Initializable {
    public ComboBox<JenisCombo> tagihanCombo;
    public TextField BiayaInput;
    public Button BayarButton;

    private int idPegawai;
    ObservableList<JenisCombo> JenisModelCombo = FXCollections.observableArrayList();


    public void setIdPegawai(int idPegawai) {
        this.idPegawai = idPegawai;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tagihanCombo.getSelectionModel().clearSelection();
        tagihanCombo.getItems().clear();
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select * from jenis";
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                JenisModelCombo.add(new JenisCombo(res.getInt("id_jenis"),res.getString("nama_jenis")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tagihanCombo.setItems(JenisModelCombo);
    }

    public void BayarButtonPressed(ActionEvent actionEvent) {
        JenisCombo selectedType = tagihanCombo.getSelectionModel().getSelectedItem();
        try {
            String sql = "insert into tagihan value(null, "
                    + this.idPegawai + ", "
                    + selectedType.getIdJenis() + ", "
                    + Integer.parseInt(BiayaInput.getText()) + ", now())";
            PreparedStatement pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
            pst.execute();
            Stage thisState = (Stage) BayarButton.getScene().getWindow();
            thisState.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
