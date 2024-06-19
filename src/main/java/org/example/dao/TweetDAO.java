package org.example.dao;

import org.example.model.Tweet;
import org.example.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TweetDAO {

    public List<Tweet> getTweets() throws SQLException {
        Connection connection = Database.getConnection();

        String query = "SELECT * FROM tweets ORDER BY created_at DESC";

        PreparedStatement stmt = connection.prepareStatement(query);

        ResultSet resultSet = stmt.executeQuery();

        List<Tweet> tweets = new ArrayList<>();

        while (resultSet.next()) {
            Tweet tweet = new Tweet();

            tweet.setId(resultSet.getInt("id"));
            tweet.setUserId(resultSet.getInt("user_id"));
            tweet.setContent(resultSet.getString("content"));
            tweet.setCreatedAt(resultSet.getTimestamp("created_at"));

            tweets.add(tweet);
        }

        return tweets;
    }

    public void createTweet(Tweet tweet) throws SQLException {
        Connection connection = Database.getConnection();

        String query = "INSERT INTO tweets (user_id, content) VALUES (?, ?)";

        PreparedStatement stmt = connection.prepareStatement(query);

        stmt.setInt(1, tweet.getUserId());
        stmt.setString(2, tweet.getContent());

        stmt.executeUpdate();

    }

}
