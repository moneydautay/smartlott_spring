package com.smartlott.web.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by greenlucky on 1/27/17.
 */
@Controller
@RequestMapping(PostController.POST_URL)
public class PostController {

    public static final String POST_URL = "/stl-admin/post";

    public static final String POST_VIEW_NAME = "admin/post";

    @RequestMapping("/all")
    public String showAllPost(){
        return POST_VIEW_NAME+"/index";
    }

    @RequestMapping("/add")
    public String addPost(){
        return POST_VIEW_NAME+"/addPost";
    }

    @RequestMapping("/edit/{postId}")
    public String editPost(@PathVariable long postId, Model model){
        model.addAttribute("postId",postId);
        return POST_VIEW_NAME+"/addPost";
    }
}
