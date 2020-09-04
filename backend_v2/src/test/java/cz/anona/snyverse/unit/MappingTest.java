package cz.anona.snyverse.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.anona.snyverse.dtos.ArticleDTO;
import cz.anona.snyverse.dtos.TagDTO;
import cz.anona.snyverse.entities.*;
import cz.anona.snyverse.entities.mappers.ArticleMapper;
import cz.anona.snyverse.entities.mappers.CycleAvoidingMappingContext;
import cz.anona.snyverse.entities.mappers.TagMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;


public class MappingTest {

    private ArticleMapper articleMapper = Mappers.getMapper(ArticleMapper.class);
    private TagMapper tagMapper = Mappers.getMapper(TagMapper.class);

    Logger logger = LoggerFactory.getLogger(MappingTest.class);

    @Test
    public void test001() throws JsonProcessingException {
        logger.warn("zde");
        Assertions.assertNotNull(this.articleMapper);

        String testString = "test";
        Long testNumber = 1l;
        OffsetDateTime testDate = OffsetDateTime.now();

        ArticleEntity entity = new ArticleEntity();
        UserEntity userEntity = new UserEntity();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(testNumber);
        categoryEntity.setName(testString);
        userEntity.setUsername(testString);
        userEntity.setId(testNumber);
        userEntity.setEmail(testString);
        entity.setId(testNumber);
        entity.setHeader(testString);
        entity.setUpdateDate(testDate);
        entity.setBody(testString);
        entity.setUser(userEntity);
        List<ArticleTagEntity> tags = new ArrayList<>();
        ArticleTagEntity at1 = new ArticleTagEntity();
        TagEntity t1 = new TagEntity();
        t1.setId(testNumber);
        t1.setName(testString);
        at1.setTag(t1);
        at1.setArticle(entity);
        tags.add(at1);
        entity.setTags(tags);
        entity.setCategory(categoryEntity);


        ArticleDTO sto = articleMapper.toDTO(entity, new CycleAvoidingMappingContext());
        TagDTO tdo = tagMapper.toDTO(t1);
        logger.info(new ObjectMapper().writeValueAsString(sto));
        logger.info(new ObjectMapper().writeValueAsString(tdo));
    }

}
