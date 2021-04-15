package in.example.notesappusingfirestore;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Notes implements Serializable {

    @Exclude
    private String id;
    private String title, description;

    public Notes(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public Notes(String title, String description) {
        this.title = title;
        this.description = description;
    }



    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
