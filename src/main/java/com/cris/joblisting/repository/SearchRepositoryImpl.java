package com.cris.joblisting.repository;

import com.cris.joblisting.model.Post;
import com.mongodb.client.AggregateIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.bson.Document;

@Component
public class SearchRepositoryImpl implements SearchRepository {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<Post> findByText(String text) {
        List<Document> pipeline = Arrays.asList(new Document("$search",
                        new Document("text",
                                new Document("query", text)
                                        .append("path", Arrays.asList("techs", "desc", "profile")))),
                new Document("$sort", new Document("exp", 1L)),
                new Document("$limit", 5L));

        List<Document> result = mongoTemplate.getDb().getCollection("JobPost").aggregate(pipeline).into(new ArrayList<>());

        List<Post> posts = new ArrayList<>();
        for (Document doc : result) {
            System.out.println(doc);
            Post post = new Post(doc);
            posts.add(post);
        }

//        final List<Post> posts = new ArrayList<>();

        return posts;
    }
}
