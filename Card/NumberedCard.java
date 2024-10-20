package Card;

public class NumberedCard extends Card {
    private String number;


    public NumberedCard(String color, String number) {
        super(color);
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return this.getColor()+" "+this.number+" card";
    }
}
