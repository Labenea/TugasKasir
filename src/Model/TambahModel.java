package Model;

public class TambahModel {
    public Integer No;
    public String Kode;
    public String Nama;
    public String Satuan;
    public Integer Stock;

    public Integer getStock() {
        return Stock;
    }

    public void setStock(Integer stock) {
        Stock = stock;
    }

    public Integer Harga;

    public Integer getNo() {
        return No;
    }

    public void setNo(Integer no) {
        No = no;
    }

    public String getKode() {
        return Kode;
    }

    public void setKode(String kode) {
        Kode = kode;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getSatuan() {
        return Satuan;
    }

    public void setSatuan(String satuan) {
        Satuan = satuan;
    }

    public Integer getHarga() {
        return Harga;
    }

    public void setHarga(Integer harga) {
        Harga = harga;
    }

    public TambahModel(Integer no, String kode, String nama, String satuan,Integer stock, Integer harga) {
        No = no;
        Kode = kode;
        Nama = nama;
        Satuan = satuan;
        Harga = harga;
        Stock = stock;
    }
}
