package com.example.exchange.model;

import java.util.Objects;

public class Ask {
    private int price;
    private int count;

    public Ask() {
    }

    public Ask(int price, int count) {
        this.price = price;
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return price +
                "," + count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ask ask = (Ask) o;
        return price == ask.price &&
                count == ask.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, count);
    }
}
