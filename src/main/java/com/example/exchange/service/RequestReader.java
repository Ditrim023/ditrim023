package com.example.exchange.service;

import com.example.exchange.exeptions.RequestNotFoundException;
import com.example.exchange.model.Ask;
import com.example.exchange.model.Bid;
import com.example.exchange.model.Spread;

class RequestReader {
   private static final String SPLIT_REGEX = ",";
   private static final String BID = "bid";
   private static final String BEST_BID = "q,best_bid";
   private static final String BEST_ASK = "q,best_ask";
   private ComparatorOffer comparatorOffer = new ComparatorOffer();
   private Ask bestAsk;
   private Bid bestBid;
   private Spread spread;

   void setBestOffer(String lineFromFile) {
      String[] offer = lineFromFile.split(SPLIT_REGEX);
      int price = Integer.parseInt(offer[1]);
      int count = Integer.parseInt(offer[2]);
      String offerType = offer[3];
      if (count == 0) {
         spread = new Spread(price, count);
      } else if (offerType.equals(BID)) {
         Bid newBid = new Bid(price, count);
         bestBid = comparatorOffer.maxBid(bestBid, newBid);
      } else {
         Ask newAsk = new Ask(price, count);
         bestAsk = comparatorOffer.minAsk(bestAsk, newAsk);
      }
   }

   String queryRequest(String lineFromFile) {
      String result;
      if (lineFromFile.equals(BEST_BID)) {
         result = bestBid.toString();
      } else if (lineFromFile.equals(BEST_ASK)) {
         result = bestAsk.toString();
      } else {
         result = queryRequestByCount(lineFromFile);
      }
      return result;
   }

   private String queryRequestByCount(String lineFromFile) {
      String result = null;
      String[] splitLine = lineFromFile.split(SPLIT_REGEX);
      int price = Integer.parseInt(splitLine[2]);
      try {
         if (price == bestBid.getPrice()) {
            result = String.valueOf(bestBid.getCount());
         } else if (price == bestAsk.getPrice()) {
            result = String.valueOf(bestAsk.getCount());
         } else if (price == spread.getPrice()) {
            result = "0";
         }
      } catch (NullPointerException e) {
         throw new RequestNotFoundException();
      }

      return result;
   }

   void deleteCountInRequest(String lineFromFile) {
      String[] splitLine = lineFromFile.split(SPLIT_REGEX);
      String index = splitLine[1];
      int count = Integer.parseInt(splitLine[2]);
      if (index.equals("sell")) {
         bestBid.setCount(bestBid.getCount() - count);
      } else if (index.equals("buy")) {
         bestAsk.setCount(bestAsk.getCount() - count);
      } else {
         throw new RequestNotFoundException();
      }
   }
}
