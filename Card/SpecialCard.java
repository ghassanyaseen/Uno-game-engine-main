package Card;

import Effects.Effect;

public class SpecialCard extends Card{
    private Effect effect;
    private boolean isWild;
    private String value;


    public SpecialCard(String color, Effect effect, boolean isWild, String value) {
        super(color);
        this.effect = effect;
        this.isWild = isWild;
        this.value = value;
    }

    public Effect getSpecialEffect(){
        return this.effect;
    }

    public boolean isWild(){
        return this.isWild;
    }

    @Override
    public String toString() {
        return this.getColor()+" "+this.value+" card";
    }
}
