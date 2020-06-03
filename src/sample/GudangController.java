package sample;


import Model.RiwayatModel;
import Model.StockModel;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class GudangController implements Initializable {

    //region Variable
    public MaterialDesignIconView logoButtonR;
    public MaterialDesignIconView logoButtonS;
    public NumberFormat nf = NumberFormat.getInstance(new Locale("id", "ID"));
    public TableColumn<RiwayatModel,String> RStatusAction;

    public int getIdPegawai() {
        return IdPegawai;
    }

    public void setIdPegawai(int idPegawai) {
        IdPegawai = idPegawai;
    }
    public int IdGudang;
    public int StockSelected;
    public int IdItemSelected;
    public int IdPegawai;
    public Button StockButton;
    public Button RiwayatButton;
    public Label EmployeName;
    public GridPane RiwayatPane;
    public TableView<RiwayatModel> RiwayatTable;
    public TableColumn<RiwayatModel,Integer> RNoColumn;
    public TableColumn<RiwayatModel,String> RNamaPegawaiColumn;
    public TableColumn<RiwayatModel,Integer> RIdBarangColumn;
    public TableColumn<RiwayatModel,String> RNamaBarangColumn;
    public TableColumn<RiwayatModel,String> RTanggalColumn;
    public TableColumn<RiwayatModel,Integer> RBanyakColumn;
    public GridPane StockPane;
    public TableView<StockModel> StockTable;
    public TableColumn<StockModel,Integer>  NoColumn;
    public TableColumn<StockModel,Integer>  IdColumn;
    public TableColumn<StockModel,String>  NamaBarangColumn;
    public TableColumn<StockModel,String>  TypeColumn;
    public TableColumn<StockModel,Integer>  HargaBeliColumn;
    public TableColumn<StockModel,Integer>  HargaJualColumn;
    public TableColumn<StockModel,Integer>  StockColumn;
    public TextField CariInput;
    public Button CariButton;
    public Label IdBarangLabel;
    public Label NamaBarangLabel;
    public Label StockBarangLabel;
    public TextField JumlahStockInput;
    public Button TambahButton;
    public Button UpdateButton;
    public Label TotalStockLabel;
    public Label BarangKeluarLabel;
    public Label BarangMasukLabel;
    ObservableList<StockModel> Sdata = FXCollections.observableArrayList();
    ObservableList<RiwayatModel> Rdata = FXCollections.observableArrayList();
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NoColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("idBarang"));
        NamaBarangColumn.setCellValueFactory(new PropertyValueFactory<>("barang"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        HargaBeliColumn.setCellValueFactory(new PropertyValueFactory<>("hargaB"));
        HargaJualColumn.setCellValueFactory(new PropertyValueFactory<>("hargaJ"));
        StockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        StockTable.setItems(Sdata);

        RNoColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
        RNamaPegawaiColumn.setCellValueFactory(new PropertyValueFactory<>("namaPegawai"));
        RIdBarangColumn.setCellValueFactory(new PropertyValueFactory<>("idBarang"));
        RNamaBarangColumn.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        RTanggalColumn.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        RBanyakColumn.setCellValueFactory(new PropertyValueFactory<>("perubahan"));
        RStatusAction.setCellValueFactory(new PropertyValueFactory<>("statusAction"));
        StockTable.setItems(Sdata);
        StockPane.toFront();
        setBarangKeluar();
        setBarangMasuk();
        setTotalStock();
        UpdateStockTable();
    }

    public void StockButtonPressed(ActionEvent actionEvent) {
        StockPane.toFront();
        StockButton.getStyleClass().clear();
        StockButton.getStyleClass().add("GudangButtonColorSelected");
        logoButtonS.getStyleClass().clear();
        logoButtonS.setStyleClass("GudangButtonLogoSelected");
        RiwayatButton.getStyleClass().clear();
        RiwayatButton.getStyleClass().add("GudangButtonColor");
        logoButtonR.getStyleClass().clear();
        logoButtonR.setStyleClass("GudangButtonLogo");
    }

    public void RiwayatButtonPressed(ActionEvent actionEvent) {
        RiwayatPane.toFront();
        StockButton.getStyleClass().clear();
        StockButton.getStyleClass().add("GudangButtonColor");
        logoButtonS.getStyleClass().clear();
        logoButtonS.setStyleClass("GudangButtonLogo");
        RiwayatButton.getStyleClass().clear();
        RiwayatButton.getStyleClass().add("GudangButtonColorSelected");
        logoButtonR.getStyleClass().clear();
        logoButtonR.setStyleClass("GudangButtonLogoSelected");
        UpdateRiwayatTable();
    }

    public void CariButtonPressed(ActionEvent actionEvent) {
    }

    public void TambahButtonPressed(ActionEvent actionEvent) {
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select * from gudang where id_barang = "
                    + IdItemSelected + ";";
            ResultSet res = stat.executeQuery(sql);
            res.next();
            IdGudang = res.getInt("id_gudang");
            StockSelected += Integer.parseInt(JumlahStockInput.getText());
            sql = "update gudang set stock = "
                    + StockSelected + " where id_barang = "
                    + IdItemSelected + ";";
            PreparedStatement pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
            pst.execute();
            StockBarangLabel.setText(String.valueOf(StockSelected));
            sql = "insert into riwayat_gudang value(null,"
                    + IdPegawai + ",now(),"
                    + IdGudang + ","
                    + Integer.parseInt(JumlahStockInput.getText())+ ", 'Tambah')";
            pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
            pst.execute();
            setBarangKeluar();
            setBarangMasuk();
            setTotalStock();
            UpdateStockTable();
            JumlahStockInput.setText("");
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void UpdateButtonPressed(ActionEvent actionEvent) {

    }

    public static Connection Koneksi() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/kasir", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void UpdateStockTable(){
        Koneksi();
        StockTable.getItems().clear();
        StockTable.refresh();
        int i=0;
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql =  "select * " +
                    "from barang as b " +
                    "inner join type as t on b.id_type=t.id_type " +
                    "inner join gudang as g on b.id_barang=g.id_barang order by g.id_barang asc;";
            ResultSet res = stat.executeQuery(sql);
            while (res.next()){
                i++;
                Sdata.add(new StockModel(i,res.getInt("id_barang"),res.getString("nama_barang"),
                        res.getString("nama_type"),nf.format(res.getInt("harga_beli")),nf.format(res.getInt("harga_jual")),res.getInt("stock")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
            StockTable.setItems(Sdata);
    }

    public void UpdateRiwayatTable(){
        Koneksi();
        RiwayatTable.getItems().clear();
        RiwayatTable.refresh();
        int i=0;
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql =  "select * from riwayat_gudang as rg \n" +
                    "inner join gudang as g on rg.id_gudang=g.id_gudang\n" +
                    "INNER JOIN barang as b on g.id_barang = b.id_barang\n" +
                    "INNER JOIN pegawai as p on rg.id_pegawai = p.id_pegawai\n" +
                    "ORDER BY rg.tgl_masuk_barang";
            ResultSet res = stat.executeQuery(sql);
            while (res.next()){
                i++;
                Rdata.add(new RiwayatModel(i,res.getString("nama_pegawai"),res.getInt("id_barang"),res.getString("nama_barang"),
                        res.getString("tgl_masuk_barang"), res.getInt("penambahan"), res.getString("status_action")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        RiwayatTable.setItems(Rdata);
    }

    public void setTotalStock(){
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql =  "select SUM(stock) as 'total_stock'" +
                    "from barang as b " +
                    "inner join type as t on b.id_type=t.id_type " +
                    "inner join gudang as g on b.id_barang=g.id_barang;";
            ResultSet res = stat.executeQuery(sql);
            if(res.next()){
                TotalStockLabel.setText(String.valueOf(res.getInt("total_stock")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setBarangMasuk(){
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "SELECT SUM(penambahan) as 'total_masuk' FROM `riwayat_gudang` WHERE tgl_masuk_barang = CURRENT_DATE and penambahan >= 0 AND status_action <> 'Update Harga' ;";
            ResultSet res = stat.executeQuery(sql);
            if (res.next()){
                BarangMasukLabel.setText(String.valueOf(res.getInt("total_masuk")));
            }else
            {
                BarangMasukLabel.setText("0");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void  setBarangKeluar(){
        BarangKeluarLabel.setText("0");
    }

    public void TableStockSelected(MouseEvent mouseEvent) {
        StockModel selectedStock = StockTable.getSelectionModel().getSelectedItems().get(0);
        IdItemSelected = Integer.parseInt(String.valueOf(selectedStock.getIdBarang()));
        StockSelected = Integer.parseInt(String.valueOf(selectedStock.getStock()));
        IdBarangLabel.setText(String.valueOf(IdItemSelected));
        NamaBarangLabel.setText(selectedStock.getBarang());
        StockBarangLabel.setText(String.valueOf(StockSelected));
    }

    public void CariOnKeyReleased(KeyEvent keyEvent) {
        Koneksi();
        StockTable.getItems().clear();
        StockTable.refresh();
        int i=0;
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql =  "select * " +
                    "from barang as b " +
                    "inner join type as t on b.id_type=t.id_type " +
                    "inner join gudang as g on b.id_barang=g.id_barang " +
                    "where b.nama_barang like '%"
                    + CariInput.getText() + "%'" +
                    "order by g.id_barang asc;";
            ResultSet res = stat.executeQuery(sql);
            while (res.next()){
                i++;
                Sdata.add(new StockModel(i,res.getInt("id_barang"),res.getString("nama_barang"),
                        res.getString("nama_type"),nf.format(res.getInt("harga_beli")),nf.format(res.getInt("harga_jual")),res.getInt("stock")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        StockTable.setItems(Sdata);
    }

    public void setPegawaiLabel(){
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select * from pegawai where id_pegawai = " + IdPegawai + ";";
            ResultSet res = stat.executeQuery(sql);
            res.next();
            EmployeName.setText(res.getString("nama_pegawai"));
        } catch (SQLException throwables) {
            Alert gagal = new Alert(Alert.AlertType.INFORMATION);
            gagal.setContentText(String.valueOf(throwables));
            gagal.showAndWait();
        }
    }
}
