package sample;

import Model.RiwayatKasirModel;
import Model.TableModel;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static Connection.Koneksi.Koneksi;

public class KasirController implements Initializable {
    //region Variable
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
    public Label namaBarang;
    public Button KasirButton;
    public MaterialDesignIconView KasirBLogo;
    public Button RiwayatKasirButton;
    public MaterialDesignIconView RiwayatKasirBLogo;
    public Button CheckOutButton;
    public Button KLogoutButton;
    public TableView<RiwayatKasirModel> RiwayatTab;
    public TableColumn<RiwayatKasirModel,Integer>  noRiwayatTab;
    public TableColumn<RiwayatKasirModel,Integer>  IDRiwayatTab;
    public TableColumn<RiwayatKasirModel,String>  NamaRiwayatTab;
    public TableColumn<RiwayatKasirModel,String>  JabatanRiwayatTab;
    public TableColumn<RiwayatKasirModel,String>  TglRiwayatTab;
    public TableColumn<RiwayatKasirModel,String>  PendapatanRiwayatTab;
    public GridPane RiwayatKas;
    public GridPane MainKas;

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
    //endregion

    ObservableList<TableModel> data = FXCollections.observableArrayList();
    ObservableList<RiwayatKasirModel> datark = FXCollections.observableArrayList();
    private LoginScreen main;
    public int hargaTotal = 0;

    public int getHargaTotal() {
        return hargaTotal;
    }

    public void setHargaTotal(int hargaTotal) {
        this.hargaTotal = hargaTotal;
    }


    public void setPegawai(int pegawai) {
        Pegawai = pegawai;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Koneksi();
        //table kasir
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        colKode.setCellValueFactory(new PropertyValueFactory<>("kode"));
        colBarang.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSatuan.setCellValueFactory(new PropertyValueFactory<>("satuan"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        table.setItems(data);
        //table riwayat transaksi
        noRiwayatTab.setCellValueFactory(new PropertyValueFactory<>("no"));
        IDRiwayatTab.setCellValueFactory(new PropertyValueFactory<>("idrk"));
        NamaRiwayatTab.setCellValueFactory(new PropertyValueFactory<>("namarpegawai"));
        JabatanRiwayatTab.setCellValueFactory(new PropertyValueFactory<>("jabatanr"));
        TglRiwayatTab.setCellValueFactory(new PropertyValueFactory<>("tglr"));
        PendapatanRiwayatTab.setCellValueFactory(new PropertyValueFactory<>("pdptn"));

        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        String val = nf.format(hargaTotal);
        hargaLabel.setText("Rp."+val);
        MainKas.toFront();
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
            updateTable();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    public void updateRiwayatTable(){
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        RiwayatTab.getItems().clear();
        RiwayatTab.refresh();
        i = 0;
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select *, sum(d.jumlah*d.harga) as total " +
                    "from detail as d " +
                    "inner join transaksi as t on d.id_transaksi=t.id_transaksi " +
                    "inner join pegawai as p on t.id_pegawai=p.id_pegawai " +
                    "inner join jabatan as j on p.id_jabatan=j.id_jabatan " +
                    "group by t.id_transaksi;";
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                datark.add(new RiwayatKasirModel(i,res.getInt("d.id_transaksi"),
                        res.getString("p.nama_pegawai"),res.getString("j.nama_jabatan"),
                        res.getString("tgl_transaksi"),nf.format(res.getInt("total"))));
            }
            RiwayatTab.setItems(datark);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
            int tab = table.getItems().size();
            TableModel vi = table.getItems().get(tab-1);
            namaBarang.setText(vi.getNamaBarang());
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
            sql = "insert into transaksi value(null,"
                    + Pegawai + ",now());";
            pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
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
        namaBarang.setText(selecteditem.getNamaBarang());
    }

    public void KasirButtonPressed(ActionEvent actionEvent) {
        MainKas.toFront();
        KasirButton.getStyleClass().clear();
        KasirButton.getStyleClass().add("GudangButtonColorSelected");
        KasirBLogo.getStyleClass().clear();
        KasirBLogo.setStyleClass("GudangButtonLogoSelected");

        //RiwayatButton
        RiwayatKasirButton.getStyleClass().clear();
        RiwayatKasirButton.getStyleClass().add("GudangButtonColor");
        RiwayatKasirBLogo.getStyleClass().clear();
        RiwayatKasirBLogo.setStyleClass("GudangButtonLogo");
    }

    public void RiwayatButtonPressed(ActionEvent actionEvent) {
        RiwayatKas.toFront();
        updateRiwayatTable();
        KasirButton.getStyleClass().clear();
        KasirButton.getStyleClass().add("GudangButtonColor");
        KasirBLogo.getStyleClass().clear();
        KasirBLogo.setStyleClass("GudangButtonLogo");

        //ManagerButton
        RiwayatKasirButton.getStyleClass().clear();
        RiwayatKasirButton.getStyleClass().add("GudangButtonColorSelected");
        RiwayatKasirBLogo.getStyleClass().clear();
        RiwayatKasirBLogo.setStyleClass("GudangButtonLogoSelected");
    }

    public void CheckOutAction(ActionEvent actionEvent) {
        if(Integer.parseInt(cash.getText()) < hargaTotal){
            Alert gagal = new Alert(Alert.AlertType.INFORMATION);
            gagal.setTitle("Transaksi Gagal");
            gagal.setHeaderText(null);
            gagal.setContentText("Uang yang Dibayarkan Kurang");
            gagal.showAndWait();
            updateTable();
        }else {
            try{
                String sql = "UPDATE gudang as g, detail as d " +
                        "SET g.stock = g.stock - d.jumlah " +
                        "WHERE g.id_barang = d.id_barang " +
                        "AND d.id_transaksi =" + idTransaki+";";
                PreparedStatement pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
                pst.execute();
                try
                {
                    sql = "UPDATE transaksi SET status_transaksi = 1 WHERE id_transaksi =" + idTransaki + ";";
                    pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
                    pst.execute();
                }catch (SQLException throwables){
                    throwables.printStackTrace();
                }
                sql = "insert into transaksi value(null,"
                        + Pegawai + ",now(),0);";
                pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
                pst.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Alert sukses = new Alert(Alert.AlertType.INFORMATION);
            sukses.setTitle("Transaksi Berhasil");
            sukses.setHeaderText(null);
            sukses.setContentText("Transaksi Berhasil Dilakukan");
            idTransaki += 1;
            cash.setText("");
            sukses.showAndWait();
            updateTable();
        }

    }

    public void KLogoutButtonPressed(ActionEvent actionEvent) {
        try {
            Stage thisState = (Stage) KLogoutButton.getScene().getWindow();
            thisState.close();
            Stage primaryStage = new Stage();
            FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            Parent root = fxmlLoader.load();

            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setResizable(false);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
