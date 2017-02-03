package com.smartlott.web.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by greenlucky on 1/27/17.
 */
@Controller
@RequestMapping(PostCategoryController.POST_CATEGORY_URL)
public class PostCategoryController {

    public static final String POST_CATEGORY_URL = "stl-admin/post/category";

    private static final String POST_CATEGORY_VIEW_NAME = "/admin/post/category/index";

    private static final String ADD_POST_CATEGORY_VIEW_NAME = "/admin/post/category/addCategory";

    @RequestMapping("")
    public String getAllCategory(){
        return POST_CATEGORY_VIEW_NAME;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addCategory(){
        return ADD_POST_CATEGORY_VIEW_NAME;
    }

    @RequestMapping(value = "/edit/{categoryId}", method = RequestMethod.GET)
    public String getCategory(@PathVariable int categoryId, Model model) {
        model.addAttribute("categoryId", categoryId);
        return ADD_POST_CATEGORY_VIEW_NAME;
    }
    @RequestMapping(value = "/delete/{categoryId}", method = RequestMethod.GET)
    public String deleteCategory(@PathVariable int categoryId, Model model){
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("typeAction", "delete");
        return ADD_POST_CATEGORY_VIEW_NAME;
    }
}
