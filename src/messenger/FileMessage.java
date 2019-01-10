/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bella
 */
public class FileMessage {

    public void createFile(Message m, String date) {
        String filename = date + ".txt";
        File f = new File(filename);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (FileWriter fw = new FileWriter(filename, true);
                BufferedWriter bf = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bf)) {
                pw.print(m);
                pw.println();
            } catch (IOException ex) {
                Logger.getLogger(FileMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try (FileWriter fw = new FileWriter(filename, true);
                BufferedWriter bf = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bf)) {
                pw.print(m);
                pw.println();
            } catch (IOException ex) {
                Logger.getLogger(FileMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
