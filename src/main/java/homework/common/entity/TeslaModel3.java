package homework.common.entity;

public class TeslaModel3 extends Car {
    private String number;
    private int firmwareVersion;

    public TeslaModel3(String number, int firmwareVersion) {
        this.number = number;
        this.firmwareVersion = firmwareVersion;
    }

//    @Reflectable(name="reflectaqwe", value = "some data")
    public void setNumber (String number) {
        this.number = number;
        System.out.println(number);
    }

//    @Reflectable(name="reflectaqwe", value = "some data")
    public void setFirmwareVersion(int firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }
}
