package cz.anona.snyverse.controllers;

import cz.anona.snyverse.entities.neo.state.State;
import cz.anona.snyverse.entities.neo.article.Article;
import cz.anona.snyverse.entities.neo.state.StateCode;
import cz.anona.snyverse.services.ArticleService;
import cz.anona.snyverse.services.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    public ArticleService articleService;

    @Autowired
    public SessionService sessionService;

    Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @RequestMapping(path = "/add")
    public State newArticle(@RequestBody Article article) {
        if(this.sessionService.isLogged()) {
            return this.articleService.createArticle(article);
        } else {
            return State.getStatus(StateCode.NOT_LOGGED);
        }
    }

}
