package fic.writer.domain.service.files;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.User;
import fic.writer.domain.entity.enums.State;

import java.util.Set;

public interface BookParser {
    String getTitle();

    Set<User> getCoAuthors();

    String getDescription();

    State getState();

    Set<Article> getArticles();
}
