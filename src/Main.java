import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        Meny meny = new Meny();
//        try {
//            program.lesFunn();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        try {
            meny.sqlMeny();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}