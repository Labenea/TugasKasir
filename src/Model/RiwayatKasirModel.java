package Model;

public class RiwayatKasirModel {
    private int no;
    private int idrk;
    private String namarpegawai;
    private String jabatanr;
    private String tglr;
    private String pdptn;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getIdrk() {
        return idrk;
    }

    public void setIdrk(int idrk) {
        this.idrk = idrk;
    }

    public String getNamarpegawai() {
        return namarpegawai;
    }

    public void setNamarpegawai(String namarpegawai) {
        this.namarpegawai = namarpegawai;
    }

    public String getJabatanr() {
        return jabatanr;
    }

    public void setJabatanr(String jabatanr) {
        this.jabatanr = jabatanr;
    }

    public String getTglr() {
        return tglr;
    }

    public void setTglr(String tglr) {
        this.tglr = tglr;
    }

    public String getPdptn() {
        return pdptn;
    }

    public void setPdptn(String pdptn) {
        this.pdptn = pdptn;
    }

    public RiwayatKasirModel(int no, int idrk, String namarpegawai, String jabatanr, String tglr, String pdptn) {
        this.no = no;
        this.idrk = idrk;
        this.namarpegawai = namarpegawai;
        this.jabatanr = jabatanr;
        this.tglr = tglr;
        this.pdptn = pdptn;
    }
}
