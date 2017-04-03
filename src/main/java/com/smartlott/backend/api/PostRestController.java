package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.Post;
import com.smartlott.backend.persistence.domain.elastic.PostElastic;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.PostService;
import com.smartlott.backend.service.elasticsearch.PostElasticService;
import com.smartlott.enums.MessageType;
import com.smartlott.utils.PageRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;
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

    @Autowired
    private PostElasticService postElasticService;

    private List<MessageDTO> messageDTOS;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllPost(Pageable pageable, Locale locale){
        messageDTOS = new ArrayList<>();

        PageRequest pageRequest = PageRequestUtils.createPageRequest(pageable);

        Page<Post> posts = postService.getAll(pageRequest);

        if(posts.getTotalPages() == 0){
            messageDTOS.add(new MessageDTO(MessageType.WARNING,
                    i18NService.getMessage("admin.post.error.post.not.found.text" ,locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Object>(posts, HttpStatus.OK);
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getPost(@PathVariable int postId, Locale locale){
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
    public ResponseEntity<Object> createPost(@RequestBody Post post, Locale locale){
        messageDTOS = new ArrayList<>();

        if(post.isStatus())
            post.setPublishDate(LocalDateTime.now(Clock.systemDefaultZone()));

        //check unique slug
        if(postService.existSlug(post.getSlug())){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.post.error.duplicate.slug.text", locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        post = postService.create(post);

        if(post.getId() == 0){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.post.error.add.text", locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        //add post elastic searchAll
        postElasticService.create(post);

        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.post.success.add.text", post.getTitle(),locale)));
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updatePost(@PathVariable int postId, @RequestBody Post post, Locale locale){
        messageDTOS = new ArrayList<>();
        Post localPost = postService.getOne(postId);
        if(localPost == null){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.post.error.post.id.not.found.text", String.valueOf(postId) ,locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        //check unique slug
        if(postService.existSlug(post.getSlug()) && !localPost.getSlug().equals(post.getSlug())){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.post.error.duplicate.slug.text", locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        localPost.setCategories(post.getCategories());
        localPost.setTitle(post.getTitle());
        localPost.setSlug(post.getSlug());
        localPost.setContent(post.getContent());
        localPost.setStatus(post.isStatus());
        localPost.setFeaturedImage(post.getFeaturedImage());

        if(post.isStatus())
            localPost.setPublishDate(LocalDateTime.now(Clock.systemDefaultZone()));

        //update elastic post searchAll
        postElasticService.update(post);

        postService.update(localPost);
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.post.success.update.text", String.valueOf(postId),locale)));
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deletePost(@PathVariable int postId, Locale locale){
        messageDTOS = new ArrayList<>();

        if(!postService.exist(postId)){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.post.error.post.id.not.found.text", String.valueOf(postId) ,locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }
        postService.delete(postId);
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.post.success.delete.text", String.valueOf(postId),locale)));
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

    /**
     * Gets a post given by category id or null if not exist
     * @param categoryId
     * @param pageable
     * @param locale
     * @return A post or null if not exist
     */
    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getPostsByCategoryId(@PathVariable int categoryId, Pageable pageable,Locale locale){
        messageDTOS = new ArrayList<>();

        PageRequest pageRequest = PageRequestUtils.createPageRequest(pageable);
        Page<Post> posts = postService.getByCategoryId(categoryId, pageRequest);

        if(posts.getTotalPages() == 0){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.post.error.of.category.not.found.text", String.valueOf(categoryId) ,locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity<Object>(posts,HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deletes(@RequestBody List<Long> postIds, Locale locale){
        messageDTOS = new ArrayList<>();
        int resutl = postService.delete(postIds);
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.post.success.deletes.text", String.valueOf(resutl),locale)));
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/change-status/{status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> deletes(@PathVariable boolean status, @RequestBody List<Long> postIds, Locale locale){
        messageDTOS = new ArrayList<>();
        int result = postService.changeStatus(postIds, status);
        String msg = "admin.post.success.status.to.publish.text";
        if(status==false)
            msg = "admin.post.success.status.to.unpublish.text";
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage(msg, String.valueOf(result),locale)));
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public ResponseEntity<Object> searchPost(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "catid", required = false, defaultValue = "-1") int catid,
            Pageable pageable, Locale locale){
        messageDTOS = new ArrayList<>();
        Page<PostElastic> results = null;
        if(catid != -1)
            results = postElasticService.searchByTitleAndCategoriesId(title, catid, PageRequestUtils.createPageRequest(pageable));
        else
            results = postElasticService.searchByTitle(title,PageRequestUtils.createPageRequest(pageable));

        if(results.getTotalElements() == 0){
            messageDTOS.add(new MessageDTO(MessageType.WARNING,
                    i18NService.getMessage("admin.post.error.search.not.found.text", String.valueOf(title) ,locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Object>(results, HttpStatus.OK);
    }
}
