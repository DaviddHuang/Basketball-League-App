package persistence;

import model.League;
import model.Team;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.*;

// represents a writer responsible for saving the JSON representation of a league to a file
// Code is influenced by: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class Writer {
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs a writer with a destination for the file to be written to
    public Writer(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer, throws FileNotFoundException if the file at the destination cannot be
    //          used for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of a league to file
    public void write(League l) {
        JSONObject json = l.toJson();
        saveFile(json.toString(4));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void closeWriter() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes strings to file
    public void saveFile(String json) {
        writer.print(json);
    }
}
