package apm.muei.distancenevermatters.entities;

import java.util.ArrayList;
import java.util.List;

import apm.muei.distancenevermatters.R;

public class DiceContainer {
    private static DiceContainer instance;
    private List<Dice> diceList;

    public DiceContainer(){
        diceList = new ArrayList<>();
        diceList.add(new Dice(1L,"D4", 4, R.mipmap.d4));
        diceList.add(new Dice(2L,"D6", 6, R.mipmap.d6));
        diceList.add(new Dice(3L,"D8", 8, R.mipmap.d8));
        diceList.add(new Dice(4L,"D10", 10, R.mipmap.d10));
        diceList.add(new Dice(5L,"D12", 12, R.mipmap.d12));
        diceList.add(new Dice(6L, "D20", 20, R.mipmap.d20));
    }

    public static List<Dice> getDiceList() {
        if (instance == null) {
            instance = new DiceContainer();
        }
        return instance.diceList;
    }
}