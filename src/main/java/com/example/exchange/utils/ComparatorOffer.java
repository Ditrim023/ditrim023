package com.example.exchange.utils;

import com.example.exchange.model.Ask;
import com.example.exchange.model.Bid;

class ComparatorOffer {
    Bid maxBid(Bid bestBid, Bid newBid) {
        Bid result;
        if (bestBid == null) {
            result = newBid;
        } else {
            result = bestBid.getPrice() > newBid.getPrice() ? bestBid : newBid;
        }
        return result;
    }

    Ask minAsk(Ask bestAsk, Ask newAsk) {
        Ask result;
        if (bestAsk == null) {
            result = newAsk;
        } else {
            result = bestAsk.getPrice() > newAsk.getPrice() ? newAsk : bestAsk;
        }
        return result;
    }
}
