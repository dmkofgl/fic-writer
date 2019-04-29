package fic.writer.domain.service.files;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.enums.State;

import java.util.Set;

public interface BookParser {
    String getTitle();

    Set<Profile> getCoauthors();

    String getDescription();

    State getState();

    Set<Article> getArticles();

    default Book parse() {
        return Book.builder()
                .title(this.getTitle())
                .description(this.getDescription())
                .articles(this.getArticles())
                .build();
    }
}
