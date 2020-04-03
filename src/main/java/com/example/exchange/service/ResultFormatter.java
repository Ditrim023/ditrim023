package com.example.exchange.service;

import com.example.exchange.exeptions.WrongFileFormatException;

import java.util.List;

import static java.lang.String.format;

public class ResultFormatter {
   private static final String FORMAT = "%s%n";
   private RequestReader requestReader = new RequestReader();

   public String construct(List<String> dataFromFile) {
      StringBuilder sb = new StringBuilder();
      for (String data : dataFromFile) {
         char firstQueryLetter = data.charAt(0);
         switch (firstQueryLetter) {
            case 'u':
               requestReader.setBestOffer(data);
               break;
            case 'q':
               sb.append(format(FORMAT, requestReader.queryRequest(data)));
               break;
            case 'o':
               requestReader.deleteCountInRequest(data);
               break;
            default:
               throw new WrongFileFormatException();
         }
      }
      return sb.toString();
   }


}
