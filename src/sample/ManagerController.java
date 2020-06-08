package sample;

import Hashing.Md5Hasing;
import Model.*;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static Connection.Koneksi.Koneksi;

public class ManagerController implements Initializable {


    //region Variable
    public NumberFormat nf = NumberFormat.getInstance(new Locale("id", "ID"));
    public int totalPemasukan;
    public Label ManagerName;
    public TableView<PemasukanModel> PemasukanTabel;
    public TableColumn<PemasukanModel, Integer> NoPemasukan;
    public TableColumn<PemasukanModel, String> TanggalPemasukan;
    public TableColumn<PemasukanModel, Integer> PemasukanColumn;
    public TableView<PengeluaranModel> PengeluaranTabel;
    public TableColumn<PengeluaranModel, String> TanggalPengeluaran;
    public TableColumn<PengeluaranModel,String> JenisPengeluaran;
    public TableColumn<PengeluaranModel, String> PengeluaranColumn;
    public Button ManagerButton;
    public MaterialDesignIconView ManagerBLogo;
    public Button MStockButton;
    public MaterialDesignIconView MStockBLogo;
    public Button PegawaiButton;
    public MaterialDesignIconView PegawaiBLogo;
    public GridPane PegawaiPane;
    public TableView<PegawaiModel> PegawaiTable;
    public TableColumn<PegawaiModel,Integer> PNoColumn;
    public TableColumn<PegawaiModel, Integer> PIdPegawaiColumn;
    public TableColumn<PegawaiModel, String> PNamaColumn;
    public TableColumn<PegawaiModel, String> PJabatanColumn;
    public TableColumn<PegawaiModel, String> PUnameColumn;
    public TextField PCariInput;
    public Button PCariButton;
    public Label NamaPegawaiLabel;
    public Label UsernamePegawaiLabel;
    public Button HapusAkunButton;
    public TextField NamaPegawaiInput;
    public TextField UsernameInput;
    public PasswordField PasswordInput;
    public ComboBox<JabatanModel> RJabatanCombo;
    public Button RegistrasiButton;
    public GridPane MRiwayatGudangPane;
    public TableView<RiwayatModel> MRiwayatTable;
    public TableColumn<RiwayatModel,Integer> MRNoColumn;
    public TableColumn<RiwayatModel,String> MRNamaPegawaiColumn;
    public TableColumn<RiwayatModel,Integer> MRIdBarangColumn;
    public TableColumn<RiwayatModel,String> MRNamaBarangColumn;
    public TableColumn<RiwayatModel,String> MRTanggalColumn;
    public TableColumn<RiwayatModel, Integer> MRBanyakColumn;
    public TableColumn<RiwayatModel,String> MRStatusAction;
    public GridPane MGudangPane;
    public TableView<StockModel> ManagerStockTable;
    public TableColumn<StockModel,Integer> GudangNoColumn;
    public TableColumn<StockModel,Integer> GudangIdColumn;
    public TableColumn<StockModel,String> GudangNamaBarangColumn;
    public TableColumn<StockModel,String> GudangTypeColumn;
    public TableColumn<StockModel,Integer> GudangHargaBeliColumn;
    public TableColumn<StockModel,Integer> GudangHargaJualColumn;
    public TableColumn<StockModel,Integer> GudangStockColumn;
    public TextField ManagerCariInput;
    public Button ManagerCariButton;
    public Label MIdBarangLabel;
    public Label MNamaBarangLabel;
    public Label MStockBarangLabel;
    public Label MHargaJualLabel;
    public TextField UpdateHargaInput;
    public Button UpdateHargaButton;
    public TextField MUpdateStockInput;
    public Button MTambahStockButton;
    public Button MUpdateStockButton;
    public TextField NamaBarangInput;
    public ComboBox<TypeModel> SatuanCombo;
    public TextField HargaBeliInput;
    public TextField BarangBaruStock;
    public TextField HargaJualInput;
    public Button RiwayatGudangButton;
    public Button BuatBarangButton;
    public Label JumlahTrasaksiLabel;
    public Label TransaksiHarIniLabel;
    public Label PemasukanHariIniLabel;
    public Label TotalPemasukanLabel;
    public Label TotalPengeluaranLabel;
    public Label LabaLabel;
    public GridPane ManagerPane;
    public int idStockSelected;
    public int idPegawaiSelected;
    public int JumlahStockSelected;
    public int pengeluaran;
    public Button MLogoutButton;

