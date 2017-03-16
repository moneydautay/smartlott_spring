package com.smartlott.web.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by greenlucky on 2/3/17.
 */
@Controller
@RequestMapping(MemberController.MEMBER_URL)
public class MemberController {

    public static final String MEMBER_URL = "/stl-admin/member";

    public static final String MEMBER_VIEW_NAME = "/admin/member";

    @RequestMapping("/all")
    public String showAllMember(@RequestParam(value = "status", required = false) boolean status,
                                @RequestParam(value = "query", required = false) String query,
                                Model model) {
        model.addAttribute("status", status);
        model.addAttribute("query", query);

        return MEMBER_VIEW_NAME + "/index";
    }

    @RequestMapping("/view/{memberId}")
    public String viewMember(@PathVariable long memberId, Model model) {
        model.addAttribute("memberId", memberId);
        return MEMBER_VIEW_NAME + "/addMember";
    }

    @RequestMapping("/edit/{memberId}")
    public String editMember(@PathVariable long memberId, Model model) {
        model.addAttribute("memberId", memberId);
        return MEMBER_VIEW_NAME + "/addMember";
    }

    @RequestMapping("/add")
    public String addMember() {
        return MEMBER_VIEW_NAME + "/addMember";
    }
}
