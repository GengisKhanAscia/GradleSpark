package com.mycompany.user;

import java.sql.*;
import java.util.ArrayList;

public class DBConnection {
    private static Connection myConnection;

    public DBConnection() throws SQLException {
        myConnection = DriverManager.getConnection("jdbc:mysql://127.0.0:3306/test", "root", "12345678");   // Stringa connessione (URL) - Utente - pwd utente
    }

    // Estraggo utente dal DB tramite id
    public User getUserByID(String id) throws SQLException{
        Statement st = myConnection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM users WHERE id = " + id);
        User user = null;

        if(rs.next()){  // Controllo che il ResultSet abbia esattamente 1 risultato (infatti uso if e non while)
            user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"));    // Estraggo colonne tramite label
        }

        st.close();
        return user;
    }

    // Aggiungo utente al DB
    public User addUser(String name, String email) throws SQLException {    // Non si passa l'id perche' c'e 'auto-increment' nel DB. Si specifica la colonna di inserimento.
        Statement st = myConnection.createStatement();
        st.executeUpdate("INSERT INTO users (name, email) VALUES ('"+ name + "', '"+ email + "')");
        ResultSet rs = st.executeQuery("SELECT * FROM user WHERE id = (SELECT max(Id) FROM users)");
        User user = null;

        if(rs.next()){
            user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
        }

        st.close();
        return user;
    }

    // Aggiornamento utente nel DB
    public void updateUser (String Id, String name, String email) throws SQLException{
        User user = getUserByID(Id);
        if(user != null){
            user.setName(name);
            user.setEmail(email);
        }

        Statement st = myConnection.createStatement();
        st.executeUpdate("UPDATE user SET name = " + user.getName() + ", email = " + user.getEmail() + " WHERE Id = " + user.getId());
        st.close();
    }

    // Cancellazione utente nel DB
    public void deleteUser(String Id) throws SQLException{
        Statement st = myConnection.createStatement();
        st.executeUpdate("DELETE FROM user WHERE Id = " + Id);
        st.close();
    }

    // Selezione di tutti gli utenti
    public ArrayList getAllUsers() throws SQLException{
        ArrayList _users = new ArrayList();
        Statement st = myConnection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM users");
        while(rs.next()){
            _users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
        }
        st.close();
        return _users;
    }
}