    //endregion

    ObservableList<PemasukanModel> PemasukanData = FXCollections.observableArrayList();
    ObservableList<PengeluaranModel> PengeluaranData = FXCollections.observableArrayList();
    ObservableList<RiwayatModel> RiwayatData = FXCollections.observableArrayList();
    ObservableList<TypeModel> TypeModelCombo = FXCollections.observableArrayList();
    ObservableList<JabatanModel> JabatanData = FXCollections.observableArrayList();
    ObservableList<StockModel> StockData = FXCollections.observableArrayList();
    ObservableList<PegawaiModel> PegawaiData = FXCollections.observableArrayList();
    private int pegawai;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //PemasukanTabel
        NoPemasukan.setCellValueFactory(new PropertyValueFactory<>("no"));
        TanggalPemasukan.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        PemasukanColumn.setCellValueFactory(new PropertyValueFactory<>("pemasukan"));
        //PengeluaranTabel
        JenisPengeluaran.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        TanggalPengeluaran.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        PengeluaranColumn.setCellValueFactory(new PropertyValueFactory<>("pengeluaran"));
        //StockTable
        GudangNoColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
        GudangIdColumn.setCellValueFactory(new PropertyValueFactory<>("idBarang"));
        GudangNamaBarangColumn.setCellValueFactory(new PropertyValueFactory<>("barang"));
        GudangTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        GudangHargaBeliColumn.setCellValueFactory(new PropertyValueFactory<>("hargaB"));
        GudangHargaJualColumn.setCellValueFactory(new PropertyValueFactory<>("hargaJ"));
        GudangStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        //RiwayatTabel
        MRNoColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
        MRNamaPegawaiColumn.setCellValueFactory(new PropertyValueFactory<>("namaPegawai"));
        MRIdBarangColumn.setCellValueFactory(new PropertyValueFactory<>("idBarang"));
        MRNamaBarangColumn.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        MRTanggalColumn.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        MRBanyakColumn.setCellValueFactory(new PropertyValueFactory<>("perubahan"));
        MRStatusAction.setCellValueFactory(new PropertyValueFactory<>("statusAction"));
        //PegawaiTabel
        PNoColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
        PIdPegawaiColumn.setCellValueFactory(new PropertyValueFactory<>("idPegawai"));
        PNamaColumn.setCellValueFactory(new PropertyValueFactory<>("namaPegawai"));
        PJabatanColumn.setCellValueFactory(new PropertyValueFactory<>("jabatanPegawai"));
        PUnameColumn.setCellValueFactory(new PropertyValueFactory<>("usernamePegawai"));
        ManagerPane.toFront();
        updatePengeluaranTable();
        updatePemasukanTable();
        updateManagerPane();
    }


    private void updatePemasukanTable() {
        PemasukanTabel.getItems().clear();
        PemasukanTabel.refresh();
        totalPemasukan = 0;
        int i = 0;
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select t.tgl_transaksi, sum(d.jumlah*d.harga) as pemasukan from detail as d inner join transaksi as t on d.id_transaksi=t.id_transaksi group by t.tgl_transaksi";
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                i++;
                totalPemasukan = totalPemasukan + res.getInt("pemasukan");
                PemasukanData.add(new PemasukanModel(i, res.getString("tgl_transaksi"), nf.format(res.getInt("pemasukan"))));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        PemasukanTabel.setItems(PemasukanData);
    }

    private void updatePengeluaranTable() {
        Koneksi();
        PengeluaranTabel.getItems().clear();
        PengeluaranTabel.refresh();
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select sum(b.harga_beli*rg.penambahan) as jumlah, rg.tgl_masuk_barang " +
                    "from barang as b " +
                    "inner join gudang as g on b.id_barang=g.id_barang " +
                    "inner join riwayat_gudang as rg on rg.id_gudang=g.id_gudang " +
                    "group by rg.tgl_masuk_barang;";
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                PengeluaranData.add(new PengeluaranModel(res.getString("tgl_masuk_barang"),"Pengadaan", nf.format(res.getInt("jumlah"))));
            }
            stat = Objects.requireNonNull(Koneksi()).createStatement();
            sql = "select sum(biaya) as jumlah, tanggal_tagihan from tagihan group by tanggal_tagihan";
            res = stat.executeQuery(sql);
            while (res.next()) {
                PengeluaranData.add(new PengeluaranModel(res.getString("tanggal_tagihan"),"Tagihan", nf.format(res.getInt("jumlah"))));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        PengeluaranTabel.setItems(PengeluaranData);
        TanggalPengeluaran.setSortType(TableColumn.SortType.DESCENDING);
        PengeluaranTabel.getSortOrder().add(TanggalPengeluaran);
        PengeluaranTabel.sort();
    }

    private void updateStockTabel(){
        Koneksi();
        ManagerStockTable.getItems().clear();
        ManagerStockTable.refresh();
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
                StockData.add(new StockModel(i,res.getInt("id_barang"),res.getString("nama_barang"),
                        res.getString("nama_type"),nf.format(res.getInt("harga_beli")),nf.format(res.getInt("harga_jual")),res.getInt("stock")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ManagerStockTable.setItems(StockData);
    }

    private void updateRiwayatTabel(){
        Koneksi();
        MRiwayatTable.getItems().clear();
        MRiwayatTable.refresh();
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
                RiwayatData.add(new RiwayatModel(i,res.getString("nama_pegawai"),res.getInt("id_barang"),res.getString("nama_barang"),
                        res.getString("tgl_masuk_barang"), res.getInt("penambahan"), res.getString("status_action")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        MRiwayatTable.setItems(RiwayatData);
    }

    private void updatePegawaiTabel(){
        Koneksi();
        PegawaiTable.getItems().clear();
        PegawaiTable.refresh();
        int i=0;
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql =  "select * from pegawai as p inner join jabatan as j on p.id_jabatan=j.id_jabatan where status_pegawai = 1;";
            ResultSet res = stat.executeQuery(sql);
            while (res.next()){
                i++;
                PegawaiData.add(new PegawaiModel(i,res.getInt("id_pegawai"),res.getString("nama_pegawai"),
                        res.getString("nama_jabatan"),res.getString("username_pegawai")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        PegawaiTable.setItems(PegawaiData);

    }

    public void setPegawai(int pegawai) {
        this.pegawai = pegawai;
    }

    public void setManagerName() {
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select * from pegawai where id_pegawai = " + this.pegawai + ";";
            ResultSet res = stat.executeQuery(sql);
            res.next();
            ManagerName.setText(res.getString("nama_pegawai"));
        } catch (SQLException throwables) {
            Alert gagal = new Alert(Alert.AlertType.INFORMATION);
            gagal.setContentText(String.valueOf(throwables));
            gagal.showAndWait();
        }
    }

    public void ManagerButtonPressed(ActionEvent actionEvent) {
        ManagerPane.toFront();
        updatePengeluaranTable();
        updatePemasukanTable();
        updateManagerPane();
        //StockButton
        MStockButton.getStyleClass().clear();
        MStockButton.getStyleClass().add("GudangButtonColor");
        MStockBLogo.getStyleClass().clear();
        MStockBLogo.setStyleClass("GudangButtonLogo");

        //ManagerButton
        ManagerButton.getStyleClass().clear();
        ManagerButton.getStyleClass().add("GudangButtonColorSelected");
        ManagerBLogo.getStyleClass().clear();
        ManagerBLogo.setStyleClass("GudangButtonLogoSelected");

        //PegawaiButton
        PegawaiButton.getStyleClass().clear();
        PegawaiButton.getStyleClass().add("GudangButtonColor");
        PegawaiBLogo.getStyleClass().clear();
        PegawaiBLogo.setStyleClass("GudangButtonLogo");


    }

    public void MStockButtonPressed(ActionEvent actionEvent) {

        MGudangPane.toFront();
        updateStockTabel();
        updateSatuanCombo();
        NamaBarangInput.setText("");
        HargaBeliInput.setText("0");
        HargaJualInput.setText("0");
        BarangBaruStock.setText("0");
        //StockButton
        MStockButton.getStyleClass().clear();
        MStockButton.getStyleClass().add("GudangButtonColorSelected");
        MStockBLogo.getStyleClass().clear();
        MStockBLogo.setStyleClass("GudangButtonLogoSelected");

        //ManagerButton
        ManagerButton.getStyleClass().clear();
        ManagerButton.getStyleClass().add("GudangButtonColor");
        ManagerBLogo.getStyleClass().clear();
        ManagerBLogo.setStyleClass("GudangButtonLogo");

        //PegawaiButton
        PegawaiButton.getStyleClass().clear();
        PegawaiButton.getStyleClass().add("GudangButtonColor");
        PegawaiBLogo.getStyleClass().clear();
        PegawaiBLogo.setStyleClass("GudangButtonLogo");


    }

    private void updateSatuanCombo() {
        SatuanCombo.getSelectionModel().clearSelection();
        SatuanCombo.getItems().clear();
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select * from type";
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                TypeModelCombo.add(new TypeModel(res.getInt("id_type"),res.getString("nama_type")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        SatuanCombo.setItems(TypeModelCombo);
    }

    private void updateJabatanCombo(){
        RJabatanCombo.getSelectionModel().clearSelection();
        RJabatanCombo.getItems().clear();
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select * from jabatan";
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                JabatanData.add(new JabatanModel(res.getInt("id_jabatan"),res.getString("nama_jabatan")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        RJabatanCombo.setItems(JabatanData);

    }

    public void PegawaiButtonPressed(ActionEvent actionEvent) {
        updateJabatanCombo();
        updatePegawaiTabel();
        PegawaiPane.toFront();
        //StockButton
        MStockButton.getStyleClass().clear();
        MStockButton.getStyleClass().add("GudangButtonColor");
        MStockBLogo.getStyleClass().clear();
        MStockBLogo.setStyleClass("GudangButtonLogo");

        //ManagerButton
        ManagerButton.getStyleClass().clear();
        ManagerButton.getStyleClass().add("GudangButtonColor");
        ManagerBLogo.getStyleClass().clear();
        ManagerBLogo.setStyleClass("GudangButtonLogo");

        //PegawaiButton
        PegawaiButton.getStyleClass().clear();
        PegawaiButton.getStyleClass().add("GudangButtonColorSelected");
        PegawaiBLogo.getStyleClass().clear();
        PegawaiBLogo.setStyleClass("GudangButtonLogoSelected");

    }

    public void PegawaiTableSelected(MouseEvent mouseEvent) {
        PegawaiModel SelectedPegawai = PegawaiTable.getSelectionModel().getSelectedItems().get(0);
        idPegawaiSelected = SelectedPegawai.getIdPegawai();
        NamaPegawaiLabel.setText(SelectedPegawai.getNamaPegawai());
        UsernamePegawaiLabel.setText(SelectedPegawai.getUsernamePegawai());
    }

    public void PCariOnKeyReleased(KeyEvent keyEvent) {
        Koneksi();
        PegawaiTable.getItems().clear();
        PegawaiTable.refresh();
        int i=0;
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql =  "select * from pegawai as p inner join jabatan as j on p.id_jabatan=j.id_jabatan " +
                    "where status_pegawai = 1 and p.nama_pegawai like '%" +
                     PCariInput.getText() +"%';";
            ResultSet res = stat.executeQuery(sql);
            while (res.next()){
                i++;
                PegawaiData.add(new PegawaiModel(i,res.getInt("id_pegawai"),res.getString("nama_pegawai"),
                        res.getString("nama_jabatan"),res.getString("username_pegawai")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        PegawaiTable.setItems(PegawaiData);

    }

    public void PCariButtonPressed(ActionEvent actionEvent) {
    }

    public void HapusAkunButtonPressed(ActionEvent actionEvent) {
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "update pegawai set status_pegawai = 0 where id_pegawai = "
                    + idPegawaiSelected + ";";
            PreparedStatement pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
            pst.execute();
            Alert Sukses = new Alert(Alert.AlertType.INFORMATION);
            Sukses.setTitle("Berhasil!!!");
            Sukses.setHeaderText(null);
            Sukses.setContentText("Akun Pegawai Berhasil Dihapus");

            Sukses.showAndWait();
            updatePegawaiTabel();

        } catch (SQLException throwables) {
            Alert Sukses = new Alert(Alert.AlertType.INFORMATION);
            Sukses.setTitle("Gagal");
            Sukses.setHeaderText(null);
            Sukses.setContentText("Akun Pegawai Gagal Dihapus");

            Sukses.showAndWait();
        }
    }

    public void RJabatanSelected(ActionEvent actionEvent) {
    }

    public void RegistrasiButtonPressed(ActionEvent actionEvent) {
        JabatanModel jabatanSelected = RJabatanCombo.getSelectionModel().getSelectedItem();
        Md5Hasing md5 = new Md5Hasing();
        String pass = md5.generatedPassword(PasswordInput.getText());
        if(NamaPegawaiInput.getText().equals("") || PasswordInput.getText().equals("") || UsernameInput.getText().equals("") || jabatanSelected == null){
            Alert gagal = new Alert(Alert.AlertType.INFORMATION);
            gagal.setTitle("Gagal!!!");
            gagal.setHeaderText(null);
            gagal.setContentText("Semua Field Harus Diisi" );

            gagal.showAndWait();
        }else {
            try {
                String sql = "insert into pegawai value(null, '"
                        + NamaPegawaiInput.getText() + "', '"
                        + pass + "', "
                        + jabatanSelected.getIdJabatan() + ", '"
                        + UsernameInput.getText() + "', 1)";
                PreparedStatement pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
                pst.execute();

                updatePegawaiTabel();

                NamaPegawaiInput.requestFocus();
                NamaPegawaiInput.setText("");
                PasswordInput.setText("");
                UsernameInput.setText("");

                Alert Sukses = new Alert(Alert.AlertType.INFORMATION);
                Sukses.setTitle("Berhasil!!!");
                Sukses.setHeaderText(null);
                Sukses.setContentText("Akun Pegawai "+ NamaPegawaiInput.getText() + "Berhasil Dibuat" );

                Sukses.showAndWait();

            } catch (SQLException throwables) {
                Alert gagal = new Alert(Alert.AlertType.INFORMATION);
                gagal.setTitle("Gagal!!!");
                gagal.setHeaderText(null);
                gagal.setContentText("Akun Gagal Dibuat" );

                gagal.showAndWait();
            }
        }

    }

    public void TableStockSelected(MouseEvent mouseEvent) {
        StockModel SelectedStock = ManagerStockTable.getSelectionModel().getSelectedItems().get(0);

        idStockSelected = SelectedStock.getIdBarang();
        JumlahStockSelected = SelectedStock.getStock();
        MIdBarangLabel.setText(String.valueOf(idStockSelected));
        MNamaBarangLabel.setText(SelectedStock.getBarang());
        MStockBarangLabel.setText(String.valueOf(SelectedStock.getStock()));
        MHargaJualLabel.setText(SelectedStock.getHargaJ());

    }

    public void CariOnKeyReleased(KeyEvent keyEvent) {
        Koneksi();
        ManagerStockTable.getItems().clear();
        ManagerStockTable.refresh();
        int i=0;
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql =  "select * " +
                    "from barang as b " +
                    "inner join type as t on b.id_type=t.id_type " +
                    "inner join gudang as g on b.id_barang=g.id_barang " +
                    "where b.nama_barang like '%"
                    + ManagerCariInput.getText() + "%'" +
                    "order by g.id_barang asc;";
            ResultSet res = stat.executeQuery(sql);
            while (res.next()){
                i++;
                StockData.add(new StockModel(i,res.getInt("id_barang"),res.getString("nama_barang"),
                        res.getString("nama_type"),nf.format(res.getInt("harga_beli")),nf.format(res.getInt("harga_jual")),res.getInt("stock")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ManagerStockTable.setItems(StockData);
    }

    public void ManagerCariButtonPressed(ActionEvent actionEvent) {
    }

    public void UpdateHargaButtonPressed(ActionEvent actionEvent) {
        try {
            String sql;
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            sql = "select * from gudang where id_barang = "
                    + idStockSelected + ";";
            ResultSet res = stat.executeQuery(sql);
            res.next();
            int IdGudang = res.getInt("id_gudang");
            sql = "update barang set harga_jual = "
                    + Integer.parseInt(UpdateHargaInput.getText()) + " where id_barang = "
                    + idStockSelected + ";";
            PreparedStatement pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
            pst.execute();
            MHargaJualLabel.setText(UpdateHargaInput.getText());
            sql = "insert into riwayat_gudang value(null,"
                    + pegawai + ",now(),"
                    + IdGudang + ","
                    + UpdateHargaInput.getText()+ ",'Update Harga');";
            pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
            pst.execute();
            updateStockTabel();

            Alert Sukses = new Alert(Alert.AlertType.INFORMATION);
            Sukses.setTitle("Berhasil!!!");
            Sukses.setHeaderText(null);
            Sukses.setContentText("Harga Jual Berhasil Diupdate");

            Sukses.showAndWait();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void MTambahStockButtonPressed(ActionEvent actionEvent) {
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select * from gudang where id_barang = "
                    + idStockSelected + ";";
            ResultSet res = stat.executeQuery(sql);
            res.next();
            int IdGudang = res.getInt("id_gudang");
            JumlahStockSelected += Integer.parseInt(MUpdateStockInput.getText());
            sql = "update gudang set stock = "
                    + JumlahStockSelected + " where id_barang = "
                    + idStockSelected + ";";
            PreparedStatement pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
            pst.execute();
            MStockBarangLabel.setText(String.valueOf(MUpdateStockInput.getText()));
            sql = "insert into riwayat_gudang value(null,"
                    + pegawai + ",now(),"
                    + IdGudang + ","
                    + MUpdateStockInput.getText()+ ",'Tambah');";
            pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
            pst.execute();
            updateStockTabel();
            MStockBarangLabel.setText(String.valueOf(JumlahStockSelected));
            Alert Sukses = new Alert(Alert.AlertType.INFORMATION);
            Sukses.setTitle("Berhasil!!!");
            Sukses.setHeaderText(null);
            Sukses.setContentText("Stock Berhasil Ditambahkan");

            Sukses.showAndWait();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void MUpdateStockButtonPressed(ActionEvent actionEvent) {
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "select * from gudang where id_barang = "
                    + idStockSelected + ";";
            ResultSet res = stat.executeQuery(sql);
            res.next();
            int StockChanged = Integer.parseInt(MUpdateStockInput.getText()) - res.getInt("stock") ;
            int IdGudang = res.getInt("id_gudang");
            sql = "update gudang set stock = "
                    + MUpdateStockInput.getText() + " where id_barang = "
                    + idStockSelected + ";";
            PreparedStatement pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
            pst.execute();
            MStockBarangLabel.setText(String.valueOf(MUpdateStockInput.getText()));
            sql = "insert into riwayat_gudang value(null,"
                    + pegawai + ",now(),"
                    + IdGudang + ","
                    + StockChanged+ ",'Update');";
            pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
            pst.execute();
            updateStockTabel();
            JumlahStockSelected = Integer.parseInt(MUpdateStockInput.getText());
            MUpdateStockInput.setText("");
            Alert Sukses = new Alert(Alert.AlertType.INFORMATION);
            Sukses.setTitle("Berhasil!!!");
            Sukses.setHeaderText(null);
            Sukses.setContentText("Barang Berhasil Diupdate");

            Sukses.showAndWait();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void SatuanComboSelected(ActionEvent actionEvent) {

    }

    public void RiwayatGudangButtonPressed(ActionEvent actionEvent) {
        updateRiwayatTabel();
        MRiwayatGudangPane.toFront();

    }

    public void BuatBarangButtonPressed(ActionEvent actionEvent) {
        TypeModel selectedType = SatuanCombo.getSelectionModel().getSelectedItem();
        if (NamaBarangInput.getText().equals("") || selectedType == null || HargaBeliInput.getText().equals("")) {
            Alert gagal = new Alert(Alert.AlertType.INFORMATION);
            gagal.setTitle("Alert");
            gagal.setHeaderText(null);
            gagal.setContentText("Nama Barang, Type dan Harga Beli wajib di isi");
            gagal.showAndWait();
        } else {
            try {
                String sql = "select nama_barang from barang where nama_barang = '"+ NamaBarangInput.getText() +"'";
                Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
                ResultSet res = stat.executeQuery(sql);
                if(res.next()){
                    System.out.println("error");
                    Alert Sudah = new Alert(Alert.AlertType.INFORMATION);
                    Sudah.setTitle("Gagal");
                    Sudah.setHeaderText(null);
                    Sudah.setContentText("Barang Sudah Ada");

                    Sudah.showAndWait();
                }else {
                    try {
                        sql = "insert into barang value(null, '"
                                + NamaBarangInput.getText() + "', "
                                + selectedType.getIdType() + ", "
                                + Integer.parseInt(HargaBeliInput.getText()) + ", " +
                                Integer.parseInt(HargaJualInput.getText()) +
                                ")";
                        PreparedStatement pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
                        pst.execute();
                        stat = Objects.requireNonNull(Koneksi()).createStatement();
                        sql = "select id_barang from barang order by id_barang desc limit 1";
                        res = stat.executeQuery(sql);
                        res.next();
                        int LidBarang = res.getInt("id_barang");
                        System.out.println(LidBarang);
                        sql = "insert into gudang value(null, "
                                + LidBarang + "," + Integer.parseInt(BarangBaruStock.getText()) + ")";
                        pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
                        pst.execute();
                        sql = "insert into riwayat_gudang value(null,"
                                + pegawai + ",now(),"
                                + "(select id_gudang from gudang where id_barang = " + LidBarang +"),"
                                + Integer.parseInt(BarangBaruStock.getText())+ ", 'Barang Baru')";
                        pst = Objects.requireNonNull(Koneksi()).prepareStatement(sql);
                        pst.execute();
                        updateStockTabel();
                        NamaBarangInput.setText("");
                        HargaBeliInput.setText("0");
                        HargaJualInput.setText("0");
                        BarangBaruStock.setText("0");
                        Alert Sukses = new Alert(Alert.AlertType.INFORMATION);
                        Sukses.setTitle("Berhasil!!!");
                        Sukses.setHeaderText(null);
                        Sukses.setContentText("Barang Berhasil Ditambahkan");

                        Sukses.showAndWait();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public void PemasukanTabelPressed(MouseEvent mouseEvent) {
    }

    public void PengeluaranTabelPressed(MouseEvent mouseEvent) {
    }

    public void updateManagerPane() {
        try {
            Statement stat = Objects.requireNonNull(Koneksi()).createStatement();
            String sql = "SELECT (SELECT COUNT(id_transaksi) FROM transaksi WHERE status_transaksi = 1 ) AS Total ,(SELECT COUNT(id_transaksi) FROM transaksi WHERE tgl_transaksi = CURDATE() AND status_transaksi = 1 )AS TotalHari\n";
            ResultSet res = stat.executeQuery(sql);
            res.next();
            JumlahTrasaksiLabel.setText(String.valueOf(res.getInt("Total")));
            TransaksiHarIniLabel.setText(String.valueOf(res.getInt("TotalHari")));
            sql = "select sum(b.harga_beli*rg.penambahan) as jumlah, rg.tgl_masuk_barang from barang as b inner join gudang as g on b.id_barang=g.id_barang inner join riwayat_gudang as rg on rg.id_gudang=g.id_gudang";
            res = stat.executeQuery(sql);
            res.next();
            TotalPengeluaranLabel.setText(nf.format(res.getInt("jumlah")));
            pengeluaran = res.getInt("jumlah");
            sql = "SELECT SUM(d.harga * d.jumlah) AS \"PemasukanHari\"\n" +
                    "FROM detail AS d \n" +
                    "INNER JOIN transaksi AS t ON d.id_transaksi = t.id_transaksi\n" +
                    "WHERE tgl_transaksi = CURDATE() AND status_transaksi = 1";
            res = stat.executeQuery(sql);
            res.next();
            PemasukanHariIniLabel.setText(nf.format(res.getInt("PemasukanHari")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        int pendapatan = totalPemasukan;
        if(pendapatan >= pengeluaran){
            LabaLabel.setText(nf.format(pendapatan+pengeluaran));
        }
        else{
            LabaLabel.setText(nf.format(pendapatan-pengeluaran));
        }
        TotalPemasukanLabel.setText(String.valueOf(nf.format(totalPemasukan)));

    }

    public void MLogoutButtonPressed(ActionEvent actionEvent) {
        try {
            Stage thisState = (Stage) MLogoutButton.getScene().getWindow();
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
