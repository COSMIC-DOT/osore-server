package com.dot.osore.util.github;

import java.util.List;

public class UrlParser {

    public static String parseRepoName(String url) throws Exception {
        List<String> words = List.of(url.split("/"));
        if (!"github.com".matches(words.get(2))) throw new Exception();
        return words.get(3) + "/" + words.get(4);
    }
}