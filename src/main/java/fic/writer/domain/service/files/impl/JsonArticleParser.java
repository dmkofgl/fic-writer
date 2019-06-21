package fic.writer.domain.service.files.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fic.writer.domain.service.files.ArticleParser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class JsonArticleParser implements ArticleParser {
    private JsonNode jsonObject;

    public JsonArticleParser(MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root;
        try {
            root = objectMapper.reader().readTree(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        this.jsonObject = root;
    }

    @Override
    public String getTitle() {
        return jsonObject.path("title").textValue();
    }

    @Override
    public String getAnnotation() {
        return jsonObject.path("annotation").textValue();
    }

    @Override
    public String getContent() {
        return jsonObject.path("content").textValue();
    }

}
