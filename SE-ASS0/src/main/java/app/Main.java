package app;

import java.util.*;

public class Main
{
    public static void print_home_logs()
    {
        System.out.println("Welcome to the App!\n");
        System.out.println("login: ./app 'login <username> <password>'");
        System.out.println("join: ./app 'join'");
        System.out.println("create: ./app 'create username=\"<value>\" password=\"<value>\" name=\"<value>\" status=\"<value>\"'");
        System.out.println("people: ./app 'people'");
    }
    public static void main(String[] arg)
    {
        Scanner sc = new Scanner(System.in);
        
        if(arg.length==0)
        {
            print_home_logs();
            return;
        }
        String[] args = arg[0].split("\\s+");
        int arg_len = args.length;


        if(arg_len==1 && args[0].equals("home"))
        {
            print_home_logs();
            return;
        }
        switch(args[0])
        {
            case "join":
            {
                if(arg_len!=1)
                {
                    System.out.println("Enter correct command");  // todo
                    return;
                }
                System.out.print("New Person\n");
                System.out.print("----------\n");

                System.out.print("username:");
                String username = sc.nextLine();
                //  System.out.println();

                System.out.print("password:");
                String password = sc.nextLine();
                //  System.out.println();

                System.out.print("confirm password:");
                String confirm_pass = sc.nextLine();
                //  System.out.println();

                if(!confirm_pass.equals(password))
                {
                    System.out.println("Enter Correct Password");
                    return;
                }

                System.out.print("name:");
                String name = sc.nextLine();
                //  System.out.println();

                System.out.print("status:");
                String status = sc.nextLine();

                App new_user = new App();
                String output = new_user.createUser(username, password, name, status,false);
                
                System.out.println(output);

                break;
            }
            case "login":
            {
                if(arg_len!=3)
                {
                    System.out.println("Enter correct command");  // todo
                    return;
                }
                App user = new App();
                System.out.println(user.loginUser(args[1], args[2],false));
                break;
            }
            case "clear":
            {
                App user = new App();
                user.clear_Json(false);
                break;
            }
            case "session":
            {
                if(arg_len==2 || (arg_len==3 && args[2].equals("home")))
                {
                    App session = new App();
                    System.out.println(session.loginUser_using_session(args[1],false));
                    break;
                }
            }
        }
    }
}