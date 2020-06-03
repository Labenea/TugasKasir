package Model;

public class TypeModel {
    private int idType;
    private String namaType;

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getNamaType() {
        return namaType;
    }

    public void setNamaType(String namaType) {
        this.namaType = namaType;
    }

    public TypeModel(int idType, String namaType) {
        this.idType = idType;
        this.namaType = namaType;
    }

    @Override
    public String toString() {
        return this.getNamaType();
    }
}
