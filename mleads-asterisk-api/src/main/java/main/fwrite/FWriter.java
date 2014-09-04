/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.fwrite;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vpryimak
 */
public class FWriter {
    
    public static Boolean getWriter(String file, String text){
        try {
            char[] c= text.toCharArray();
            
            FileWriter fw = new FileWriter(file);
            
            fw.write(c);
            fw.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(FWriter.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
