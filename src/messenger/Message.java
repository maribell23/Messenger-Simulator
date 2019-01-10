/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 *
 * @author Bella
 */
public class Message {

    private int id;
    private String message;
    private Integer userIDFrom;
    private Integer userIDTo;
    private Timestamp date;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

    public Message() {

    }

    public Message(Integer userIDFrom, Integer userIDTo, String message, Timestamp date) {
        this.message = message;
        this.userIDFrom = userIDFrom;
        this.userIDTo = userIDTo;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        while (message.length() > 250) {
            System.out.println("To minima prepei na einai ligotero apo 250 xaraktires. Ksanadwste to minima: ");
            Scanner sc = new Scanner(System.in);
            message = sc.nextLine();
        }
        this.message = message;
    }

    public Integer getUserIDFrom() {
        return userIDFrom;
    }

    public void setUserIDFrom(Integer userIDFrom) {
        this.userIDFrom = userIDFrom;
    }

    public Integer getUserIDTo() {
        return userIDTo;
    }

    public void setUserIDTo(Integer userIDTo) {
        this.userIDTo = userIDTo;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return " Message ID: " + this.id + "\n"
            + " From User : " + this.userIDFrom + "\n"
            + " To   User : " + this.userIDTo + "\n"
            + " Date      : " + sdf.format(this.date) + "\n"
            + " Message   : " + this.message + "\n";

    }
}
