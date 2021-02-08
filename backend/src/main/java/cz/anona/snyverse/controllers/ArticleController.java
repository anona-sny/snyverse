package cz.anona.snyverse.controllers;

import cz.anona.snyverse.dtos.ArticleDTO;
import cz.anona.snyverse.entities.exceptions.ArticleException;
import cz.anona.snyverse.entities.exceptions.AuthorizationException;
import cz.anona.snyverse.services.ArticleService;
import cz.anona.snyverse.services.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {

    private final ArticleService articleService;
    private final SessionService sessionService;

    @Autowired
    public ArticleController(ArticleService as, SessionService ss) {
        this.articleService = as;
        this.sessionService = ss;
    }

    @PostMapping(path = "/create")
    public void createArticle(@RequestBody ArticleDTO articleDTO) throws AuthorizationException {
        if(articleDTO == null) {

        }
        if(!this.sessionService.isLogged()) {
            throw new AuthorizationException("You cannot create article without login");
        }

    }

    public void updateArticle() {

    }

    public void deleteArticle() {

    }

    public void listArticles() {

    }

    @ExceptionHandler({ AuthorizationException.class })
    public ResponseEntity<Object> handleAuthorizeException(AuthorizationException ae) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ae.getData());
    }

    @ExceptionHandler({ ArticleException.class })
    public ResponseEntity<Object> handleAuthorizeException(ArticleException ae) {
        return ResponseEntity.status(400).body(ae.getData());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleFatalException(Exception e) {
        log.error(e.getLocalizedMessage());
        return ResponseEntity.status(500).body("Fatal exception on server");
    }
}
