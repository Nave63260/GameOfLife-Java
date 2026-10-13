package Board;

public class Square {
    private int isOn;

    public Square() {
        this.isOn = 0;
    }

    @Override
    public String toString() {
        if (isOn == 1) {
            return "⬛";
        } else {
            return "⬜";
        }
    }

    public int getIsOn() {
        return isOn;
    }

    public void setIsOn(int isOn) {
        this.isOn = isOn;
    }

    public void invert() {
        if (isOn == 1) {
            isOn = 0;
        } else if (isOn == 0) {
            isOn = 1;
        }
    }
}
