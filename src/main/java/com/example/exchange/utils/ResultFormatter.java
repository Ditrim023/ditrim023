package com.example.exchange.utils;

import com.example.exchange.exeptions.RequestNotFoundException;
import com.example.exchange.exeptions.WrongFileFormatException;
import com.example.exchange.model.Ask;
import com.example.exchange.model.Bid;
import com.example.exchange.model.Spread;

import java.util.List;

import static java.lang.String.format;

public class ResultFormatter {
   private static final String SPLIT_REGEX = ",";
   private static final String FORMAT = "%s%n";

   private Ask bestAsk;
   private Bid bestBid;
   private Spread spread;

   private ComparatorOffer comparatorOffer = new ComparatorOffer();
   private static final String BID = "bid";
   private static final String ASK = "ask";
   private static final String BEST_BID = "q,best_bid";
   private static final String BEST_ASK = "q,best_ask";
   private static final String SIZE_REQUEST = "q,size,%s%n";
   private static final String DELETE_BUY = "o,buy,%s%n";
   private static final String DELETE_SELL = "o,buy,%s%n";


   public String construct(List<String> dataFromFile) {
      StringBuilder sb = new StringBuilder();
      for (String data : dataFromFile) {
         char firstQueryLetter = data.charAt(0);
         switch (firstQueryLetter) {
            case 'u':
               setBestOffer(data);
               break;
            case 'q':
               sb.append(format(FORMAT, queryRequest(data)));
               break;
            case 'o':
               deleteCountInRequest(data);
               break;
            default:
               throw new WrongFileFormatException();
         }
      }
      return sb.toString();
   }

   private void setBestOffer(String lineFromFile) {
      String[] offer = lineFromFile.split(SPLIT_REGEX);
      int newPrice = Integer.parseInt(offer[1]);
      int newCount = Integer.parseInt(offer[2]);
      String offerType = offer[3];
      if (offerType.equals(BID)) {
         Bid newBid = new Bid(newPrice, newCount);
         bestBid = comparatorOffer.maxBid(bestBid, newBid);
      } else {
         Ask newAsk = new Ask(newPrice, newCount);
         bestAsk = comparatorOffer.minAsk(bestAsk, newAsk);
      }
   }

   private String queryRequest(String lineFromFile) {
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
      String result;
      String[] splitLine = lineFromFile.split(SPLIT_REGEX);
      int price = Integer.parseInt(splitLine[3]);
      if (price == bestBid.getPrice()) {
         result = bestBid.toString();
      } else if (lineFromFile.equals(BEST_ASK)) {
         result = bestAsk.toString();
      } else {
         result = queryRequestByCount(lineFromFile);
      }
      return result;
   }

   private void deleteCountInRequest(String lineFromFile) {
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
