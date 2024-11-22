package com.sdtvnews.sdtvnews.controller_frontend;

import com.sdtvnews.sdtvnews.config.security.CustomUserDetails;
import com.sdtvnews.sdtvnews.dto.ListArticleDTO;
import com.sdtvnews.sdtvnews.entity.Category;
import com.sdtvnews.sdtvnews.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/index")
public class IndexNewsController {

    @GetMapping("/")
    public String dashboard(Model model) {


        model.addAttribute("ads1","/ads-image/baca81ad-5aac-45c6-9618-6bae5491fef6_Top-ads2.png");

        return "fronted/index";  // Return the view for the dashboard
    }


}
