package com.company;

import com.company.model.*;
import com.google.gson.*;
import java.io.*;
import java.net.URI;
import java.util.*;

public class HttpDemo {
    private static final String USERS_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";
    private static final String RELATIVE_PATH = "src/main/resources";

    public static User createNewUser() {
        User user = new User();
        user.setId(715);
        user.setName("David");
        user.setUsername("Dav");
        user.setEmail("David@gmail.com");
        Adress adress = new Adress();
        adress.setStreet("Fulton");
        adress.setSuite("Apt. 700");
        adress.setCity("Manhattan");
        adress.setZipcode("10038");
        Geo geo = new Geo();
        geo.setLat("404235");
        geo.setLng("740024");
        adress.setGeo(geo);
        user.setAddress(adress);
        user.setPhone("1-255-859-5263");
        user.setWebsite("david.com");
        Company company = new Company();
        company.setNameCompany("ABC");
        company.setCatchPhrase("We work for you");
        company.setBs("API");
        user.setCompany(company);

        return user;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //TASK 1
        //1.1
        User user = createNewUser();
        final User userCreated = HttpUtil.sendPOST(URI.create(USERS_URL), user);
        System.out.println("//TASK 1\n//1.1\n" + userCreated);

        //1.2
        System.out.println("\n//1.2\nPlease enter the ID of the user whose you want to rename.");
        int userId = new Scanner(System.in).nextInt();
        User userForRename = HttpUtil.sendGET_User(URI.create(String.format("%s/%d", USERS_URL, userId)));
        System.out.println("User before rename: " + userForRename);
        userForRename.setName("Alex");
        User userAfterRename = HttpUtil.sendPUT(URI.create(String.format("%s/%d", USERS_URL, userId)), userForRename);
        System.out.println("User after rename: " + userAfterRename);

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
        if (userById.getId() == 0) {
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
        System.out.println("\n//TASK2\nPlease enter the ID of the user whose comments you are interested.");
        int userId2 = new Scanner(System.in).nextInt();
        List<Post> allPosts = HttpUtil.sendGET_ALL_POSTS(URI.create(String.format("%s/%d/%s",
                USERS_URL, userId2, "posts")));
        ArrayList<Integer> arrayListPost = new ArrayList<>(allPosts.size());
        int k = 0;
        while (k < allPosts.size()) {
            arrayListPost.add(k, allPosts.get(k).getId());
            k++;
        }
        Integer maxIdPost = Collections.max(arrayListPost);
        Comment[] comment = HttpUtil.sendGET_MAX_Comment_BY_ID_USER(URI.create(String.format("%s/%d/%s",
                POSTS_URL, maxIdPost, "comments")));
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
                if (!todo.isCompleted()) {
                    System.out.println(todo);
                }
            }
        }
    }
}