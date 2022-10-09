package com.qiaoyn.xadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qiaoyanan
 * date:2022/10/08 16:50
 */
@Controller
public class MemberController {

    @GetMapping("/member-add")
    public String memberAdd() {
        return "/member/member-add";
    }

    @GetMapping("/member-del")
    public String memberDel() {
        return "/member/member-del";
    }

    @GetMapping("/member-edit")
    public String memberEdit() {
        return "/member/member-edit";
    }

    @GetMapping("/member-list")
    public String memberList() {
        return "/member/member-list";
    }

    @GetMapping("/member-password")
    public String memberPassword() {
        return "/member/member-password";
    }
}
