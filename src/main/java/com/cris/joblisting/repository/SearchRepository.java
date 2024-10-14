package com.cris.joblisting.repository;

import com.cris.joblisting.model.Post;

import java.util.List;

public interface SearchRepository {
    List<Post> findByText(String text);
}
