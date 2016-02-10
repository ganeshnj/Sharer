package com.ganesh.sharer.models;

/**
 * Created by Ganesh-XPS13 on 2/10/2016.
 */
public class Currency {
    private String symbol;
    private String desc;

    public Currency(String symbol, String desc) {
        this.symbol = symbol;
        this.desc = desc;
    }


    public String getSymbol() {
        return this.symbol;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
