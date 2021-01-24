package cz.anona.snyverse.services.guards;

import cz.anona.snyverse.entities.ArticleEntity;
import cz.anona.snyverse.repositories.ArticleRepository;
import cz.anona.snyverse.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationGuard {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private SessionService sessionService;

    public boolean checkingPermissionArticle(Long articleId) {
        ArticleEntity articleEntity = this.articleRepository.getOne(articleId);
        return articleEntity.getUser().getId().longValue() == this.sessionService.getSession().getUserId().longValue();
    }

    public boolean checkingPermissionUser(Long userId) {
        return this.sessionService.getSession().getUserId().longValue() == userId;
    }

}
