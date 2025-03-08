package test.gas;

public class GasTank implements IGasStorage {
    private int gas = 0;
    private int maxGas = 0;

    public GasTank() {}

    @Override
    public void setGas(int gas) {
        this.gas = gas;
    }

    @Override
    public int getGas() {
        return gas;
    }

    @Override
    public void setMaxGas(int maxGas) {
        this.maxGas = maxGas;
    }

    @Override
    public int getMaxGas() {
        return maxGas;
    }

}
