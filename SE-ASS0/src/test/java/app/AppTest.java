package app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private App app;

    @BeforeEach
    void setUp() {
        app = new App();
        app.clear_Json(true);
    }

    @Test
    void testCreateUser() {
        String result = app.createUser("john", "password", "John Doe", "Active",true);
        assertTrue(result.contains("[account created]"));
        assertTrue(result.contains("name: John Doe"));
        assertTrue(result.contains("username: john"));
        assertTrue(result.contains("status: Active"));

        result = app.createUser("john", "12321", "abcs", "sleep",true);
        assertTrue(result.contains("User already exists"));
        
        result = app.createUser(null, "password", "John Doe", "Active",true);
        assertTrue(result.contains("missing username"));

        result = app.createUser("sai", null, "John Doe", "Active",true);
        assertTrue(result.contains("missing password"));

        result = app.createUser("sai", "adsc", null, "Active",true);
        assertTrue(result.contains("missing name"));

        result = app.createUser("sai", "adsc", "John Doe", null,true);
        assertTrue(result.contains("missing status"));

    }

    @Test
    void testLoginUser() {
        app.createUser("john", "password", "John Doe", "Active",true);
        String result = app.loginUser("john", "password",true);
        assertTrue(result.contains("Welcome back to the App, John Doe!"));
    }

    @Test
    void testLoginUserInvalidCredentials() {
        app.createUser("john", "password", "John Doe", "Active",true);
        String result = app.loginUser("john", "wrongpassword",true);
        assertEquals("Invalid credentials.", result);

        result = app.loginUser("unknown", "password",true);
        assertEquals("Invalid credentials.", result);

    }

    // @Test
    // void testListPeople() {
    //     app.createUser("john", "password", "John Doe", "Active");
    //     app.createUser("jane", "password", "Jane Doe", "Busy");

    //     List<Person> people = app.listPeople();
    //     assertEquals(2, people.size());
    // }

    // @Test
    // void testUpdateUser() {
    //     app.createUser("john", "password", "John Doe", "Active");
    //     String sessionId = app.loginUser("john", "password").split("Session ID: ")[1];

    //     String result = app.updateUser(sessionId, "Johnny", null);
    //     assertEquals("User updated.", result);

    //     result = app.updateUser(sessionId, null, "Inactive");
    //     assertEquals("User updated.", result);

    //     Person john = app.listPeople().stream()
    //         .filter(person -> person.getUsername().equals("john"))
    //         .findFirst()
    //         .orElse(null);

    //     assertNotNull(john);
    //     assertEquals("Johnny", john.getName());
    //     assertEquals("Inactive", john.getStatus());
    // }

    // @Test
    // void testDeleteUser() {
    //     app.createUser("john", "password", "John Doe", "Active");
    //     String sessionId = app.loginUser("john", "password").split("Session ID: ")[1];

    //     String result = app.deleteUser(sessionId);
    //     assertEquals("Account deleted.", result);

    //     assertTrue(app.listPeople().isEmpty());
    // }

    // @Test
    // void testLogout() {
    //     app.createUser("john", "password", "John Doe", "Active");
    //     String sessionId = app.loginUser("john", "password").split("Session ID: ")[1];

    //     app.logout(sessionId);
    //     String result = app.updateUser(sessionId, "Johnny", "Inactive");
    //     assertEquals("Invalid session.", result);
   // }
}
