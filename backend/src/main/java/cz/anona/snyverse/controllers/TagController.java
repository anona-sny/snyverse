package cz.anona.snyverse.controllers;

import cz.anona.snyverse.entities.neo.article.Tag;
import cz.anona.snyverse.services.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation(	value = "List tags", notes = "Check and create article or retrieve error")
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<Tag>> selectAllTags() {
        return this.tagService.listAllTags();
    }

    @ApiOperation(	value = "Add Tag", notes = "Check and create article or retrieve error")
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> saveTag(@RequestBody Tag tag) {
        if(tag != null) {
            return this.tagService.createTag(tag.getName());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }



}
