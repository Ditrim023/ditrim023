package com.example.exchange;

import com.example.exchange.model.BestRequest;
import com.example.exchange.model.RequestType;
import com.example.exchange.service.ComparatorOffer;
import com.example.exchange.service.ResultFormatter;
import com.example.exchange.utils.FileReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Launcher {
   private static final String FILE_IN = "src/main/resources/in.txt";
   private static final String FILE_OUT = "src/main/resources/out.txt";
   private static final String FILE_CANNOT_READ_MESSAGE = "Something wrong with this file";
   private FileReader fileReader = new FileReader();
   private ResultFormatter resultFormatter = new ResultFormatter();
   private Map<RequestType, BestRequest> bestBid = new HashMap<>();
   private ComparatorOffer comparatorOffer = new ComparatorOffer();

   void run() {
      try {
         List<String> fromIn = fileReader.read(FILE_IN);
         String result = resultFormatter.construct(fromIn);
         printMessage(result);
         fileReader.write(FILE_OUT, result);
      } catch (IOException e) {
         printMessage(FILE_CANNOT_READ_MESSAGE);
      }

   }

   private void printMessage(String message) {
      System.out.println(message);
   }
}
