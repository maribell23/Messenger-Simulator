/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenger;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bella
 */
public class MenuMain {

    private User temp = null;
    private Menu menu = new Menu();
    private Database db = new Database();

    public void mainMenu(User user) {
        temp = user;
        Menu menu = new Menu();
        menu.setTitle("Welcome");

        menu.addItem(new MenuItem("Send message", this, "sendMessage"));
        switch (temp.getRole_id()) {
            case 1:
                menu.addItem(new MenuItem("All Messages", this, "menuu"));
                menu.addItem(new MenuItem("My Messages", this, "menu1"));
                menu.addItem(new MenuItem("Manage Users", this, "menu2"));
                break;
            case 2:
                menu.addItem(new MenuItem("All Messages", this, "menuu"));
                break;
            case 3:
                menu.addItem(new MenuItem("View all messages", this, "viewAllMessages"));
                menu.addItem(new MenuItem("Edit from all messages", this, "updateMessage"));
                break;
            case 4:
                menu.addItem(new MenuItem("View all messages", this, "viewAllMessages"));
                break;
            case 5:
                menu.addItem(new MenuItem("My messages", this, "menu1"));
                break;
        }
        menu.execute();
    }

    public void menuu() {
        Menu menuu = new Menu();
        menuu.setTitle("All Messages");
        menuu.addItem(new MenuItem("View all messages", this, "viewAllMessages"));
        menuu.addItem(new MenuItem("Edit from all messages", this, "updateMessage"));
        menuu.addItem(new MenuItem("Delete from all messages", this, "deleteMessage"));
        menuu.execute();
    }

    public void menu1() {
        Menu menu1 = new Menu();
        menu1.setTitle("My messages");
        menu1.addItem(new MenuItem("View my message", this, "viewMessage"));
        menu1.addItem(new MenuItem("Edit my message", this, "updateMyMessage"));
        menu1.addItem(new MenuItem("Delete my message", this, "deleteMyMessage"));
        menu1.execute();
    }

    public void menu2() {
        Menu menu2 = new Menu();
        menu2.setTitle("Manage Users");
        menu2.addItem(new MenuItem("Create User", this, "createUser"));
        menu2.addItem(new MenuItem("View Users", this, "getUsers"));
        menu2.addItem(new MenuItem("Update User", this, "updateUser"));
        menu2.addItem(new MenuItem("Delete User", this, "deleteUser"));
        menu2.execute();
    }

    public void sendMessage() {
        Message m = new Message();
        Timestamp t = new Timestamp(System.currentTimeMillis());
        m.setUserIDFrom(temp.getId());
        //prepare message
        m = db.prepareMessage(m);
        m.setDate(t);

        //insert message
        try {
            db.insertMessage(m);

        } catch (SQLException ex) {
            Logger.getLogger(MenuMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(m);
    }

    public void viewAllMessages() {
        db.getAllUsersMessage();
    }

    public void deleteMessage() {
        db.deleteMessage();
    }

    public void updateMessage() {
        db.updateMessage();
    }

    public void createUser() {
        db.createUser();
    }

    public void getUsers() {
        db.getUsers();
    }

    public void updateUser() {
        db.updateUser();
    }

    public void deleteUser() {
        db.deleteUser();
    }

    public void viewMessage() {
        db.getMessage(temp);
    }

    public void updateMyMessage() {
        db.updateUserMessage(temp);
    }

    public void deleteMyMessage() {
        db.deleteUserMessage(temp);
    }

}
