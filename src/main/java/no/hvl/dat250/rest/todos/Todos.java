package no.hvl.dat250.rest.todos;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Todos {

    ArrayList<Todo> todos;
    ArrayList<Long> usedIds;

    public Todos() {
        todos = new ArrayList<>();
        usedIds = new ArrayList<>();
    }

    public void add(Todo todo){
        if (todo.getId() == null){
            todo.setId(generateUniqueLong());
        }
        todos.add(todo);
        usedIds.add(todo.getId());
        System.out.println(todos.get(0));
    }

    public boolean delete(long id){
        Todo toDelete = getTodo(id);
        if (toDelete != null){
            todos.remove(toDelete);
            usedIds.remove(toDelete.getId());
            return true;
        }
        return false;
    }

    public ArrayList<Todo> getTodos(){
        return todos;
    }

    public Todo getTodo(Long id) {
        Todo to;
        for (int i = 0; i < todos.size(); i++){
            to = todos.get(i);
            if (to.getId().equals(id)){
                return to;
        }
        }
        return null;
    }

    public boolean updateTodo(Todo todo) {
        Todo toUpdate = getTodo(todo.getId());
        if (toUpdate != null){
            todos.remove(toUpdate);
            todos.add(todo);
            return true;
        }
        return false;
    }

    public long generateUniqueLong(){
        long unique = ThreadLocalRandom.current().nextLong(100000000L);
        while (usedIds.contains(unique)){
            unique = ThreadLocalRandom.current().nextLong(100000000L);
            if (unique < 0L){ //Don't want negative id's
                unique *= (-1L);
            }
        }
        System.out.println(unique);
        return unique;
    }


}
