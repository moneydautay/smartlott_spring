package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.Post;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.PostService;
import com.smartlott.enums.MessageType;
import com.smartlott.utils.PageRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by greenlucky on 1/28/17.
 */
@RestController
@RequestMapping(PostRestController.POST_URL)
public class PostRestController {
    public static final String POST_URL = "/api/post";

    @Autowired
    private PostService postService;

    @Autowired
    private I18NService i18NService;

    private List<MessageDTO> messageDTOS;

    @RequestMapping("/all")
    public ResponseEntity<Object> getAllPost(Pageable pageable, Locale locale){
        messageDTOS = new ArrayList<>();

        PageRequest pageRequest = PageRequestUtils.createPageRequest(pageable);

        Page<Post> posts = postService.getAll(pageable);

        if(posts.getTotalPages() == 0){
            messageDTOS.add(new MessageDTO(MessageType.WARNING,
                    i18NService.getMessage("admin.post.error.post.not.found.text" ,locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Object>(posts, HttpStatus.OK);
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCategory(@PathVariable int postId, Locale locale){
        messageDTOS = new ArrayList<>();

        Post post = postService.getOne(postId);

        if(post == null){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.post.error.post.id.not.found.text", String.valueOf(postId) ,locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity<Object>(post, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> createCategory(@RequestBody Post post, Locale locale){
        messageDTOS = new ArrayList<>();

        post = postService.create(post);
        if(post.getId() == 0){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.post.error.add.text", locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.post.success.add.text", post.getTitle(),locale)));
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCategory(@PathVariable int postId, @RequestBody Post category, Locale locale){
        messageDTOS = new ArrayList<>();

        if(!postService.exist(postId)){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.post.error.post.id.not.found.text", String.valueOf(postId) ,locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }
        postService.update(category);
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.post.success.update.text", String.valueOf(postId),locale)));
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCategory(@PathVariable int postId, Locale locale){
        messageDTOS = new ArrayList<>();

        if(!postService.exist(postId)){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.post.error.post.id.not.found.text", String.valueOf(postId) ,locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }
        postService.delete(postId);
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("g", String.valueOf(postId),locale)));
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }
}
