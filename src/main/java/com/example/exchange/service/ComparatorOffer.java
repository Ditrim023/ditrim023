package com.example.exchange.service;

import com.example.exchange.model.BestRequest;
import com.example.exchange.model.RequestType;

import java.util.Map;

public class ComparatorOffer {
   BestRequest maxBid(BestRequest newBid, Map<RequestType, BestRequest> bestBidMap) {
      BestRequest result;
      if (bestBidMap.size() == 0) {
         result = newBid;
      } else {
         BestRequest bestBid = bestBidMap.get(RequestType.BID);
         result = bestBid.getPrice() > newBid.getPrice() ? bestBid : newBid;
      }
      return result;
   }

   BestRequest minAsk(BestRequest newAsk, Map<RequestType, BestRequest> bestBidMap) {
      BestRequest result;
      if (bestBidMap.size() == 0) {
         result = newAsk;
      } else {
         BestRequest bestAsk = bestBidMap.get(RequestType.ASK);
         result = bestAsk.getPrice() < newAsk.getPrice() ? bestAsk : newAsk;
      }
      return result;
   }
}
