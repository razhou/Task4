package com.example.ghost.task4;

/**
 * Created by ghost on 10/03/2016.
 */
public class listExp {

    String descript;
    int amtExp;

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public int getAmtExp() {
        return amtExp;
    }

    public void setAmtExp(int amtExp) {
        this.amtExp = amtExp;
    }



    public listExp(String descript, int amtExp) {
        this.descript = descript;
        this.amtExp = amtExp;
    }
}
