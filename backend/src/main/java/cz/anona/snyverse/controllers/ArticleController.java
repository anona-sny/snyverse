package cz.anona.snyverse.controllers;

import cz.anona.snyverse.entities.neo.state.State;
import cz.anona.snyverse.entities.neo.article.Article;
import cz.anona.snyverse.entities.neo.state.StateCode;
import cz.anona.snyverse.services.ArticleService;
import cz.anona.snyverse.services.SessionService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    public ArticleService articleService;

    @Autowired
    public SessionService sessionService;

    Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @ApiOperation(	value = "Add article", notes = "Check and create article or retrieve error")
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public State newArticle(@RequestBody Article article) {
        logger.info("Create article with data: "+article.toString());
        return this.articleService.createArticle(article);
    }

    @ApiOperation(	value = "Edit article", notes = "Check and create article or retrieve error")
    @RequestMapping(path = "/edit/{articleId}", method = RequestMethod.PUT)
    public State editArticle(@RequestBody Article article, @PathVariable Long articleId) {
        logger.info("Update article with id:"+articleId+", data: "+article.toString());
        return this.articleService.updateArticle(article, articleId);
    }

    @ApiOperation(	value = "Delete article", notes = "Check and create article or retrieve error")
    @RequestMapping(path = "/delete/{articleId}", method = RequestMethod.DELETE)
    public State deleteArticle(@PathVariable Long articleId) {
        logger.info("Delete article with id:"+articleId);
        return this.articleService.deleteArticle(articleId);
    }

}
