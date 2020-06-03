package Model;

public class RiwayatModel {
    private int No;
    private String NamaPegawai;
    private int IdBarang;
    private String NamaBarang;
    private String Tanggal;
    private int Perubahan;
    private String StatusAction;

    public void setPerubahan(int perubahan) {
        Perubahan = perubahan;
    }

    public String getStatusAction() {
        return StatusAction;
    }

    public void setStatusAction(String statusAction) {
        StatusAction = statusAction;
    }

    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
    }

    public String getNamaPegawai() {
        return NamaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        NamaPegawai = namaPegawai;
    }

    public int getIdBarang() {
        return IdBarang;
    }

    public void setIdBarang(int idBarang) {
        IdBarang = idBarang;
    }

    public String getNamaBarang() {
        return NamaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        NamaBarang = namaBarang;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public int getPerubahan() {
        return Perubahan;
    }

    public void setPerbubahan(int perubahan) {
        Perubahan = perubahan;
    }


    public RiwayatModel(int no, String namaPegawai, int idBarang, String namaBarang, String tanggal, int perubahan, String statusAction) {
        No = no;
        NamaPegawai = namaPegawai;
        IdBarang = idBarang;
        NamaBarang = namaBarang;
        Tanggal = tanggal;
        Perubahan = perubahan;
        StatusAction = statusAction;
    }
}
