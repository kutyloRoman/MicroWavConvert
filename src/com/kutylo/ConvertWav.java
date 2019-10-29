package com.kutylo;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConvertWav {

//    public static void convertToBinary() {
//       try{
//        File inputfile = new File("wave.wav");
//       byte[] data = new byte[(int)inputfile.length()];
//       FileInputStream in = new FileInputStream(inputfile);
//       in.read(data);
//       in.close();
//
//       //encrypt data
//
//       FileOutputStream out = new FileOutputStream(new File("converted.txt"));
//       out.write(data);
//       out.close();
//       }
//       catch(Exception e){
//           e.printStackTrace();
//       }
//   }
    
     public static Boolean convertToBinary() {
       File inputFile;
       byte[] data = null;
         try {
              inputFile = new File("D://Project/Labs/mzkit/mzkit_lab_3/record.wav");
              data = Files.readAllBytes(Paths.get("D://Project/Labs/mzkit/mzkit_lab_3/record.wav"));

              FileOutputStream out = new FileOutputStream(new File("D://Project/Labs/mzkit/mzkit_lab_3/converted.txt"));
              for (byte b : data) {
              out.write(Integer.toBinaryString(b).getBytes());
}
            out.close();
        return true;
              
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
      
   }

}
    

