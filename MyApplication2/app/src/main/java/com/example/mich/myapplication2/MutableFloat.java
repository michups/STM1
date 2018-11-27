package com.example.mich.myapplication2;

public class MutableFloat {

    private Float value;

    public MutableFloat(Float value) {
        this.value = value;
    }

    public MutableFloat( ) {
        this.value = 0f;
    }
    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MutableFloat{" +
                "value=" + value +
                '}';
    }
}
