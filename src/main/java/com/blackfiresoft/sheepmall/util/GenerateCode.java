package com.blackfiresoft.sheepmall.util;

import java.util.Random;

public class GenerateCode {

   public static String generateCode() {
       Random random = new Random();
       StringBuilder sb = new StringBuilder();
       for (int i = 0; i < 5; i++) {
           sb.append(random.nextInt(10));
       }
       return sb.toString();
   }
}
