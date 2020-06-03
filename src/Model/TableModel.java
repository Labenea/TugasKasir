package Model;

public class TableModel {
    private int no,harga,qty,total;
    private String namaBarang,satuan,kode;

    public TableModel(int no, String kode, String namaBarang, int harga, int qty, String satuan, int total) {
        this.no = no;
        this.kode = kode;
        this.harga = harga;
        this.qty = qty;
        this.total = total;
        this.namaBarang = namaBarang;
        this.satuan = satuan;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }
}
