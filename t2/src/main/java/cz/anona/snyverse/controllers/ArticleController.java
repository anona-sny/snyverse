package cz.anona.snyverse.controllers;

import cz.anona.snyverse.dtos.ArticleDTO;
import cz.anona.snyverse.services.ArticleService;
import cz.anona.snyverse.services.ResponseService;
import cz.anona.snyverse.services.guards.OperationGuard;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private OperationGuard guard;

    @Autowired
    private ResponseService responseService;

    @ApiOperation(value = "Creating article", notes = "Creating new article for logged user")
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<String> createArticle() {
        return null;
    }

    @ApiOperation(value = "Updating article", notes = "If user is creator, update article")
    @RequestMapping(path = "/update/{articleId}", method = RequestMethod.POST)
    public ResponseEntity<String> updateArticle(@PathVariable Long articleId, @RequestBody ArticleDTO articleDTO) {
        // TODO guard can allow access for admin
        if (!this.guard.checkingPermissionArticle(articleId)) {
            return this.responseService.generateResponse(ResponseService.NOTAUTHORIZED, "not authorized");
        }
        articleDTO.setId(articleId);
        return this.articleService.updateArticle(articleDTO);
    }

}
