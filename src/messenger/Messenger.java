/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bella
 */
public class Messenger {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
    private static User maria = null;
    private static Database db = new Database();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Login lg = new Login();
        maria = lg.userDetails(db);
        MenuMain menu = new MenuMain();
        menu.mainMenu(maria);
    }
}
