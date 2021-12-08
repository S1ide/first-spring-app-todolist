package main;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import main.model.CustomJsonDeserializer;
import main.model.TodoItem;
import main.model.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/items")
public class ItemsController {
    @Autowired
    public TodoListRepository repository;

    @GetMapping("/")
    public ResponseEntity getItems() {
        Iterable<TodoItem> todoItems = repository.findAll();
        ArrayList<TodoItem> items = new ArrayList<>();
        todoItems.forEach(items::add);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @PostMapping("/")
    public ResponseEntity<Integer> add(@RequestBody TodoItem todoItem) {
        repository.save(todoItem);
        return ResponseEntity.status(HttpStatus.OK).body(todoItem.getId());
    }

    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity putAll(@RequestBody List<TodoItem> todoItemList){
        todoItemList.forEach(repository::save);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/")
    public ResponseEntity delete(){
        repository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity getItem(@PathVariable int id){
        Optional<TodoItem> item = repository.findById(id);
        return item.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(item.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/{id}")
    public ResponseEntity postItem(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity putItem(@PathVariable int id, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "date", required = false) String date){
        Optional<TodoItem> itemTodo = repository.findById(id);
        if (itemTodo.isPresent()){
            TodoItem item = itemTodo.get();
            if (name != null) item.setName(name);
            if (date != null) item.setDeadline(date);
            repository.save(item);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable int id){
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
