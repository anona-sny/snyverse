package cz.anona.snyverse.services;

import cz.anona.snyverse.dtos.ArticleDTO;
import cz.anona.snyverse.entities.ArticleDataEntity;
import cz.anona.snyverse.entities.ArticleEntity;
import cz.anona.snyverse.entities.ArticleTagEntity;
import cz.anona.snyverse.entities.enums.ArticleExceptionType;
import cz.anona.snyverse.entities.enums.VisibilityType;
import cz.anona.snyverse.entities.exceptions.ArticleException;
import cz.anona.snyverse.repositories.ArticleDataRepository;
import cz.anona.snyverse.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleDataRepository articleDataRepository;

    private final TagService tagService;

    private final CategoryService categoryService;

    private final UserService userService;

    private final SessionService sessionService;

    @Autowired
    public ArticleService(ArticleRepository ar, TagService ts, CategoryService cs, UserService us, SessionService ss, ArticleDataRepository adr) {
        this.articleRepository = ar;
        this.tagService = ts;
        this.categoryService = cs;
        this.userService = us;
        this.sessionService = ss;
        this.articleDataRepository = adr;
    }

    public ArticleEntity createArticle(ArticleDTO articleDTO) throws Exception {
        // null check
        if(articleDTO.getName() == null || articleDTO.getName().equals("")) {
            throw new ArticleException(ArticleExceptionType.NAME_BODY_NULL, "Name cannot be null");
        }
        // need more uni-centered JSON check
        if(articleDTO.getBody() == null) {
            throw new ArticleException(ArticleExceptionType.NAME_BODY_NULL, "Body cannot be empty");
        }
        // regex name
        if(!RegexService.checkArticleHeader(articleDTO.getName())) {
            throw new ArticleException(ArticleExceptionType.NAME_INVALID, "Name regex invalid");
        }
        // category exist (if category is not null and id is not in database
        if(articleDTO.getCategory() != null && this.categoryService.getCategoryById(articleDTO.getCategory().getId()) == null) {
            throw new ArticleException(ArticleExceptionType.CATEGORY_INVALID, "Category doesn't exist");
        }
        // tag exists (dont create new, must be done on FE)
        if(!articleDTO.getTags().stream().allMatch(aLong -> this.tagService.getTagById(aLong)!= null)) {
            throw new ArticleException(ArticleExceptionType.TAGS_INVALID, "Insert only existing tags");
        }
        // files exist (dont create new, must be done on FE)

        // convert to entity and give it right data's
        ArticleEntity newArticle = new ArticleEntity();
        newArticle.setAuthor(this.userService.getUserById(this.sessionService.getSession().getUserId()));
        newArticle.setCreationDate(OffsetDateTime.now());
        newArticle.setLanguage(articleDTO.getLanguage());
        newArticle.setVisibility(VisibilityType.PRIVATE);
        newArticle.setArticleDataEntities(new ArrayList<>());
        ArticleDataEntity newArticleData = new ArticleDataEntity();
        newArticleData.setArticle(newArticle);
        newArticleData.setAuthor(newArticle.getAuthor());
        newArticleData.setName(articleDTO.getName());
        newArticleData.setBody(articleDTO.getBody());
        newArticleData.setCreateDate(newArticle.getCreationDate());
        newArticleData.setCategory(this.categoryService.getCategoryById(articleDTO.getCategory().getId()));
        newArticleData.setArticleTagEntities(articleDTO.getTags().stream().map(aLong -> {
            ArticleTagEntity articleTagEntity = new ArticleTagEntity();
            articleTagEntity.setArticleData(newArticleData);
            articleTagEntity.setTag(tagService.getTagById(aLong));
            return articleTagEntity;
        }).collect(Collectors.toList()));
        // store on db and return
        return this.articleRepository.save(newArticle);
    }

}
