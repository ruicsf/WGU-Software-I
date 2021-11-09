package Model;

import Model.Part;

/**
 * Class for  In House parts*/
public class InHouse extends Part {
    private int machineID;

    /**Constructor for InHouse class
     * @param machineID In House part ID*/
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {

        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }
    /**Machine ID getter*/
    public int getMachineID() {
        return machineID;
    }
    /**Machine ID setter*/
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
