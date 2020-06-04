package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.Status;
import cz.anona.snyverse.entities.article.Article;
import cz.anona.snyverse.entities.article.Tag;
import cz.anona.snyverse.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    public Status createArticle(Article article) {
        if(article == null) {
            return Status.getErrorStatus("Error", "Can't create empty article");
        }
        if(article.getTitle() == null || article.getTitle().equals("")){
            return Status.getErrorStatus("Error", "Empty title");
        }
        if(this.userService.getUserFromSession() == null) {
            return Status.getErrorStatus("Error", "User not logged");
        }
        for (Tag tag : article.getTags()) {
            this.tagService.createTag(tag.getName());
        }
        article.setCreationDate(OffsetDateTime.now());
        article.setUpdateDate(OffsetDateTime.now());
        article.setAuthor(this.userService.getUserFromSession());
        article.setId(null);
        this.articleRepository.save(article);
        return Status.getSuccessStatus("Article created", "Article was successfully created");
    }

}
