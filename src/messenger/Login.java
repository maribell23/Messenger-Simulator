/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenger;

import java.util.Scanner;

/**
 *
 * @author Bella
 */
public class Login {
    private String username;
    private String password;
    
    public User userDetails(Database db){
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter username: ");
        String u = sc.nextLine();
        u = Helper.ckeckString(u); 
        System.out.println("Enter password: ");
        String p = sc.nextLine();
        p = Helper.ckeckString(p);
        User user = new User();
        user = db.getUserDetailsFromDB(u, p);
       return user;
        
    }
    
    
}
