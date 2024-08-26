package app;


public class Session {
    private String sessionId;
    private Person user;

    public Session(String sessionId, Person user) {
        this.sessionId = sessionId;
        this.user = user;
    }

    public String getSessionId() { return sessionId; }
    public Person getUser() { return user; }
}