package sample;

import Model.TableModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class KasirController implements Initializable {
    public int Pegawai;
    public Label namaPegawai;
    public Label hargaLabel;
    public int jumlahItem;
    public Label jumlahItemLabel;
    public String CashV ="0";
    public Integer KembalianV;
    public TextField cash;
    public Label kembalian;
    public int TableSelectedId;
    public Label Jabatan;

    public int getIdTransaki() {
        return idTransaki;
    }

    public Label noTransaksiLabel;
    public int idTransaki;
    public   int i = 0;

    public TableColumn<TableModel, Integer> colNo;
    public TableColumn<TableModel, String> colKode;
    public TableColumn<TableModel, String> colBarang;
    public TableColumn<TableModel, Integer> colHarga;
    public TableColumn<TableModel, Integer> colQty;
    public TableColumn<TableModel, String> colSatuan;
    public TableColumn<TableModel, Integer> colTotal;
    public TableView<TableModel> table;
    public Button tambahBarang;
    public Button editJumlah;
    public Button hapusBarang;
    ObservableList<TableModel> data = FXCollections.observableArrayList();
    private LoginScreen main;
    public int hargaTotal = 0;

    public int getHargaTotal() {
        return hargaTotal;
    }

    public void setHargaTotal(int hargaTotal) {
        this.hargaTotal = hargaTotal;
    }

    public static Connection Koneksi() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/kasir", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setPegawai(int pegawai) {
        Pegawai = pegawai;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Koneksi();
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        colKode.setCellValueFactory(new PropertyValueFactory<>("kode"));
        colBarang.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSatuan.setCellValueFactory(new PropertyValueFactory<>("satuan"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        table.setItems(data);
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        String val = nf.format(hargaTotal);
        hargaLabel.setText("Rp."+val);

    }

    public void setLabel() {
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select * from pegawai where id_pegawai = " + this.Pegawai + ";";
            ResultSet res = stat.executeQuery(sql);
            res.next();
            namaPegawai.setText(res.getString("nama_pegawai"));
            sql = "select * from transaksi order by id_transaksi desc limit 1 ";
            res = stat.executeQuery(sql);
            res.next();
            idTransaki = res.getInt("id_transaksi");
            noTransaksiLabel.setText(Integer.toString(idTransaki));

        } catch (SQLException throwables) {
            Alert gagal = new Alert(Alert.AlertType.INFORMATION);
            gagal.setTitle("Login Gagal");
            gagal.setHeaderText("Info");
            gagal.setContentText(String.valueOf(throwables));

            gagal.showAndWait();
        }
    }

    public void setTableItem() {


    }

    public void updateTable(){
        Koneksi();
        table.getItems().clear();
        table.refresh();
        jumlahItem = 0;
        hargaTotal = 0;
        i=0;
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select * from detail as d inner join barang as b on d.id_barang=b.id_barang inner join type as ty on b.id_type=ty.id_type " +
                    "where d.id_transaksi ="+ idTransaki+";";
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                int hargaJual = Integer.parseInt(res.getString("b.harga_jual"));
                int qty = Integer.parseInt(res.getString("d.jumlah"));
                int total = qty*hargaJual;
                i++;
                data.add(new TableModel(i, res.getString("d.id_barang"), res.getString("b.nama_barang"),
                        hargaJual, qty, res.getString("ty.nama_type"), (qty * hargaJual)));
                hargaTotal = hargaTotal+total;
                jumlahItem = jumlahItem+qty;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        table.setItems(data);
        jumlahItemLabel.setText(String.valueOf(jumlahItem));
        NumberFormat nf = NumberFormat.getInstance(new Locale("id", "ID"));
        String val = nf.format(hargaTotal);
        hargaLabel.setText("Rp. "+val);

        KembalianV = Integer.parseInt(CashV)-hargaTotal;
        NumberFormat nfv = NumberFormat.getInstance(new Locale("id", "ID"));
        String val2 = nf.format(KembalianV);
        kembalian.setText("Rp. "+val2);
    }

    public void tambahBarangAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("TambahBarang.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),600,400);
            Stage stage = new Stage();

            TambahBarang tambahBarang1 = fxmlLoader.getController();
            tambahBarang1.setIdTransaksi(idTransaki);

            stage.setTitle("Tambah Barang");
            stage.setScene(scene);
            stage.showAndWait();
            updateTable();
            cash.requestFocus();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editJumahAction(ActionEvent actionEvent) {
    }

    public void hapusBarangAction(ActionEvent actionEvent) {
        try{
            String sql = "delete from detail where id_barang = "
                    + TableSelectedId + " and id_transaksi = "
                    + idTransaki + ";";
            PreparedStatement pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
            pst.execute();
            updateTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void cashKeyPressed(KeyEvent keyEvent) {
        CashV = cash.getText();
        KembalianV = Integer.parseInt(CashV)-hargaTotal;
        NumberFormat nf = NumberFormat.getInstance(new Locale("id", "ID"));
        String val = nf.format(KembalianV);
        kembalian.setText("Rp. "+val);
    }

    public void selectTable(MouseEvent mouseEvent) {
        TableModel selecteditem = table.getSelectionModel().getSelectedItems().get(0);
        TableSelectedId = Integer.parseInt(selecteditem.getKode());
    }
}
