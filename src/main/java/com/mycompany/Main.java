package com.mycompany;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.user.User;
import com.mycompany.user.UserService;

import java.util.List;

import static spark.Spark.*;

/**
 * RUN: curl http://localhost:8080
 *
 * curl -X POST -d "name=luca&email=luca.bianchi@gmail.com" http://localhost:8080/user/add
 *
 * curl -X POST -d "name=marco&email=marco.ascia@gmail.com" http://localhost:8080/user/add
 *
 * curl -X PUT -d "name=primo&email=primo.dellelenco@gmail.com" http://localhost:8080/user/2
 *
 * curl -X DELETE -d "name=luca&email=luca.bianchi@gmail.com" http://localhost:8080/user/1
 */
public class Main {

    private static UserService userService = new UserService(); // servizio
    private static ObjectMapper objmap = new ObjectMapper();    // mappa per usare JSON

    public static void main(String[] args) {

        port(8080); // setto porta Server Java

        // SETTO METODI:

        // --- GET: richiedo utente ---
        get("/", ((request, response) -> "Welcome"));

        get("/user/id", ((request, response) -> {
            User user = userService.findById(request.params(":id"));
            if(user != null){
                return objmap.writeValueAsString(user); // converte la classe in un oggeto JSON
            }
            else{
                response.status(404); // NOT FOUND
                return objmap.writeValueAsString("User not found");
            }
        }));

        get("/user", ((request, response) -> {
            List utenti = userService.findALl();
            if(utenti.isEmpty()){
                return objmap.writeValueAsString("No user in DB");
            }
            else{
                return objmap.writeValueAsString(userService.findALl());
            }
        }));

        // --- POST: inserisco utente ---
        post("/user/add", (request, response) -> {
            String name = request.queryParams("name");
            String email = request.queryParams("email");
            User user = userService.add(name, email);
            response.status(201);   // CREATED
            return objmap.writeValueAsString(user);
        });

        // --- PUT: modifico valore di nome/email utente  ---
        put("/user/:id", ((request, response) -> {
            String id = request.params(":id");
            User user = userService.findById(id);
            if(user != null){
                String name = request.queryParams("name");
                String email = request.queryParams("email");
                userService.update(id, name, email);
                return objmap.writeValueAsString("User with id " + id + " updated");
            }
            else{
                response.status(404);   // NOT FOUND
                return objmap.writeValueAsString("User with id " + id + " not found");
            }
        }));

        // --- DELETE: cancellazione utente  ---
        delete("/user/:id", ((request, response) -> {
            String id = request.params(":id");
            User user = userService.findById(id);
            if(user != null){
                userService.delete(id);
                return objmap.writeValueAsString("User with id " + id + " deleted");
            }
            else{
                response.status(404);   // NOT FOUND
                return objmap.writeValueAsString("User with id " + id + " not found");
            }
        }));

    }
}
