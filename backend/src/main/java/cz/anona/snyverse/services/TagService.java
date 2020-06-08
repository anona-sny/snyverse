package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.neo.article.Tag;
import cz.anona.snyverse.repositories.neo.TagRepository;
import org.neo4j.ogm.cypher.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagService {

    @Autowired
    protected TagRepository tagRepository;

    public boolean existTag(String tagName) {
        return this.tagRepository.findAllByName(tagName).size()>0;
    }

    /**
     * Create tag, if doesn't exist and haven't any forbidden keyword
     * @param tagName
     */
    public ResponseEntity<?> createTag(String tagName) {
        if(tagName != null) {
            String mem = tagName.toLowerCase();
            if(RegexService.checkTag(mem) && !this.existTag(tagName.toLowerCase())) {
                Tag tag = new Tag();
                tag.setName(mem);
                this.tagRepository.save(tag);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<List<Tag>> listAllTags() {
        List<Tag> tags = this.tagRepository.findAll();
        return ResponseEntity.ok(tags);
    }

    public ResponseEntity<List<Tag>> listTagsByName(String tagName) {
        if(tagName != null) {
            String mem = tagName.toLowerCase();
            if(RegexService.checkTag(mem)) {
                List<Tag> tags = this.tagRepository.findAllWithNameLike(tagName.toLowerCase());
                return ResponseEntity.ok(tags);
            }
        }
        return ResponseEntity.status(HttpStatus.valueOf(400)).build();
    }

}
