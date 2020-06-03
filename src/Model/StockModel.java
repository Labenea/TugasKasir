package Model;

public class StockModel {
    private int No;
    private int IdBarang;
    private String HargaB;
    private String HargaJ;
    private int Stock;
    private String Barang,Type;

    public StockModel(int no, int idBarang, String barang, String type, String hargaB, String hargaJ, int stock) {
        No = no;
        IdBarang = idBarang;
        HargaB = hargaB;
        HargaJ = hargaJ;
        Stock = stock;
        Barang = barang;
        Type = type;
    }

    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
    }

    public int getIdBarang() {
        return IdBarang;
    }

    public void setIdBarang(int idBarang) {
        IdBarang = idBarang;
    }

    public String getHargaB() {
        return HargaB;
    }

    public void setHargaB(String hargaB) {
        HargaB = hargaB;
    }

    public String getHargaJ() {
        return HargaJ;
    }

    public void setHargaJ(String hargaJ) {
        HargaJ = hargaJ;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public String getBarang() {
        return Barang;
    }

    public void setBarang(String barang) {
        Barang = barang;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
