package cz.anona.snyverse.services;

import cz.anona.snyverse.dtos.ArticleDTO;
import cz.anona.snyverse.dtos.TagDTO;
import cz.anona.snyverse.entities.ArticleEntity;
import cz.anona.snyverse.entities.mappers.ArticleMapper;
import cz.anona.snyverse.entities.mappers.CycleAvoidingMappingContext;
import cz.anona.snyverse.repositories.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public ArticleService() {
    }

    public ResponseEntity<String> createArticle(ArticleDTO articleDTO) {
        ArrayList<String> list = this.checkArticle(articleDTO);
        if (list.size() <= 0) {
            // tags
            if (articleDTO.getTags() != null) {
                List<TagDTO> tags = new ArrayList<>();
                for (int i = 0; i < articleDTO.getTags().size(); i++) {
                    tags.add(articleDTO.getTags().get(i).getTag());
                }
                this.tagService.analyzeTags(tags);
            }
            // save
            ArticleEntity articleEntity = articleMapper.toEntity(articleDTO, new CycleAvoidingMappingContext());
            articleEntity.getTags().forEach(tag -> {
                tag.setId(null);
                tag.setArticle(articleEntity);
            });
            // set dates
            articleEntity.setCreatedDate(Date.from(Instant.now()));
            articleEntity.setUpdateDate(Date.from(Instant.now()));
            this.articleRepository.save(articleEntity);
            return responseService.generateResponse(ResponseService.CREATED, "created");
        } else {
            return responseService.generateResponse(ResponseService.BADREQUEST, ResponseService.asJsonString(list));
        }
    }

    public ResponseEntity<String> updateArticle(ArticleDTO articleDTO) {
        ArrayList<String> list = this.checkArticle(articleDTO);
        if (list.size() <= 0) {
            // tags
            if (articleDTO.getTags() != null) {
                List<TagDTO> tags = new ArrayList<>();
                for (int i = 0; i < articleDTO.getTags().size(); i++) {
                    tags.add(articleDTO.getTags().get(i).getTag());
                }
                this.tagService.analyzeTags(tags);
            }
            // save
            ArticleEntity articleEntity = articleMapper.toEntity(articleDTO, new CycleAvoidingMappingContext());
            articleEntity.getTags().forEach(tag -> {
                tag.setId(null);
                tag.setArticle(articleEntity);
                if (tag.getTag() != null) {
                    tag.getTag().setId(null);
                }
            });
            try {
                articleEntity.setUpdateDate(Date.from(Instant.now()));
                ArticleEntity oldArticle = this.articleRepository.getOne(articleEntity.getId());
                articleEntity.setCreatedDate(oldArticle.getCreatedDate());
                this.articleRepository.save(articleEntity);
                return responseService.generateResponse(ResponseService.OKREQUEST, "updated");
            } catch (Exception e) {
                return responseService.generateResponse(ResponseService.FATALERROR, "error on server, logged for admin");
            }
        } else {
            return responseService.generateResponse(ResponseService.BADREQUEST, ResponseService.asJsonString(list));
        }
    }

    private boolean testJsonBody(ArticleDTO articleDTO) {
        boolean test = false;
        try {
            JSONObject testObject = new JSONObject(articleDTO.getBody());
            boolean isBodyPage = testObject.has("page");
            boolean isBodyMedia = testObject.has("media");
            boolean isBodySources = testObject.has("sources");
            boolean isBodyRelatives = testObject.has("relatives");
            if (isBodyPage && isBodyMedia && isBodyRelatives && isBodySources) {
                test = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.logger.error(e.getLocalizedMessage());
            test = false;
        }
        return test;
    }

    private ArrayList<String> checkArticle(ArticleDTO articleDTO) {
        ArrayList<String> errors = new ArrayList<>();
        if (articleDTO == null) {
            errors.add("null object");
            return errors;
        }
        // header
        if (articleDTO.getHeader() == null || articleDTO.getHeader().equals("")) {
            errors.add("empty header");
        } else {
            if (!RegexService.checkArticleHeader(articleDTO.getHeader())) {
                errors.add("bad header");
            }
        }
        // body
        if (articleDTO.getBody() == null || articleDTO.getBody().equals("")) {
            errors.add("empty body");
        } else {
            // body JSON parse and test
            if (!RegexService.checkArticleBody(articleDTO.getBody()) || !this.testJsonBody(articleDTO)) {
                errors.add("bad body");
            }
        }
        return errors;
    }

}
