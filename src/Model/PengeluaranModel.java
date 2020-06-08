package Model;

public class PengeluaranModel {
    private String tanggal;
    private String jenis;
    private String pengeluaran;

    public PengeluaranModel(String tanggal, String jenis, String pengeluaran) {
        this.tanggal = tanggal;
        this.jenis = jenis;
        this.pengeluaran = pengeluaran;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPengeluaran() {
        return pengeluaran;
    }

    public void setPengeluaran(String pengeluaran) {
        this.pengeluaran = pengeluaran;
    }
}
