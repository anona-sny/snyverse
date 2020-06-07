package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.neo.state.State;
import cz.anona.snyverse.entities.neo.article.Article;
import cz.anona.snyverse.entities.neo.article.Tag;
import cz.anona.snyverse.entities.neo.state.StateCode;
import cz.anona.snyverse.repositories.neo.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@Transactional
public class ArticleService {

    @Autowired
    protected ArticleRepository articleRepository;

    @Autowired
    protected TagService tagService;

    @Autowired
    protected UserService userService;

    protected Logger logger = LoggerFactory.getLogger(ArticleService.class);

    public State createArticle(Article article) {
        if(article == null) {
            return State.getStatus("Error", "Can't create empty article", StateCode.BAD_DATA);
        }
        if(article.getTitle() == null || article.getTitle().equals("")){
            return State.getStatus("Error", "Empty title", StateCode.BAD_DATA);
        }
        if(this.userService.getUserFromSession() == null) {
            return State.getStatus("Error", "User not logged", StateCode.NOT_LOGGED);
        }
        for (Tag tag : article.getTags()) {
            this.tagService.createTag(tag.getName());
        }
        article.setCreationDate(OffsetDateTime.now());
        article.setUpdateDate(OffsetDateTime.now());
        article.setAuthor(this.userService.getUserFromSession());
        article.setId(null);
        Article articleSaved = this.articleRepository.save(article);
        this.logger.info(articleSaved.toString());
        return State.getStatus("Article created", "Article was successfully created", StateCode.CREATED);
    }

}
