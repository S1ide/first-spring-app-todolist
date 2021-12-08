package main.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@JsonDeserialize(using = CustomJsonDeserializer.class)
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private Date deadline;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public TodoItem(String name, String date){
        this.name = name;
        try {
            deadline = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public TodoItem(String id, String name, String date){
        this.id = Integer.parseInt(id);
        this.name = name;
        try {
            deadline = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public TodoItem() { }

    public Date getDeadline() {
        return deadline;
    }

    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDeadline(String date) {
        try {
            this.deadline = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
