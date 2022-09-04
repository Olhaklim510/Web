package com.company;

import com.company.model.*;
import com.google.gson.*;
import java.io.*;
import java.net.URI;
import java.util.*;

public class HttpDemo {
    private static final String USERS_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String POSTS_URL = "https://jsonplaceholder.typicode.com/users/1/posts";
    private static final String COMMENTS_URL = "https://jsonplaceholder.typicode.com/posts";
    private static final String RELATIVE_PATH = "src/main/resources";

    public static User createNewUser() {
        User user = new User();
        user.id = 715;
        user.name = "David";
        user.username = "Dav";
        user.email = "David@gmail.com";
        Adress adress = new Adress();
        adress.street = "Fulton";
        adress.suite = "Apt. 700";
        adress.city = "Manhattan";
        adress.zipcode = "10038";
        Geo geo = new Geo();
        geo.lat = "404235";
        geo.lng = "740024";
        adress.geo = geo;
        user.address = adress;
        user.phone = "1-255-859-5263";
        user.website = "david.com";
        Company company = new Company();
        company.nameCompany = "ABC";
        company.catchPhrase = "We work for you";
        company.bs = "API";
        user.company = company;

        return user;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //TASK 1
        //1.1
        User user = createNewUser();
        final User userCreated = HttpUtil.sendPOST(URI.create(USERS_URL), user);
        System.out.println("//TASK 1\n//1.1\n" + userCreated);

        //1.2
        userCreated.name = "Alex";
        HttpUtil.sendPUT(URI.create(String.format("%s?id=%d", USERS_URL, userCreated.getId())), userCreated);
        System.out.println("\n//1.2\n" + userCreated);

        //1.3
        System.out.println("\n//1.3");
        HttpUtil.sendDELETE(URI.create(String.format("%s/%d", USERS_URL, 1)));

        //1.4
        List<User> users = HttpUtil.sendGET_ALL_USERS(URI.create(USERS_URL));
        System.out.println("\n//1.4");
        users.forEach(System.out::println);

        //1.5
        System.out.println("\n//1.5\nPlease enter the ID of the user you want to find.");
        int userId1 = new Scanner(System.in).nextInt();
        final User userById = HttpUtil.sendGET_User(URI.create(String.format("%s/%d", USERS_URL, userId1)));
        if (userById.id == 0) {
            System.out.println("This id doesn't exist.");
        } else {
            System.out.println(userById);
        }

        //1.6
        System.out.println("\n//1.6\nPlease enter the username of the user you want to find.");
        String userName = new Scanner(System.in).nextLine();
        final User[] userByUsername = HttpUtil.sendGET_BY_USERNAME(URI.create(String.format("%s?username=%s", USERS_URL, userName)));
        if (userByUsername.length == 0) {
            System.out.println("This surname doesn't exist.");
        } else {
            StringJoiner userByUsernameString = new StringJoiner(", ");
            for (User value : userByUsername) {
                userByUsernameString.add(value.toString());
            }
            System.out.println(userByUsernameString);
        }

        //TASK 2
        List<Post> allPosts = HttpUtil.sendGET_ALL_POSTS(URI.create(POSTS_URL));
        //System.out.println("\nTASK2");
        ArrayList<Integer> arrayListPost = new ArrayList<>(allPosts.size());
        int k = 0;
        while (k < allPosts.size()) {
            arrayListPost.add(k, allPosts.get(k).id);
            k++;
        }
        Integer maxIdPost = Collections.max(arrayListPost);

        System.out.println("\n//TASK2\nPlease enter the ID of the user whose comments you are interested in on the latest post.");
        int userId2 = new Scanner(System.in).nextInt();
        Comment[] comment = HttpUtil.sendGET_MAX_Comment_BY_ID_USER(URI.create(String.format("%s/%d/%s?id=%d",
                COMMENTS_URL, maxIdPost, "comments", userId2)));
        if (comment.length == 0) {
            System.out.println("This id doesn't exist.");
        } else {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(comment);

            File file = new File(RELATIVE_PATH, String.format("user-%d-post-%d-comments.json", userId2, maxIdPost));

            if (!file.exists()) {
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                    bufferedWriter.write(json);
                } catch (IOException exc) {
                    System.out.println(exc.getMessage());
                }
            }
        }
        //TASK 3
        System.out.println("\n//TASK3\nPlease enter the ID");
        int userId3 = new Scanner(System.in).nextInt();
        List<Todo> todos = HttpUtil.sendTodo(URI.create(String.format("%s/%d/%s", USERS_URL, userId3, "todos")));
        if (todos.size() == 0) {
            System.out.println("This id doesn't exist.");
        } else {
            for (Todo todo : todos) {
                if (todo.completed.equals("false")) {
                    System.out.println(todo);
                }
            }
        }
    }
}