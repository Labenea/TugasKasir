package Model;

public class PengeluaranModel {
    private int no;
    private String tanggal;
    private int pengeluaran;

    public PengeluaranModel(int no, String tanggal, int pengeluaran) {
        this.no = no;
        this.tanggal = tanggal;
        this.pengeluaran = pengeluaran;
    }

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

    public int getPengeluaran() {
        return pengeluaran;
    }

    public void setPengeluaran(int pengeluaran) {
        this.pengeluaran = pengeluaran;
    }
}
