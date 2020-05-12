package sample;

public class Karyawan{
    protected String nama;
    protected String username;
    protected String password;

    public Karyawan(String n,String u,String p){
        nama = n;
        username = u;
        password = p;
    }

    public double getGaji(){
        return 3000000;
    }

}
