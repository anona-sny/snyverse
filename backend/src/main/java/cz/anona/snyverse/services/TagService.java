package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.article.Tag;
import cz.anona.snyverse.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public boolean existTag(String tagName) {
        return this.tagRepository.findAllByName(tagName).size()>0;
    }

    public void createTag(String tagName) {
        if(!this.existTag(tagName)) {
            Tag tag = new Tag();
            tag.setName(tagName);
            this.tagRepository.save(tag);
        }
    }

}
