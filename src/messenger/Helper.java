package messenger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Bella
 */
public class Helper {

    public static boolean containsOnlyNumbers(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean requestConfirmation() {
        String in = "";
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Confirm Operation (y/n)... ");
            in = sc.nextLine();
            if (in.equals("y") || in.equals("yes")) {
                return true;
            } else if (in.equals("n") || in.equals("no")) {
                return false;
            }
        }
    }

    public static int checkRoleId(String role_id) {
        Scanner sc = new Scanner(System.in);
        int new_role_id = 0;
        while (!(role_id.equals("1") || role_id.equals("2") || role_id.equals("3") || role_id.equals("4") || role_id.equals("5"))) {
            System.out.println("Invalid role id. Give again:");
            role_id = sc.nextLine();
        }
        return new_role_id = Integer.parseInt(role_id);
    }

    public static String ckeckString(String str) {
        Scanner sc = new Scanner(System.in);
        while (str.isEmpty()) {
            System.out.println("Field cannot be empty! Give again:");
            str = sc.nextLine();
        }
        return str;
    }

    public static void closeConnection() {
        Database db = new Database();
        Connection c = db.connect();
        try {
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
