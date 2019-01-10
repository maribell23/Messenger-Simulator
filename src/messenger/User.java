/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenger;

/**
 *
 * @author Bella
 */
public class User {

    private int id;
    private String username;
    private String password;
    private String fName;
    private String lName;
    private int role_id;
    //private static final String roleSA = "sa";

    public User() {
    }

    public User(String username, String password, String fName, String lName, int role_id) {
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.role_id = role_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + username + password + fName + lName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

}
