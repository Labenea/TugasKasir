package Model;

public class PegawaiModel {
    private int no;
    private int idPegawai;
    private String namaPegawai;
    private String jabatanPegawai;
    private  String usernamePegawai;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(int idPegawai) {
        this.idPegawai = idPegawai;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

    public String getJabatanPegawai() {
        return jabatanPegawai;
    }

    public void setJabatanPegawai(String jabatanPegawai) {
        this.jabatanPegawai = jabatanPegawai;
    }

    public String getUsernamePegawai() {
        return usernamePegawai;
    }

    public void setUsernamePegawai(String usernamePegawai) {
        this.usernamePegawai = usernamePegawai;
    }

    public PegawaiModel(int no, int idPegawai, String namaPegawai, String jabatanPegawai, String usernamePegawai) {
        this.no = no;
        this.idPegawai = idPegawai;
        this.namaPegawai = namaPegawai;
        this.jabatanPegawai = jabatanPegawai;
        this.usernamePegawai = usernamePegawai;
    }
}
