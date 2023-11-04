package com.jwt.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleBaseController {

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getAdmin(){
        return "admin";
    }

    @GetMapping("/normal")
    @PreAuthorize("hasAuthority('ROLE_NORMAL')")
    public String getNormal(){
        return "normal";
    }

    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }

}
