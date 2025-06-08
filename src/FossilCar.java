public class FossilCar extends Kjoretoy{
    private String fuelType;
    private int fuelAmount;

    public FossilCar(int vehicleId, String brand, String model, int yearModel, String registrationNumber, String chassisNumber, boolean driveable, int numberOfSellableWheels, int scrapyardId, String fuelType, int fuelAmount) {
        super(vehicleId, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, scrapyardId);
        this.fuelType = fuelType;
        this.fuelAmount = fuelAmount;
    }

    public String getFuelType() {
        return fuelType;
    }

    public int getFuelAmount() {
        return fuelAmount;
    }

    @Override
    public String toString() {
        return "FossilCar{" +
                "fuelType='" + fuelType + '\'' +
                ", fuelAmount=" + fuelAmount +
                "} " + super.toString();
    }
}
