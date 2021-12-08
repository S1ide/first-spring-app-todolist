package main;

import main.model.TodoItem;
import main.model.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@Controller
public class DefaultController {

    @Autowired
    TodoListRepository repository;

    @GetMapping("/")
    public String index(Model model) {
        Iterable<TodoItem> todoItems = repository.findAll();
        ArrayList<TodoItem> items = new ArrayList<>();
        todoItems.forEach(items::add);
        model.addAttribute("items", items);
        model.addAttribute("itemsCount", items.size());
        return "index";
    }
}
