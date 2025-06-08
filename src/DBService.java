import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBService {
    private static final String LEGG_SCRAPYARD_SQL = "INSERT INTO Scrapyard VALUES(?,?,?,?)";
    private static final String LEGG_FOSSIL_CAR_SQL = "INSERT INTO FossilCar VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String LEGG_ELECTRIC_CAR = "INSERT INTO ElectricCar VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String ADD_MOTORCYCLE_SQL = "INSERT INTO Motorcycle VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String TOTALT_DRIVESTOFF_FOSILL = "SELECT SUM(FuelAmount) FROM FossilCar";
    private static final String GET_ALL_FOSSIL_CARS_SQL = "SELECT * FROM FossilCar";
    private static final String GET_ALL_ELECTRIC_CARS_SQL = "SELECT * FROM ElectricCar";
    private static final String GET_ALL_MOTORCYCLES_SQL = "SELECT * FROM Motorcycle";
    private static final String GET_DRIVEABLE_FOSSIL_CARS_SQL = "SELECT * FROM FossilCar WHERE Driveable = true";
    private static final String GET_DRIVEABLE_ELECTRIC_CARS_SQL = "SELECT * FROM ElectricCar WHERE Driveable = true";
    private static final String GET_DRIVEABLE_MOTORCYCLES_SQL = "SELECT * FROM Motorcycle WHERE Driveable = true";
    private static final String GET_FOSSIL_CAR_BY_BRAND_SQL = "SELECT * FROM FossilCar WHERE Brand = ?";
    private static final String GET_ELECTRIC_CAR_BY_BRAND_SQL = "SELECT * FROM ElectricCar WHERE Brand = ?";
    private static final String GET_MOTORCYCLE_BY_BRAND_SQL = "SELECT * FROM Motorcycle WHERE Brand = ?";
    private final MysqlDataSource dataSource;

    public DBService() {
        dataSource = new MysqlDataSource();
        dataSource.setServerName(PropertiesProvider.PROPERTIES.getProperty("host"));
        dataSource.setPortNumber(Integer.parseInt(PropertiesProvider.PROPERTIES.getProperty("port")));
        dataSource.setDatabaseName(PropertiesProvider.PROPERTIES.getProperty("database_name"));
        dataSource.setUser(PropertiesProvider.PROPERTIES.getProperty("uname"));
        dataSource.setPassword(PropertiesProvider.PROPERTIES.getProperty("pwd"));
    }

    public MysqlDataSource getDataSource() {
        return dataSource;
    }

    public void leggTilScrapyard(List<Scrapyard> scrapyardList, Connection connection) {
        try (PreparedStatement stmt = connection.prepareStatement(LEGG_SCRAPYARD_SQL)) {
            for (Scrapyard scrapyard : scrapyardList) {
                stmt.setInt(1, scrapyard.getId());
                stmt.setString(2, scrapyard.getName());
                stmt.setString(3, scrapyard.getAddress());
                stmt.setString(4, scrapyard.getPhoneNumber());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void leggTilFossilCar(List<FossilCar> fossilCarList, Connection connection) {
        try (PreparedStatement stmt = connection.prepareStatement(LEGG_FOSSIL_CAR_SQL)) {
            for (FossilCar fossilCar : fossilCarList) {
                stmt.setInt(1, fossilCar.getVehicleId());
                stmt.setString(2, fossilCar.getBrand());
                stmt.setString(3, fossilCar.getModel());
                stmt.setInt(4, fossilCar.getYearModel());
                stmt.setString(5, fossilCar.getRegistrationNumber());
                stmt.setString(6, fossilCar.getChassisNumber());
                stmt.setBoolean(7, fossilCar.isDriveable());
                stmt.setInt(8, fossilCar.getNumberOfSellableWheels());
                stmt.setInt(9, fossilCar.getScrapyardId());
                stmt.setString(10, fossilCar.getFuelType());
                stmt.setInt(11, fossilCar.getFuelAmount());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void leggTilElectricCar(List<ElectricCar> electricCarList, Connection connection) {
        try (PreparedStatement stmt = connection.prepareStatement(LEGG_ELECTRIC_CAR)) {
            for (ElectricCar electricCar : electricCarList) {
                stmt.setInt(1, electricCar.getVehicleId());
                stmt.setString(2, electricCar.getBrand());
                stmt.setString(3, electricCar.getModel());
                stmt.setInt(4, electricCar.getYearModel());
                stmt.setString(5, electricCar.getRegistrationNumber());
                stmt.setString(6, electricCar.getChassisNumber());
                stmt.setBoolean(7, electricCar.isDriveable());
                stmt.setInt(8, electricCar.getNumberOfSellableWheels());
                stmt.setInt(9, electricCar.getScrapyardId());
                stmt.setInt(10, electricCar.getBatteryCapacity());
                stmt.setInt(11, electricCar.getChargeLevel());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void leggTilMotorcycle(List<Motorcycle> motorcycleList, Connection connection) {
        try (PreparedStatement stmt = connection.prepareStatement(ADD_MOTORCYCLE_SQL)) {
            for (Motorcycle motorcycle : motorcycleList) {
                stmt.setInt(1, motorcycle.getVehicleId());
                stmt.setString(2, motorcycle.getBrand());
                stmt.setString(3, motorcycle.getModel());
                stmt.setInt(4, motorcycle.getYearModel());
                stmt.setString(5, motorcycle.getRegistrationNumber());
                stmt.setString(6, motorcycle.getChassisNumber());
                stmt.setBoolean(7, motorcycle.isDriveable());
                stmt.setInt(8, motorcycle.getNumberOfSellableWheels());
                stmt.setInt(9, motorcycle.getScrapyardId());
                stmt.setBoolean(10, motorcycle.isHasSidecar());
                stmt.setInt(11, motorcycle.getEngineCapacity());
                stmt.setBoolean(12, motorcycle.isModified());
                stmt.setInt(13, motorcycle.getNumberOfWheels());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Kjoretoy> readVehicles() {
        List<Kjoretoy> vehicles = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(GET_ALL_FOSSIL_CARS_SQL)) {
                while (rs.next()) {
                    int vehicleId = rs.getInt("VehicleID");
                    String brand = rs.getString("Brand");
                    String model = rs.getString("Model");
                    int yearModel = rs.getInt("YearModel");
                    String registrationNumber = rs.getString("RegistrationNumber");
                    String chassisNumber = rs.getString("ChassisNumber");
                    boolean driveable = rs.getBoolean("Driveable");
                    int wheels = rs.getInt("NumberOfSellableWheels");
                    int scrapyardId = rs.getInt("ScrapyardID");
                    String fuelType = rs.getString("FuelType");
                    int fuelAmount = rs.getInt("FuelAmount");
                    FossilCar fossilCar = new FossilCar(vehicleId, brand, model, yearModel, registrationNumber, chassisNumber, driveable, wheels, scrapyardId, fuelType, fuelAmount);
                    vehicles.add(fossilCar);
                }
            }
            try (ResultSet rs = stmt.executeQuery(GET_ALL_ELECTRIC_CARS_SQL)) {
                while (rs.next()) {
                    int vehicleId = rs.getInt("VehicleID");
                    String brand = rs.getString("Brand");
                    String model = rs.getString("Model");
                    int yearModel = rs.getInt("YearModel");
                    String registrationNumber = rs.getString("RegistrationNumber");
                    String chassisNumber = rs.getString("ChassisNumber");
                    boolean driveable = rs.getBoolean("Driveable");
                    int wheels = rs.getInt("NumberOfSellableWheels");
                    int scrapyardId = rs.getInt("ScrapyardID");
                    int batteryCapacity = rs.getInt("BatteryCapacity");
                    int chargeLevel = rs.getInt("ChargeLevel");
                    ElectricCar electricCar = new ElectricCar(vehicleId, brand, model, yearModel, registrationNumber, chassisNumber, driveable, wheels, scrapyardId, batteryCapacity, chargeLevel);
                    vehicles.add(electricCar);
                }
            }
            try (ResultSet rs = stmt.executeQuery(GET_ALL_MOTORCYCLES_SQL)) {
                while (rs.next()) {
                    int vehicleId = rs.getInt("VehicleID");
                    String brand = rs.getString("Brand");
                    String model = rs.getString("Model");
                    int yearModel = rs.getInt("YearModel");
                    String registrationNumber = rs.getString("RegistrationNumber");
                    String chassisNumber = rs.getString("ChassisNumber");
                    boolean driveable = rs.getBoolean("Driveable");
                    int wheels = rs.getInt("NumberOfSellableWheels");
                    int scrapyardId = rs.getInt("ScrapyardID");
                    boolean hasSidecar = rs.getBoolean("HasSidecar");
                    int engineCapacity = rs.getInt("EngineCapacity");
                    boolean isModified = rs.getBoolean("IsModified");
                    int numberOfWheels = rs.getInt("NumberOfWheels");
                    Motorcycle motorcycle = new Motorcycle(vehicleId, brand, model, yearModel, registrationNumber, chassisNumber, driveable, wheels, scrapyardId, hasSidecar, engineCapacity, isModified, numberOfWheels);
                    vehicles.add(motorcycle);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Feil fra databasen", e);
        }
        return vehicles;
    }

    public int getTotalDrivestoff() {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(TOTALT_DRIVESTOFF_FOSILL)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Feil ved henting av total drivstoffmengde", e);
        }
        return 0;
    }

    public List<Kjoretoy> getVehiclesByBrand(String brand) throws SQLException {
        List<Kjoretoy> vehicles = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(GET_FOSSIL_CAR_BY_BRAND_SQL)) {
                stmt.setString(1, brand);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int vehicleId = rs.getInt("VehicleID");
                        String brands = rs.getString("Brand");
                        String model = rs.getString("Model");
                        int yearModel = rs.getInt("YearModel");
                        String registrationNumber = rs.getString("RegistrationNumber");
                        String chassisNumber = rs.getString("ChassisNumber");
                        boolean driveable = rs.getBoolean("Driveable");
                        int wheels = rs.getInt("NumberOfSellableWheels");
                        int scrapyardId = rs.getInt("ScrapyardID");
                        String fuelType = rs.getString("FuelType");
                        int fuelAmount = rs.getInt("FuelAmount");
                        FossilCar fossilCar = new FossilCar(vehicleId, brands, model, yearModel, registrationNumber, chassisNumber, driveable, wheels, scrapyardId, fuelType, fuelAmount);
                        vehicles.add(fossilCar);
                    }
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(GET_ELECTRIC_CAR_BY_BRAND_SQL)) {
                stmt.setString(1, brand);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int vehicleId = rs.getInt("VehicleID");
                        String brands = rs.getString("Brand");
                        String model = rs.getString("Model");
                        int yearModel = rs.getInt("YearModel");
                        String registrationNumber = rs.getString("RegistrationNumber");
                        String chassisNumber = rs.getString("ChassisNumber");
                        boolean driveable = rs.getBoolean("Driveable");
                        int wheels = rs.getInt("NumberOfSellableWheels");
                        int scrapyardId = rs.getInt("ScrapyardID");
                        int batteryCapacity = rs.getInt("BatteryCapacity");
                        int chargeLevel = rs.getInt("ChargeLevel");
                        ElectricCar electricCar = new ElectricCar(vehicleId, brands, model, yearModel, registrationNumber, chassisNumber, driveable, wheels, scrapyardId, batteryCapacity, chargeLevel);
                        vehicles.add(electricCar);
                    }
                }
            }
            try (PreparedStatement stmt = conn.prepareStatement(GET_MOTORCYCLE_BY_BRAND_SQL)) {
                stmt.setString(1, brand);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int vehicleId = rs.getInt("VehicleID");
                        String brands = rs.getString("Brand");
                        String model = rs.getString("Model");
                        int yearModel = rs.getInt("YearModel");
                        String registrationNumber = rs.getString("RegistrationNumber");
                        String chassisNumber = rs.getString("ChassisNumber");
                        boolean driveable = rs.getBoolean("Driveable");
                        int wheels = rs.getInt("NumberOfSellableWheels");
                        int scrapyardId = rs.getInt("ScrapyardID");
                        boolean hasSidecar = rs.getBoolean("HasSidecar");
                        int engineCapacity = rs.getInt("EngineCapacity");
                        boolean isModified = rs.getBoolean("IsModified");
                        int numberOfWheels = rs.getInt("NumberOfWheels");
                        Motorcycle motorcycle = new Motorcycle(vehicleId, brands, model, yearModel, registrationNumber, chassisNumber, driveable, wheels, scrapyardId, hasSidecar, engineCapacity, isModified, numberOfWheels);
                        vehicles.add(motorcycle);
                    }
                }
            }
        }

        return vehicles;
    }

    public List<Kjoretoy> visKjorbareKjoretoy() {
        List<Kjoretoy> vehicles = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(GET_DRIVEABLE_FOSSIL_CARS_SQL)) {
                while (rs.next()) {
                    int vehicleId = rs.getInt("VehicleID");
                    String brand = rs.getString("Brand");
                    String model = rs.getString("Model");
                    int yearModel = rs.getInt("YearModel");
                    String registrationNumber = rs.getString("RegistrationNumber");
                    String chassisNumber = rs.getString("ChassisNumber");
                    boolean driveable = rs.getBoolean("Driveable");
                    int wheels = rs.getInt("NumberOfSellableWheels");
                    int scrapyardId = rs.getInt("ScrapyardID");
                    String fuelType = rs.getString("FuelType");
                    int fuelAmount = rs.getInt("FuelAmount");
                    FossilCar fossilCar = new FossilCar(vehicleId, brand, model, yearModel, registrationNumber, chassisNumber, driveable, wheels, scrapyardId, fuelType, fuelAmount);
                    vehicles.add(fossilCar);
                }
            }
            try (ResultSet rs = stmt.executeQuery(GET_DRIVEABLE_ELECTRIC_CARS_SQL)) {
                while (rs.next()) {
                    int vehicleId = rs.getInt("VehicleID");
                    String brand = rs.getString("Brand");
                    String model = rs.getString("Model");
                    int yearModel = rs.getInt("YearModel");
                    String registrationNumber = rs.getString("RegistrationNumber");
                    String chassisNumber = rs.getString("ChassisNumber");
                    boolean driveable = rs.getBoolean("Driveable");
                    int wheels = rs.getInt("NumberOfSellableWheels");
                    int scrapyardId = rs.getInt("ScrapyardID");
                    int batteryCapacity = rs.getInt("BatteryCapacity");
                    int chargeLevel = rs.getInt("ChargeLevel");
                    ElectricCar electricCar = new ElectricCar(vehicleId, brand, model, yearModel, registrationNumber, chassisNumber, driveable, wheels, scrapyardId, batteryCapacity, chargeLevel);
                    vehicles.add(electricCar);
                }
            }
            try (ResultSet rs = stmt.executeQuery(GET_DRIVEABLE_MOTORCYCLES_SQL)) {
                while (rs.next()) {
                    int vehicleId = rs.getInt("VehicleID");
                    String brand = rs.getString("Brand");
                    String model = rs.getString("Model");
                    int yearModel = rs.getInt("YearModel");
                    String registrationNumber = rs.getString("RegistrationNumber");
                    String chassisNumber = rs.getString("ChassisNumber");
                    boolean driveable = rs.getBoolean("Driveable");
                    int wheels = rs.getInt("NumberOfSellableWheels");
                    int scrapyardId = rs.getInt("ScrapyardID");
                    boolean hasSidecar = rs.getBoolean("HasSidecar");
                    int engineCapacity = rs.getInt("EngineCapacity");
                    boolean isModified = rs.getBoolean("IsModified");
                    int numberOfWheels = rs.getInt("NumberOfWheels");
                    Motorcycle motorcycle = new Motorcycle(vehicleId, brand, model, yearModel, registrationNumber, chassisNumber, driveable, wheels, scrapyardId, hasSidecar, engineCapacity, isModified, numberOfWheels);
                    vehicles.add(motorcycle);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }
}


