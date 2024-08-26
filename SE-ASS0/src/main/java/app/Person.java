package app;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Person {
    private String username;
    private String password;
    private String name;
    private String status;
    private String timestamp;
    private String sessionId;

    public Person(String username, String password, String name, String status,String sessionId) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.status = status;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        timestamp = LocalDateTime.now().format(formatter);
        this.sessionId = sessionId;
    }

    public String getUsername() { 
        return username; 
    }
    public String getPassword() {
         return password; 
    }
    public String getName() { 
        return name; 
    }
    public String getStatus() { 
        return status; 
    }
    public String getUpdated() { 
        return timestamp; 
    }
    public String getSessionId()
    {
        return sessionId;
    }
    public void setName(String name) {
        this.name = name;
        // no need to update time // todo
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }

    public void setStatus(String status) {
        this.status = status;
        // no need to update time  //todo
    }

    public String toString() {
        // System.out.println("Person");
        // System.out.println();
        // System.out.println("------");
        // String tmp = 
        return String.format("Person\n------\nname: %s\nusername: %s\nstatus: %s\nupdated: %s", name, username, status, timestamp);
    }
}
