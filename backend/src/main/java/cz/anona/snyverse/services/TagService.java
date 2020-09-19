package cz.anona.snyverse.services;

import cz.anona.snyverse.dtos.TagDTO;
import cz.anona.snyverse.entities.TagEntity;
import cz.anona.snyverse.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserService userService;

    public void analyzeTags(List<TagDTO> tagDTOS) {
        List<String> nameList = new ArrayList<>();
        tagDTOS.forEach(tagDTO -> {
            nameList.add(tagDTO.getName());
        });
        List<String> dbNames = new ArrayList<>();
        this.tagRepository.findByNameIn(nameList).forEach(tagEntity -> {
            dbNames.add(tagEntity.getName());
        });
        nameList.removeAll(dbNames);
        if(userService.returnLoggedUser() != null) {
            for (String name : nameList) {
                TagEntity tagEntity = new TagEntity();
                tagEntity.setAuthor(userService.returnLoggedUser());
                tagEntity.setName(name);
                this.tagRepository.save(tagEntity);
            }
        }
    }

}
