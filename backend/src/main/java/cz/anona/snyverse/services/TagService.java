package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.TagEntity;
import cz.anona.snyverse.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public void createTag(TagEntity tagEntity) {
        tagEntity.setName(tagEntity.getName().toLowerCase(Locale.ROOT));
        if(this.tagRepository.findAllByName(tagEntity.getName()).size() == 0) {
            this.tagRepository.save(tagEntity);
        }
    }

    public List<TagEntity> listAllTags() {
        return this.tagRepository.findAll();
    }

    public TagEntity getTagById(Long id) {
        return this.tagRepository.findById(id).orElse(null);
    }

}
