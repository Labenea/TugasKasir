package Model;

public class JenisCombo {
    private int idJenis;
    private String namaJenis;
    public JenisCombo(int idJenis, String namaJenis) {
        this.idJenis = idJenis;
        this.namaJenis = namaJenis;
    }

    public int getIdJenis() {
        return idJenis;
    }

    public void setIdJenis(int idJenis) {
        this.idJenis = idJenis;
    }

    public String getNamaJenis() {
        return namaJenis;
    }

    public void setNamaJenis(String namaJenis) {
        this.namaJenis = namaJenis;
    }

    @Override
    public String toString() {
        return this.getNamaJenis();
    }


}
