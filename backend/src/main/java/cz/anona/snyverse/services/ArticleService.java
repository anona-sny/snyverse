package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.neo.article.Tag;
import cz.anona.snyverse.entities.neo.user.User;
import cz.anona.snyverse.entities.neo.state.State;
import cz.anona.snyverse.entities.neo.article.Article;
import cz.anona.snyverse.entities.neo.state.StateCode;
import cz.anona.snyverse.repositories.neo.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ArticleService {

    @Autowired
    protected ArticleRepository articleRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    protected SessionService sessionService;

    protected Logger logger = LoggerFactory.getLogger(ArticleService.class);

    public State createArticle(Article article) {
        if(article == null) {
            return State.getStatus("Error", "Can't create empty article", StateCode.BAD_DATA);
        }
        if(article.getActiveData().getHeader() == null || article.getActiveData().getHeader().equals("")){
            return State.getStatus("Error", "Empty title", StateCode.BAD_DATA);
        }
        if(!this.sessionService.isLogged()) {
            return State.getStatus(StateCode.NOT_LOGGED);
        }
        if(!checkTags(article)) {
            return State.getStatus("Tags don't exist", "Tags don't exist", StateCode.BAD_DATA);
        }
        article.getActiveData().setCreateDate(OffsetDateTime.now());
        article.getActiveData().setLastUpdateDate(OffsetDateTime.now());
        article.setAuthor(this.userService.getUserFromSession());
        article.setId(null);
        Article articleSaved = this.articleRepository.save(article);
        this.logger.info(articleSaved.toString());
        return State.getStatus("Article created", "Article was successfully created", StateCode.CREATED);
    }

    public List<Article> getAllArticles() {
        List<Article> articles = this.articleRepository.findAll();
        for(Article article: articles) {
            article.getAuthor().getActiveData().setPasswordHash(null);
            article.getAuthor().getActiveData().setEmail(null);
        }
        return articles;
    }

    public List<Article> getAllArticlesForUser(Long userId) {
        List<Article> articles = new ArrayList<>();
        User author = this.userService.getUserFromSession();
        this.articleRepository.findAll().forEach(article -> {
           if(article.getAuthor().getId() == author.getId()) {
               article.getAuthor().getActiveData().setPasswordHash(null);
               article.getAuthor().getActiveData().setEmail(null);
               articles.add(article);
           }
        });
        return articles;
    }

    public State updateArticle(Article article, Long articleId) {
        if(article == null || articleId == null || this.articleRepository.existsById(articleId)) {
            return State.getStatus(StateCode.BAD_DATA);
        }
        if(article.getActiveData().getHeader() == null || article.getActiveData().getHeader().equals("")) {
            return State.getStatus(StateCode.BAD_DATA);
        }
        if(!this.sessionService.isLogged()) {
            return State.getStatus(StateCode.NOT_LOGGED);
        }
        Article articleSaved = this.articleRepository.findById(articleId).get();
        if(articleSaved.getAuthor() != this.userService.getUserFromSession()) {
            return State.getStatus(StateCode.OPERATION_FORBIDDEN);
        }
        if(checkTags(article)) {
            return State.getStatus("Tags don't exist", "Tags don't exist", StateCode.BAD_DATA);
        }
        articleSaved.getActiveData().setHeader(article.getActiveData().getHeader());
        articleSaved.getActiveData().setBody(article.getActiveData().getBody());
        articleSaved.getActiveData().setTags(article.getActiveData().getTags());
        articleSaved.getActiveData().setLastUpdateDate(OffsetDateTime.now());
        return State.getStatus(StateCode.UPDATED);
    }

    public State deleteArticle(Long articleId) {
        if(articleId == null || !this.sessionService.isLogged() || !this.articleRepository.existsById(articleId)) {
            return State.getStatus(StateCode.OPERATION_FORBIDDEN);
        }
        Article savedArticle = this.articleRepository.findById(articleId).get();
        if(savedArticle.getAuthor() != this.userService.getUserFromSession()) {
            return State.getStatus(StateCode.OPERATION_FORBIDDEN);
        }
        this.articleRepository.delete(savedArticle);
        return State.getStatus(StateCode.DELETED);
    }

    private boolean checkTags(Article article) {
        return true;
    }

}
