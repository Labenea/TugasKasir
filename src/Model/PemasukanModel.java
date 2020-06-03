package Model;

public class PemasukanModel {
    private int no;
    private String tanggal;
    private String pemasukan;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPemasukan() {
        return pemasukan;
    }

    public void setPemasukan(String pemasukan) {
        this.pemasukan = pemasukan;
    }

    public PemasukanModel(int no, String tanggal, String pemasukan) {
        this.no = no;
        this.tanggal = tanggal;
        this.pemasukan = pemasukan;
    }
}
