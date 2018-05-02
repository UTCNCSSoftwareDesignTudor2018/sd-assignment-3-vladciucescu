package client.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Writer {

    private String username;
    private String name;
    private String surname;

    @JsonCreator
    public Writer(@JsonProperty("username") String username, @JsonProperty("name") String name, @JsonProperty("surname") String surname) {
        this.username = username;
        this.name = name;
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return name+" "+surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return Objects.equals(username, writer.username) &&
        		Objects.equals(name, writer.name) &&
        		Objects.equals(surname, writer.surname);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, name, surname);
    }
}
