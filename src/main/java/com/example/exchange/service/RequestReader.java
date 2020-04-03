package com.example.exchange.service;

import com.example.exchange.exeptions.RequestNotFoundException;
import com.example.exchange.model.BestRequest;
import com.example.exchange.model.RequestType;

import java.util.EnumMap;
import java.util.Map;

class RequestReader {
   private static final String SPLIT_REGEX = ",";
   private static final String BID = "bid";
   private static final String BEST_BID = "q,best_bid";
   private static final String BEST_ASK = "q,best_ask";
   private static final String SELL = "sell";
   private ComparatorOffer comparatorOffer = new ComparatorOffer();
   private Map<RequestType, BestRequest> bestBidMap = new EnumMap<>(RequestType.class);
   private Map<RequestType, BestRequest> bestAskMap = new EnumMap<>(RequestType.class);
   private Map<RequestType, BestRequest> spreadMap = new EnumMap<>(RequestType.class);

   void setBestOffer(String lineFromFile) {
      String[] offer = lineFromFile.split(SPLIT_REGEX);
      int price = Integer.parseInt(offer[1]);
      int count = Integer.parseInt(offer[2]);
      String offerType = offer[3];
      BestRequest newRequest = new BestRequest(price, count);
      if (count == 0) {
         spreadMap.put(RequestType.SPREAD, newRequest);
      } else if (offerType.equals(BID)) {
         BestRequest result = comparatorOffer.maxBid(newRequest, bestBidMap);
         bestBidMap.put(RequestType.BID, result);
      } else {
         BestRequest result = comparatorOffer.minAsk(newRequest, bestAskMap);
         bestAskMap.put(RequestType.BID, result);
      }
   }

   String queryRequest(String lineFromFile) {
      String result;
      try {
         if (lineFromFile.equals(BEST_BID)) {
            result = bestBidMap.get(RequestType.BID).toString();
         } else if (lineFromFile.equals(BEST_ASK)) {
            result = bestAskMap.get(RequestType.ASK).toString();
         } else {
            result = queryRequestByCount(lineFromFile);
         }
      } catch (NullPointerException e) {
         throw new RequestNotFoundException(lineFromFile);
      }
      return result;
   }

   private String queryRequestByCount(String lineFromFile) {
      String result = null;
      String[] splitLine = lineFromFile.split(SPLIT_REGEX);
      int price = Integer.parseInt(splitLine[2]);
      try {
         if (price == bestBidMap.get(RequestType.BID).getPrice()) {
            result = String.valueOf(bestBidMap.get(RequestType.BID).getCount());
         } else if (price == bestAskMap.get(RequestType.ASK).getPrice()) {
            result = String.valueOf(bestAskMap.get(RequestType.ASK).getPrice());
         } else if (price == spreadMap.get(RequestType.SPREAD).getPrice()) {
            result = "0";
         }
      } catch (NullPointerException e) {
         throw new RequestNotFoundException(lineFromFile);
      }
      return result;
   }

   void deleteCountInRequest(String lineFromFile) {
      String[] splitLine = lineFromFile.split(SPLIT_REGEX);
      String index = splitLine[1];
      int count = Integer.parseInt(splitLine[2]);
      if (index.equals(SELL)) {
         BestRequest bestRequest = bestBidMap.get(RequestType.BID);
         bestRequest.setCount(bestRequest.getCount() - count);
         bestBidMap.put(RequestType.BID, bestRequest);
      } else {
         BestRequest bestRequest = bestAskMap.get(RequestType.ASK);
         bestRequest.setCount(bestRequest.getCount() - count);
         bestAskMap.put(RequestType.ASK, bestRequest);
      }
   }
}
