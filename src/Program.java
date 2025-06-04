import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Program {
    private final DBService dbService;
    public Program() {
        dbService = new DBService();
    }

    public void lesFunn() throws FileNotFoundException, SQLException {
        File funnFile = new File(".txt");
        Scanner reader = new Scanner(funnFile);
        try (Connection connection = dbService.getDataSource().getConnection()) {
            while (reader.hasNextLine()) {
                String overskrift = reader.nextLine();
                switch (overskrift) {
                    //case "Personer:" ->
                    //case "Museer:" -> {
                    }
                    reader.nextLine();
                }
            }
        reader.close();
    }



}
