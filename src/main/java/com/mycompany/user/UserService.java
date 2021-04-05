package com.mycompany.user;

import java.util.ArrayList;
// import java.util.HashMap;
import java.util.List;
// import java.util.Map;
// import java.util.concurrent.atomic.AtomicInteger;

public class UserService {

    // public static Map users = new HashMap<>(); // '< >' sono 'Diamond references' - ELIMINATA, sostituita da DB USER
    // private static final AtomicInteger count = new AtomicInteger(0);    // Per avere un incremento automatico e univoco del valore - ELIMINATA, sostituita da auto-increment della colonna 'id' della tabella 'users'

    private static DBConnection db;

    /*
    public User findById(String id){
        return (User) users.get(id);
    }
     */

    public User findById(String id){
        User user = null;
        try{
            user = db.getUserByID(id);
        } catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }

    /*
    public User add(String name, String email){
        int currentId = count.incrementAndGet();
        User user = new User(currentId, name, email);
        users.put(String.valueOf(currentId), user);
        return user;
    }
     */

    public User add(String name, String email){
        User user = null;
        try{
           user = db.addUser(name, email);
        } catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }

    /*
    public User update(String id, String name, String email){
        User user = (User) users.get(id);
        if(user.getName() != null){
            user.setName(name);
        }
        if(user.getEmail() != null){
            user.setEmail(email);
        }

        users.put(id, user);

        return user;
    }
     */

    public void update(String id, String name, String email){
        try{
            db.updateUser(id, name, email);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
    public void delete(String id){
        users.remove(id);
    }
    */

    public void delete(String id){
        try{
            db.deleteUser(id);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
    public List findALl(){
        return new ArrayList<>(users.values());
    }
     */
    public List findALl(){
        ArrayList allUSers = null;
        try{
            allUSers = db.getAllUsers();
        } catch(Exception e){
            e.printStackTrace();
        }
        return allUSers;
    }

    // Prima era vuoto
    public UserService(){
        try{
            db = new DBConnection();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
