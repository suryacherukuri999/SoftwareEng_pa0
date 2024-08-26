package app;

import java.lang.reflect.Type;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class App
{

    String session_output(String inp,String sessionId,boolean login)
    {
        StringBuilder sb = new StringBuilder(inp);
        sb.append(String.format("\n\nedit: ./app 'session %s edit':\n", sessionId));
        sb.append(String.format("update: ./app 'session %s update (name=\"<value>\"|status=\"<value>\")+'\n", sessionId));
        if(!login)
            sb.append(String.format("delete: ./app 'session %s delete'\n", sessionId));
        sb.append(String.format("logout: ./app 'session %s logout'\n", sessionId));
        sb.append(String.format("show people: ./app '[session %s ]people'\n", sessionId));
        if(!login)
            sb.append(String.format("home: ./app ['session %s']\n", sessionId));

        return sb.toString();
    }
    String createUser(String username, String password, String name, String status,boolean is_utc) {
        if(username==null || username.length()==0)
        {
            return "missing username";
        }
        if(password==null || password.length()==0)
        {
            return "missing password";
        }
        if(name==null || name.length()==0)
        {
            return "missing name";
        }
        if(status==null || status.length()==0)
        {
            return "missing status";
        }
        String user_filePath = "user_data.json";
        if(is_utc)
            user_filePath = "user_data_test.json";

        String session_filePath = "session_data.json";
        if(is_utc)
            session_filePath = "session_data_test.json";

        String sessionId = UUID.randomUUID().toString();

        Gson gson1 = new Gson();

        Type type = new TypeToken<HashMap<String, Person>>() {}.getType();
        Type type_session = new TypeToken<HashMap<String, Session>>() {}.getType();

        Map<String, Person> users;
        Map<String, Session> sessions;

        try (FileReader reader = new FileReader(user_filePath)) {
            users = gson1.fromJson(reader, type);
            if (users == null) {
                users = new HashMap<>(); // Initialize if null
            }
        } catch (IOException e) {
            e.printStackTrace();
            users = new HashMap<>(); // Initialize if file doesn't exist or other IO issues
        }

        // Check if user already exists
        if (users.containsKey(username)) {
            return "User already exists";   // todo
        }

        // Add new user
        Person user = new Person(username, password, name, status,sessionId);
        users.put(username, user);

        Gson gson2 = new Gson();
        try (FileReader reader = new FileReader(session_filePath)) {
            sessions = gson2.fromJson(reader, type_session);
            if (sessions == null) {
                sessions = new HashMap<>(); // Initialize if null
            }
        } catch (IOException e) {
            e.printStackTrace();
            sessions = new HashMap<>(); // Initialize if file doesn't exist or other IO issues
        }

        sessions.put(sessionId, new Session(sessionId, user));

        // Write the updated HashMap back to the JSON file
        try (FileWriter writer = new FileWriter(user_filePath)) {
            gson1.toJson(users, type, writer);
            System.out.println("Data has been written to user_data.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(session_filePath)) {
            gson2.toJson(sessions, type_session, writer);
            System.out.println("Data has been written to session_data.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the user's information as a string
        String inp = "[account created]\n"+user.toString();
        return session_output(inp,sessionId,false);

    }

    String loginUser(String username, String password,boolean is_utc) {

        String filePath = "user_data.json";
        if(is_utc)
            filePath = "user_data_test.json";

        Gson gson = new Gson();

        Type type = new TypeToken<HashMap<String, Person>>() {}.getType();

        HashMap<String, Person> users;

        try (FileReader reader = new FileReader(filePath)) {
            users = gson.fromJson(reader, type);
            if (users == null) {
                return "Invalid credentials.";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Invalid credentials.";
        }
        // Check if user already exists
        if (users.containsKey(username)) {
            Person P = users.get(username);
            if(!P.getPassword().equals(password))
            {
                return "Invalid credentials.";
            }
            String form =  String.format("Welcome back to the App, %s!\n\n\"%s\"", P.getName(), P.getStatus());
            return session_output(form,P.getSessionId(),true);
        }
        
        return "Invalid credentials.";
    }

    String loginUser_using_session(String sessionId,boolean is_utc) {

        String filePath = "session_data.json";
        if(is_utc)
        filePath = "session_data_test.json";

        Gson gson = new Gson();

        Type type = new TypeToken<HashMap<String, Session>>() {}.getType();

        HashMap<String, Session> sessions;

        try (FileReader reader = new FileReader(filePath)) {
            sessions = gson.fromJson(reader, type);
            if (sessions == null) {
                return "User doesn't exist";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "User doesn't exist";
        }

        // Check if user already exists
        if (sessions.containsKey(sessionId)) {
            Person P = sessions.get(sessionId).getUser();
            String form = String.format("Welcome back to the App, %s!\n\n\"%s\"", P.getName(), P.getStatus());
            return session_output(form,sessionId,true);
        }
        
        return "User doesn't exist";
    }

    void clear_Json(boolean is_utc)
    {
        String filePath = "user_data.json";
        if(is_utc)
        filePath = "user_data_test.json";

        try (FileWriter writer = new FileWriter(filePath)) {
            // Write an empty JSON object to the file
            writer.write("{}");
            System.out.println("User JSON file has been cleared.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        filePath = "session_data.json";
        if(is_utc)
        filePath = "session_data_test.json";

        try (FileWriter writer = new FileWriter(filePath,false)) {
            // Write an empty JSON object to the file
            writer.write("{}");
            System.out.println("Session JSON file has been cleared.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // public List<Person> listPeople() {
    //     return new ArrayList<>(users.values());
    // }

    // public String updateUser(String sessionId, String newName, String newStatus) {
    //     Session session = sessions.get(sessionId);
    //     if (session == null) {
    //         return "Invalid session.";
    //     }
    //     if (newName != null) {
    //         session.getUser().setName(newName);
    //     }
    //     if (newStatus != null) {
    //         session.getUser().setStatus(newStatus);
    //     }
    //     return "User updated.";
    // }

    // public String deleteUser(String sessionId) {
    //     Session session = sessions.remove(sessionId);
    //     if (session == null) {
    //         return "Invalid session.";
    //     }
    //     users.remove(session.getUser().getUsername());
    //     return "Account deleted.";
    // }

    // public void logout(String sessionId) {
    //     sessions.remove(sessionId);
    // }
}