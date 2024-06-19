package org.example;

import org.example.dao.TweetDAO;
import org.example.dao.UserDAO;
import org.example.model.Tweet;
import org.example.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        TweetDAO tweetDAO = new TweetDAO();

        while (true) {
            System.out.println("Welcome to Twitter!");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    login(scanner, userDAO, tweetDAO);
                    break;
                case 2:
                    signUp(scanner, userDAO);
                    break;
                case 3:
                    System.out.println("GoodBye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.Please try again.");
            }
        }

    }

    private static void login(Scanner scanner, UserDAO userDAO, TweetDAO tweetDAO) {

        System.out.println("Username: ");
        String username = scanner.nextLine();

        System.out.println("Password: ");
        String password = scanner.nextLine();

        try {
            User user = userDAO.getUserByUsername(username);

            if (user != null && user.getPassword().equals(password)) {
                System.out.println("Login successful");
                showTweets(scanner, tweetDAO, user);
            } else {
                System.out.println("Invalid username or password");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void signUp(Scanner scanner, UserDAO userDAO) {

        System.out.print("choose a username: ");
        String username = scanner.nextLine();

        System.out.println("choose a password: ");
        String password = scanner.nextLine();

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);

        try {

            userDAO.createUser(user);
            System.out.println("User created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showTweets(Scanner scanner, TweetDAO tweetDAO, User user) {
        while (true) {
            System.out.println("1. View Tweets");
            System.out.println("2. Post a tweet");
            System.out.println("3. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewTweets(tweetDAO);
                    break;
                case 2:
                    postTweet(scanner, tweetDAO, user);
                    break;
                case 3:
                    System.out.println("Logging out...");
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    private static void viewTweets(TweetDAO tweetDAO) {
        try {
            List<Tweet> tweets = tweetDAO.getTweets();

            System.out.println("Recent Tweets: ");

            for (Tweet tweet : tweets) {
                System.out.println(tweet.getUserId() + ": " + tweet.getContent() + " (Posted at: " + tweet.getCreatedAt() + ")");
            }

            System.out.println(" ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void postTweet(Scanner scanner, TweetDAO tweetDAO, User user) {
        System.out.print("Enter your tweet: ");
        String content = scanner.nextLine();

        Tweet tweet = new Tweet();
        tweet.setUserId(user.getId());
        tweet.setContent(content);

        try {

            tweetDAO.createTweet(tweet);
            System.out.println("Tweet posted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}