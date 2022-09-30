package no.hvl.dat250.rest.todos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


import static spark.Spark.*;

/**
 * Rest-Endpoint.
 */
public class TodoAPI {

    static Todos todos;
    public static void main(String[] args) {
        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        todos = new Todos();

        after((req, res) -> res.type("application/json"));

        post("/todos", (req, res) -> {
            res.type("application/json");

            Todo todo = new Gson().fromJson(req.body(), Todo.class);

            todos.add(todo);



            return new Gson().toJson(todo);
        });


        get("/todos", (req, res) -> {
            res.type("application/json");
            return new Gson().toJson(new Gson()
                            .toJsonTree(todos.getTodos()));
        });

        get("/todos/:id", (req, res) -> {
            res.type("application/json");
            Long id;
            String stringResponse;
            try {
                id = Long.parseLong(req.params(":id"));
            }
            catch (NumberFormatException e){
                stringResponse = String.format("The id \"%s\" is not a number!", req.params(":id"));
                res.body(stringResponse);
                return "";
            }
            Todo t = todos.getTodo(id);
            if (t == null){
                stringResponse = String.format("Todo with the id \"%s\" not found!", id);
                res.body(stringResponse);
                return "";
            }

            return new Gson().toJson(new Gson()
                            .toJsonTree(t));
        });

        put("todos/:id", (req, res) -> {
            res.type("application/json");
            long id;
            String stringResponse;
            try {
                id = Long.parseLong(req.params(":id"));
            }
            catch (NumberFormatException e){
                stringResponse = String.format("The id \"%s\" is not a number!", req.params(":id"));
                res.body(stringResponse);
                return "";
            }
            Todo toEdit = new Gson().fromJson(req.body(), Todo.class);
            if (todos.updateTodo(toEdit)){
                return new Gson().toJson(new Gson()
                        .toJsonTree(toEdit));
            }
            stringResponse = String.format("Todo with the id \"%s\" not found!", req.params(":id"));
            res.body(stringResponse);
            return "";
        });


        delete("/todos/:id", (req, res) -> {
            res.type("application/json");
            long id;
            String stringResponse;
            try {
                id = Long.parseLong(req.params(":id"));
            }
            catch (NumberFormatException e){
                stringResponse = String.format("The id \"%s\" is not a number!", req.params(":id"));
                res.body(stringResponse);
                return "";
            }
            boolean isDeleted = todos.delete(id);
            if (!isDeleted){
                stringResponse = String.format("Todo with the id \"%s\" not found!", id);
                res.body(stringResponse);
                return "";
            }

            return"";


        });


        // TODO: Implement API, such that the testcases succeed.
    }
}
