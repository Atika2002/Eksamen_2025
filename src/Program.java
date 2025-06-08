import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    private final DBService dbService;
    public Program() {
        dbService = new DBService();
    }

    public void lesFunn() throws FileNotFoundException, SQLException {
        File funnFile = new File("vehicles.txt");
        Scanner reader = new Scanner(funnFile);
        try (Connection connection = dbService.getDataSource().getConnection()) {
            while (reader.hasNextLine()) {
                int antallScrapyard = Integer.parseInt(reader.nextLine());
                for (int i = 0; i < antallScrapyard; i++) {
                    List<Scrapyard> scrapyardList = lesScrapyard(reader);
                    reader.nextLine();
                    System.out.println(scrapyardList);
                    dbService.leggTilScrapyard(scrapyardList, connection);
                }
                int antallKjoretoy = Integer.parseInt(reader.nextLine());
                for (int i = 0; i < antallKjoretoy; i++) {
                    lesKjoretoy(reader, connection);
                    reader.nextLine();
                }
                }
            }
        reader.close();
    }

    private void lesKjoretoy(Scanner reader, Connection connection) {
        int id = Integer.parseInt(reader.nextLine());
        int scrapyardId = Integer.parseInt(reader.nextLine());
        String kjoretoy = reader.nextLine();
        String brand = reader.nextLine();
        String model = reader.nextLine();
        int yearModel = Integer.parseInt(reader.nextLine());
        String registrationNumber = reader.nextLine();
        String chassisNumber = reader.nextLine();
        boolean driveable = Boolean.parseBoolean(reader.nextLine());
        int numberOfSellableWheels = Integer.parseInt(reader.nextLine());
        switch (kjoretoy) {
            case "FossilCar" -> {
                List<FossilCar> fossilCarList = lesFossilCar(id, scrapyardId, brand, model, yearModel, registrationNumber,chassisNumber, driveable, numberOfSellableWheels, reader);
                System.out.println(fossilCarList);
                dbService.leggTilFossilCar(fossilCarList, connection);
            }
            case "ElectricCar" -> {
                List<ElectricCar> electricCarList = lesElectricCar(id, scrapyardId, brand, model, yearModel, registrationNumber,chassisNumber, driveable, numberOfSellableWheels, reader);
                System.out.println(electricCarList);
                dbService.leggTilElectricCar(electricCarList, connection);
            }
            case "Motorcycle" -> {
                List<Motorcycle> motorcycleList = lesMotorcycle(id, scrapyardId, brand, model, yearModel, registrationNumber,chassisNumber, driveable, numberOfSellableWheels, reader);
                System.out.println(motorcycleList);
                dbService.leggTilMotorcycle(motorcycleList, connection);
            }
        }
    }

    private List<Motorcycle> lesMotorcycle(int id, int scrapyardId, String brand, String model, int yearModel, String registrationNumber, String chassisNumber, boolean driveable, int numberOfSellableWheels, Scanner reader) {
        List<Motorcycle> motorcycleList = new ArrayList<>();
        boolean hasSidecar = Boolean.parseBoolean(reader.nextLine());
        int engineCapacity = Integer.parseInt(reader.nextLine());
        boolean isModified = Boolean.parseBoolean(reader.nextLine());
        int numberOfWheels = Integer.parseInt(reader.nextLine());
        Motorcycle motorcycle = new Motorcycle(id, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, scrapyardId, hasSidecar, engineCapacity, isModified, numberOfWheels);
        motorcycleList.add(motorcycle);
        return motorcycleList;
    }

    private List<ElectricCar> lesElectricCar(int id, int scrapyardId, String brand, String model, int yearModel, String registrationNumber, String chassisNumber, boolean driveable, int numberOfSellableWheels, Scanner reader) {
        List<ElectricCar> electricCarList = new ArrayList<>();
        int batteryCapacity = Integer.parseInt(reader.nextLine());
        int chargeLevel = Integer.parseInt(reader.nextLine());
        ElectricCar electricCar = new ElectricCar(id, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, scrapyardId, batteryCapacity, chargeLevel);
        electricCarList.add(electricCar);
        return electricCarList;
    }

    private List<FossilCar> lesFossilCar(int id, int scrapyardId, String brand, String model, int yearModel, String registrationNumber, String chassisNumber, boolean driveable, int numberOfSellableWheels, Scanner reader) {
        List<FossilCar> fossilCarList = new ArrayList<>();
        String fuelType = reader.nextLine();
        int fuelAmount = Integer.parseInt(reader.nextLine());
        FossilCar fossilCar = new FossilCar(id, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, scrapyardId, fuelType, fuelAmount);
        fossilCarList.add(fossilCar);
        return fossilCarList;
    }

    private List<Scrapyard> lesScrapyard(Scanner reader) {
        List<Scrapyard> scrapyardList = new ArrayList<>();
        int id = Integer.parseInt(reader.nextLine());
        String name = reader.nextLine();
        String address = reader.nextLine();
        String number = reader.nextLine();
        Scrapyard scrapyard = new Scrapyard(id, name, address, number);
        scrapyardList.add(scrapyard);
        return scrapyardList;
    }


}
