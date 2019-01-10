/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bella
 */
public class Database {

    private static final String DB_URL = "localhost:3306";
    private static final String FULL_DB_URL = "jdbc:mysql://" + DB_URL + "/messenger?zeroDateTimeBehavior=EXCEPTION&useSSL=false&allowPublicKeyRetrieval=true &serverTimezone=UTC";
    private static final String DB_USER = "user1";
    private static final String DB_PASSWD = "asdfasdf!@_MB";
    private static final SimpleDateFormat dateForInsert = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public boolean test() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM messenger.messages;");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + "\t" + resultSet.getString(2) + "\t" + resultSet.getString(3)); //+ "\t" //+ resultSet.getString(4) + "\t" + resultSet.getString(5));
            }
        } catch (SQLException ex) {
            System.out.println("Sorry, problems with the database connection!");
            System.out.println(ex.toString());
            System.exit(0);
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }
    }

    public Connection connect() {
        Connection c = null;

        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            c = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWD);
        } catch (SQLException ex) {
            System.out.println("Connection problems");
        }
        return c;
    }

    public void insertMessage(Message m) throws SQLException {
        Connection c = connect();
        int m_rows = 0;
        int mu_rows = 0;
        int msg_id = 0;
        String date = dateForInsert.format(m.getDate());
        String sql = new StringBuilder()
            .append("INSERT INTO `messenger`.`messages` (`message`, `date`) ")
            .append("VALUES (?, ?);").toString();
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setString(1, m.getMessage());
            statement.setString(2, date);
            m_rows = statement.executeUpdate();
        }
        String aS = m.getDate().toString().substring(0, 19);
        String dateToFile = aS.substring(0, 10);
        sql = "SELECT id FROM messages WHERE date LIKE '" + aS + "%';";
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(sql);
        rs.first();
        msg_id = rs.getInt(1);

        sql = new StringBuilder()
            .append("INSERT INTO `messenger`.`message_user` (`id_user_from`, `id_user_to`, `id_message`) ")
            .append("VALUES (?, ?, ?);").toString();
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setString(1, m.getUserIDFrom().toString());
            statement.setString(2, m.getUserIDTo().toString());
            statement.setString(3, rs.getString(1));
            mu_rows = statement.executeUpdate();
        }
        m.setId(msg_id);
        FileMessage f = new FileMessage();
        f.createFile(m, dateToFile);
        createFileToDb(dateToFile);
        c.close();
    }

    public void getMessage(User user) {
        Message m = null;
        Connection c = connect();

        String sql = "SELECT message_user.id_user_from, message_user.id_user_to, messages.id, messages.date, messages.message \n"
            + "FROM message_user, messages \n"
            + "\n"
            + "WHERE (message_user.id_user_from = " + user.getId() + " AND (message_user.id_message = messages.id)) \n"
            + "OR \n"
            + "(message_user.id_user_to = " + user.getId() + " AND (message_user.id_message = messages.id))";

        try {
            Statement s1 = c.createStatement();
            ResultSet rs_msg = s1.executeQuery(sql);
            System.out.println("ID_From ID_To ID_Message \tDate \t\tMessage");
            while (rs_msg.next()) {
                System.out.println(rs_msg.getString(1) + "\t" + rs_msg.getString(2) + "\t" + rs_msg.getString(3) + "\t" + rs_msg.getString(4) + "\t" + rs_msg.getString(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getAllUsersMessage() {
        Connection c = connect();
        String sql = "SELECT message_user.id_user_from, message_user.id_user_to, messages.id, messages.date, messages.message \n"
            + "FROM message_user, messages \n"
            + "\n"
            + "WHERE message_user.id_message = messages.id";
        try {
            Statement s1 = c.createStatement();
            ResultSet rs_msg = s1.executeQuery(sql);
            System.out.println("ID_From ID_To ID_Message \tDate \t\tMessage");
            while (rs_msg.next()) {

                System.out.println(rs_msg.getString(1) + "\t" + rs_msg.getString(2) + "\t" + rs_msg.getString(3) + "\t" + rs_msg.getString(4) + "\t" + rs_msg.getString(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getIdUserMessage(User user) {
        Connection c = connect();

        String sql = "SELECT messages.id from messages,message_user where (message_user.id_user_from = '" + user.getId() + "' \n"
            + "OR message_user.id_user_to = '" + user.getId() + "') AND message_user.id_message = messages.id";

        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int checkIdMessage() {
        Scanner sc = new Scanner(System.in);
        Connection c = connect();

        getAllUsersMessage();
        System.out.println("\nGive id_message: ");
        String new_id_msg = sc.nextLine();
        new_id_msg = Helper.ckeckString(new_id_msg);
        while (Helper.containsOnlyNumbers(new_id_msg) == false) {
            System.out.println("\nId_message must contains only numbers. Please give another id: ");
            new_id_msg = sc.nextLine();
            new_id_msg = Helper.ckeckString(new_id_msg);
        }
        int id = Integer.parseInt(new_id_msg);
        String sql = "select id from messages";

        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                if (id == rs.getInt(1)) {
                    return id;
                }
            }
            System.out.println("\nThis id doesn't exist. \n");
            id = checkIdMessage();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Helper.closeConnection();
        }
        return id;
    }

    public int checkIdUserMessage(User user) {
        Connection c = connect();
        Scanner sc = new Scanner(System.in);

        getMessage(user);
        System.out.println("\nGive id_message: ");
        String new_id_msg = sc.nextLine();
        new_id_msg = Helper.ckeckString(new_id_msg);
        while (Helper.containsOnlyNumbers(new_id_msg) == false) {
            System.out.println("\nId_message must contains only numbers. Please give another id: ");
            new_id_msg = sc.nextLine();
            new_id_msg = Helper.ckeckString(new_id_msg);
        }
        int id = Integer.parseInt(new_id_msg);
        String sql = "SELECT messages.id from messages,message_user where (message_user.id_user_from = '" + user.getId() + "' \n"
            + "AND message_user.id_message = '" + id + "')  \n"
            + "OR (message_user.id_user_to = '" + user.getId() + "' AND message_user.id_message = '" + id + "')";

        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                if (id == rs.getInt(1)) {
                    return id;
                }
            }
            System.out.println("\nThis id doesn't exist. \n");
            id = checkIdUserMessage(user);

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return id;

    }

    public int checkIdUser() {
        Connection c = connect();
        Scanner sc = new Scanner(System.in);

        getUsers();
        System.out.println("\nGive id user: ");
        String user_id = sc.nextLine();
        user_id = Helper.ckeckString(user_id);
        while (Helper.containsOnlyNumbers(user_id) == false) {
            System.out.println("\nId_user must contains only numbers.Please give another id: ");
            user_id = sc.nextLine();
            user_id = Helper.ckeckString(user_id);
        }
        int new_u_id = Integer.parseInt(user_id);
        String sql = "SELECT id from users";

        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                if (new_u_id == rs.getInt(1)) {
                    return new_u_id;

                }
            }
            System.out.println("\nThis id doesnt exist. \n");
            new_u_id = checkIdUser();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Helper.closeConnection();
            return new_u_id;
        }
    }

    public void deleteMessage() {
        Connection c = connect();
        int i = 0;
        int id = checkIdMessage();
        boolean confirm = Helper.requestConfirmation();
        if (!confirm) {
            System.out.println("\nOption cancelled!");
            return;
        }
        String sql = "DELETE FROM message_user WHERE id_message = '" + id + "'";
        try {
            Statement stmt = c.createStatement();
            i = stmt.executeUpdate(sql);

            String sql2 = "SELECT * FROM message_user WHERE id_message = '" + id + "'";
            ResultSet resultset = stmt.executeQuery(sql2);
            if (!resultset.first()) {
                sql = "DELETE FROM messages WHERE id = '" + id + "'";
                i = stmt.executeUpdate(sql);
                System.out.println(i + " message was deleted. \n");

            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Helper.closeConnection();
        }
    }

    public void deleteUserMessage(User user) {
        Connection c = connect();
        int id = checkIdUserMessage(user);
        boolean confirm = Helper.requestConfirmation();
        if (!confirm) {
            System.out.println("\nOption cancelled!");
            return;
        }
        String sql = "delete from message_user where id_message = '" + id + "'";
        try {
            Statement s = c.createStatement();
            int i = s.executeUpdate(sql);
            String sql1 = "SELECT * FROM message_user WHERE id_message = '" + id + "'";
            ResultSet rs = s.executeQuery(sql1);
            if (!rs.first()) {
                String sql2 = "DELETE FROM messages WHERE id = '" + id + "'";
                i = s.executeUpdate(sql2);
                System.out.println(i + " message was deleted. \n");
                getMessage(user);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Helper.closeConnection();
        }

    }

    public void updateMessage() {
        Connection c = connect();
        Scanner sc = new Scanner(System.in);

        int id = checkIdMessage();
        System.out.println("\nUpdate your message: ");
        String up_msg = sc.nextLine();

        String sql = "UPDATE messages SET messages.message = '" + up_msg + "' WHERE id = '" + id + "'";
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            int rowsAffected = statement.executeUpdate();
            getAllUsersMessage();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Helper.closeConnection();
        }
    }

    public void updateUserMessage(User user) {
        Connection c = connect();
        Scanner sc = new Scanner(System.in);
        int id = checkIdUserMessage(user);
        System.out.println("\nUpdate your message: ");
        String msg = sc.nextLine();
        String sql = "UPDATE messages SET messages.message = '" + msg + "' WHERE id = '" + id + "'";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            int rowsAffected = ps.executeUpdate();
            getMessage(user);

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Helper.closeConnection();
        }

    }

    public void createUser() {
        Connection c = connect();
        Scanner sc = new Scanner(System.in);
        int i = 0;

        System.out.println("Dwse username: ");
        String username = sc.nextLine();
        username = Helper.ckeckString(username);
        System.out.println("Dwse password: ");
        String password = sc.nextLine();
        password = Helper.ckeckString(password);
        System.out.println("Dwse first name: ");
        String fName = sc.nextLine();
        fName = Helper.ckeckString(fName);
        System.out.println("Dwse last name: ");
        String lName = sc.nextLine();
        lName = Helper.ckeckString(lName);
        System.out.println("Dwse role_id");
        String r_id = sc.nextLine();
        r_id = Helper.ckeckString(r_id);
        int new_r_id = Helper.checkRoleId(r_id);
        boolean confirm = Helper.requestConfirmation();
        if (!confirm) {
            System.out.println("\nOption cancelled!");
            return;
        }
        String sql = new StringBuilder()
            .append("INSERT INTO `messenger`.`users` (`username`, `password`, `fName`, `lName`, `role_id`) ")
            .append("VALUES (?, ?, ?, ?, ?);").toString();
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, fName);
            statement.setString(4, lName);
            statement.setInt(5, new_r_id);
            i = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Helper.closeConnection();

        }
    }

    public void deleteUser() {
        Connection c = connect();
        int id = checkIdUser();
        boolean confirm = Helper.requestConfirmation();
        if (!confirm) {
            System.out.println("\nOption cancelled!");
            return;
        }
        String sql = "SELECT id_user_from, id_user_to, id_message from message_user, messages where (id_user_from = '" + id + "' or id_user_to = '" + id + "') and messages.id = message_user.id_message";

        try {
            Statement s = c.createStatement();
            Statement s2 = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                int id_message = rs.getInt(3);
                String sql2 = "delete from message_user where (message_user.id_user_from = '" + id + "'" + "or message_user.id_user_to = '" + id + "') and message_user.id_message = '" + id_message + "'";

                int i = s2.executeUpdate(sql2);
                String sql3 = "delete from messages where messages.id = '" + id_message + "'";
                i = s2.executeUpdate(sql3);

            }
            String sql4 = "delete from users where id = '" + id + "'";
            int j = s2.executeUpdate(sql4);
            System.out.println(j + " user was deleted");

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Helper.closeConnection();

        }

    }

    public void updateUser() {
        Connection c = connect();
        Scanner sc = new Scanner(System.in);
        int new_role_id = 0;
        int id = checkIdUser();

        System.out.println("Update username");
        String new_username = sc.nextLine();
        System.out.println("Update password");
        String new_password = sc.nextLine();
        new_password = Helper.ckeckString(new_password);
        System.out.println("Update fName");
        String fName = sc.nextLine();
        fName = Helper.ckeckString(fName);
        System.out.println("Update lName");
        String lName = sc.nextLine();
        lName = Helper.ckeckString(lName);
        System.out.println("Update role id");
        String role_id = sc.nextLine();
        role_id = Helper.ckeckString(role_id);
        new_role_id = Helper.checkRoleId(role_id);
        boolean confirm = Helper.requestConfirmation();
        if (!confirm) {
            System.out.println("\nOption cancelled!");
            return;
        }

        String sql = "update users set username = '" + new_username + "' , password = '" + new_password + "' , fName = '"
            + fName + "' , lName = '" + lName + "', role_id = '" + new_role_id + "' where id = '" + id + "'";

        try {
            PreparedStatement ps = c.prepareStatement(sql);
            int rowsAffected = ps.executeUpdate();
            getUsers();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Helper.closeConnection();

        }

    }

    public User getUserDetailsFromDB(String username, String password) {
        Connection c = connect();
        User user = null;
        String sql = "SELECT * FROM users";

        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                if (username.equals(rs.getString(2)) && password.equals(rs.getString(3))) {
                    user = new User(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
                    user.setId(rs.getInt(1));
                    return user;
                }
            }
            System.out.println("User not found. Please enter another username and password.");
            Login lg = new Login();
            user = lg.userDetails(this);

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Helper.closeConnection();
        }

        return user;
    }

    public Message prepareMessage(Message m) {
        Scanner sc = new Scanner(System.in);
        Connection c = connect();

        getIDUser();

        System.out.println("\nGive id uer to: ");

        String new_id = sc.nextLine();
        new_id = Helper.ckeckString(new_id);
        while (Helper.containsOnlyNumbers(new_id) == false) {
            System.out.println("\nId_user must contains only numbers. Please give another id: ");
            new_id = sc.nextLine();
            new_id = Helper.ckeckString(new_id);
        }
        int id = Integer.parseInt(new_id);
        String sql = "select id from users";
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                if (rs.getInt(1) == id) {
                    m.setUserIDTo(id);
                }
            }
            if (m.getUserIDTo() == null) {
                System.out.println("\nThis id doesn't exist. \n");
                m = prepareMessage(m);
                return m;
            }
            System.out.println("Type your message: ");
            m.setMessage(sc.nextLine());
            Timestamp t = new Timestamp(System.currentTimeMillis());
            m.setDate(t);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Helper.closeConnection();
        }
        return m;

    }

    public void getUsers() {
        Connection c = connect();
        String sql = "SELECT * from users";
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            System.out.println("ID \tusername \tpassword \tfName \tlName \trole_id");

            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6));

            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Helper.closeConnection();
        }
    }

    public void getIDUser() {
        Connection c = connect();
        String sql = "SELECT id,username from users";

        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            System.out.println("ID \tusername \t");

            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Helper.closeConnection();
        }

    }

    public void getIDMessage() {
        Connection c = connect();
        String sql = "SELECT id, message from messages";

        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            System.out.println("ID \tmessage \t");

            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Helper.closeConnection();
        }
    }

    public void createFileToDb(String date) {
        Connection c = connect();
        String filename = date + ".txt";

        try {
            File f = new File("C:\\Users\\Bella\\Documents\\NetBeansProjects\\Messenger\\" + filename);
            FileInputStream inputStream = new FileInputStream(f);
            String sql = "insert into files (name,txt_file) values (?,?)";

            Statement st = c.createStatement();
            PreparedStatement ps = c.prepareStatement(sql);
            String sql1 = "select * from files where name = '" + filename + "'";
            ResultSet rs = st.executeQuery(sql1);
            while (rs.next()) {
                if (filename.equals(rs.getString(1))) {
                    String sql2 = "delete from files where name = '" + filename + "'";
                    int i = st.executeUpdate(sql2);
                    ps.setString(1, filename);
                    ps.setBinaryStream(2, inputStream);
                    ps.execute();
                    System.out.println("File stored in database!");
                    return;
                }
            }
            ps.setString(1, f.getName());
            ps.setBinaryStream(2, inputStream);
            ps.executeUpdate();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
