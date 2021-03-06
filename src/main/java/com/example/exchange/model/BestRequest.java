package com.example.exchange.model;

public class BestRequest {
   private int price;
   private int count;

   public BestRequest() {
   }

   public BestRequest(int price, int count) {
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
}
