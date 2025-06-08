import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Meny {
    DBService dbService = new DBService();
    public void sqlMeny() throws SQLException {
        Scanner reader = new Scanner(System.in);
        boolean run = true;
        while (run) {
            System.out.println("Meny");
            System.out.println("1: Alt i kjøretøy");
            System.out.println("2: Antall drivestoff i fossil biller");
            System.out.println("3: Kjørbare kjøretøy");
            System.out.println("4: Hent kjøretøy merke");
            System.out.println("5: Avslutt");

            int valg = Integer.parseInt(reader.nextLine());
            switch (valg) {
                case 1 -> hentAllKjoretoy(dbService);
                case 2 -> visTotalDrivstoff(dbService);
                case 3 -> visKjorbareKjoretoy(dbService);
                case 4 -> hentKjoretoyEtterMerke(dbService, reader);
                case 5 -> run = false;
            }
        }
    }

    private void visKjorbareKjoretoy(DBService dbService) {
        List<Kjoretoy> kjoretoyList = dbService.visKjorbareKjoretoy();
        for (Kjoretoy kjoretoy : kjoretoyList) {
            System.out.println(kjoretoy);
        }
    }

    private void hentKjoretoyEtterMerke(DBService dbService, Scanner reader) {
        System.out.print("Skriv inn merke til bil: ");
        String merke = reader.nextLine();
        try {
            List<Kjoretoy> resultater = dbService.getVehiclesByBrand(merke);
                System.out.println("Merke: " + merke);
            for (Kjoretoy kjoretoy : resultater) {
                System.out.println(kjoretoy);
            }


        } catch (SQLException e) {
            System.out.println("Feil ved søk etter kjøretøy med merke: " + e.getMessage());
        }
    }

    private void visTotalDrivstoff(DBService dbService) {
        int total = dbService.getTotalDrivestoff();
        System.out.println("Total drivstoff i fossilbiler " + total);
    }

    private void hentAllKjoretoy(DBService dbService) {
        List<Kjoretoy> kjoretoyList = dbService.readVehicles();
        for (Kjoretoy kjoretoy : kjoretoyList) {
            System.out.println(kjoretoy);
        }
    }
}
