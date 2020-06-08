package Model;

public class JabatanModel {
    private int idJabatan;

    public int getIdJabatan() {
        return idJabatan;
    }

    public void setIdJabatan(int idJabatan) {
        this.idJabatan = idJabatan;
    }

    public String getNamaJabatan() {
        return namaJabatan;
    }

    public void setNamaJabatan(String namaJabatan) {
        this.namaJabatan = namaJabatan;
    }

    private String namaJabatan;

    public JabatanModel(int idJabatan, String namaJabatan) {
        this.idJabatan = idJabatan;
        this.namaJabatan = namaJabatan;
    }

    @Override
    public String toString() {
        return this.getNamaJabatan();
    }
}
