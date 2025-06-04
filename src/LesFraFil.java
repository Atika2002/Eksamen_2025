import java.sql.SQLException;
import java.util.Scanner;

public class LesFraFil {
    public void sqlMeny() throws SQLException {
        Scanner reader = new Scanner(System.in);
        boolean run = true;
        while (run) {
            System.out.println("Meny");
            System.out.println("1: Alt i funn");
            System.out.println("2: Eldre en angitt årstid");
            System.out.println("3: Antall funn");
            System.out.println("4: Slutt");

            int valg = Integer.parseInt(reader.nextLine());
            switch (valg) {
//                case 1 -> listAllFunn(funnService);
//                case 2 -> listUtAngittTid(funnService, reader);
//                case 3 -> listUtAnallFunn(funnService);
//                case 4 -> {run = false;}
            }
        }
    }
}
