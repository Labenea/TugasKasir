package sample;

import Model.TambahModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

import static Connection.Koneksi.Koneksi;

public class TambahBarang implements Initializable {
    public TableColumn<TambahModel, Integer> colNo;
    public TableColumn<TambahModel, String> colKode;
    public TableColumn<TambahModel, String> colNama;
    public TableColumn<TambahModel, String> colSatuan;
    public TableColumn<TambahModel, Integer> colHarga;
    public TextField searchBarang;
    public Label namaBarang;
    public TextField jumlahInput;
    public int HargaBarang;
    public Button tambahButton;
    public TableView<TambahModel> tableTambah;
    public int idBarang;
    public int IdTransaksi;

    ObservableList<TambahModel> data = FXCollections.observableArrayList();


    public void setIdTransaksi(int idTransaksi) {
        IdTransaksi = idTransaksi;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void searchOnKeyPressed(KeyEvent keyEvent) {
        tableTambah.getItems().clear();
        tableTambah.refresh();
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select * " +
                    "from barang as b " +
                    "inner join type as t on b.id_type=t.id_type " +
                    "inner join gudang as g on b.id_barang=g.id_barang "+
                    "WHERE b.nama_barang LIKE '%"
                    + searchBarang.getText() + "%';";
            ResultSet res = stat.executeQuery(sql);
            int i = 0;
            while (res.next()) {
                i++;
                data.add(new TambahModel(i, res.getString("b.id_barang"), res.getString("barang.nama_barang"),
                        res.getString("type.nama_type"), res.getInt("barang.harga_jual")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        colNo.setCellValueFactory(new PropertyValueFactory<>("No"));
        colKode.setCellValueFactory(new PropertyValueFactory<>("Kode"));
        colHarga.setCellValueFactory(new PropertyValueFactory<>("Harga"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("Nama"));
        colSatuan.setCellValueFactory(new PropertyValueFactory<>("Satuan"));

        tableTambah.setItems(data);
    }


    public void jumlahInputOnKeyPressed(KeyEvent keyEvent) {
    }

    public void tambahButtonAction(ActionEvent actionEvent) {
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select * from detail where id_barang = "
                    + idBarang + " and id_transaksi = "
                    + IdTransaksi + ";";
            ResultSet res = stat.executeQuery(sql);
            PreparedStatement pst;
            if (res.next()) {
                int jumlah = res.getInt("jumlah");
                jumlah = jumlah + Integer.parseInt(jumlahInput.getText());
                sql = "update detail set jumlah = "
                        + jumlah + " where id_barang = "
                        + idBarang + " and id_transaksi = "
                        + IdTransaksi + ";";
                pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
                pst.execute();
            } else {
                sql = "insert into detail value(null,"
                        + IdTransaksi + ","
                        + idBarang + ","
                        + Integer.parseInt(jumlahInput.getText()) +","+
                        HargaBarang +")";
                pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
                pst.execute();
            }
            Stage thisState = (Stage) tambahButton.getScene().getWindow();
            thisState.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void selectedTable(MouseEvent mouseEvent) {
        TambahModel selecteditem = tableTambah.getSelectionModel().getSelectedItems().get(0);
        idBarang = Integer.parseInt(selecteditem.getKode());
        jumlahInput.setText("1");
        namaBarang.setText(selecteditem.getNama());
        HargaBarang = selecteditem.getHarga();
        System.out.println(idBarang);

        jumlahInput.requestFocus();

    }
}
