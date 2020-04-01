package com.example.exchange.utils;

import com.example.exchange.model.Ask;
import com.example.exchange.model.Bid;

import java.util.List;

import static java.lang.String.format;

public class ResultFormatter {
    private static final String SPLIT_REGEX = ",";
    private static final String FORMAT = "%s%n";
    private Ask bestAsk;
    private Bid bestBid;
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
        for (String s : dataFromFile) {
            if (s.charAt(0) == 'u') {
                setBestOffer(s);
            } else if (s.charAt(0) == 'q') {
                sb.append(format(FORMAT, printResult(s)));
            } else {
                sb.append("o\n");
            }
        }

        return sb.toString();
    }

    private void setBestOffer(String data) {
        String[] offer = data.split(SPLIT_REGEX);
        System.out.println("Lenght" + offer.length);
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

    private String printResult(String data) {
        String result;
        if (data.equals(BEST_BID)) {
            result = bestBid.toString();
        } else {
            result = bestAsk.toString();
        }
        return result;
    }
}
