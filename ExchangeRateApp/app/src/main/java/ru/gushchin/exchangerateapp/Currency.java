package ru.gushchin.exchangerateapp;

class Currency {
    String code;
    String name;
    String  number;
    String price;
    String moving;

    public Currency(String code, String name, String number, String price, String moving) {
        this.code = code;
        this.name = name;
        this.number = number;
        this.price = price;
        this.moving = moving;
    }

}
