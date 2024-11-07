package com.sdtvnews.sdtvnews.controller;

import com.sdtvnews.sdtvnews.config.security.CustomUserDetails;
import com.sdtvnews.sdtvnews.dto.ListArticleDTO;
import com.sdtvnews.sdtvnews.dto.request.NewsArticleRequest;
import com.sdtvnews.sdtvnews.dto.response.CategoryResponse;
import com.sdtvnews.sdtvnews.dto.response.UserResponse;
import com.sdtvnews.sdtvnews.entity.NewsArticle;
import com.sdtvnews.sdtvnews.services.CategoryService;
import com.sdtvnews.sdtvnews.services.NewsArticleService;
import com.sdtvnews.sdtvnews.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    NewsArticleService newsArticleService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;

    @GetMapping("/new-article")
    public String createArticle(Model model) {
        List<CategoryResponse>lstCategory= categoryService.getActiveCategories();

        model.addAttribute("lstCategory",lstCategory);
        return "dashboard/new-article";  // Return login page
    }
    @GetMapping("/list-article")
    public String listArticle(Model model) {
        List<ListArticleDTO>lstdata = newsArticleService.getAllArticles();
        List<CategoryResponse>lstCategory= categoryService.getActiveCategories();
        List<UserResponse>lstUser= userService.getActiveUser();

        model.addAttribute("lstCategory",lstCategory);
        model.addAttribute("lstUser",lstUser);
        model.addAttribute("lstdata",lstdata);

        return "dashboard/list-article";  // Return login page
    }

    @GetMapping("/find-by/{id}")
    public String findById(@PathVariable String id, Model model) {
        NewsArticle lstdata = newsArticleService.getArticleById(Long.valueOf(id));
        List<CategoryResponse>lstCategory= categoryService.getActiveCategories();


        model.addAttribute("lstCategory",lstCategory);
        model.addAttribute("categoryId", lstdata.getCateId());
        model.addAttribute("lstdata",lstdata);
        //model.addAttribute("responses",responses);
        return "dashboard/edit-article";  // Return login page
    }

    @PostMapping("/create")
    public String createSections(@ModelAttribute("article") NewsArticleRequest request, RedirectAttributes redirectAttributes) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                Long userId = userDetails.getId(); // Retrieve user ID from the authenticated user

                request.setUserId(userId); // Set the user ID in the request
            }



            newsArticleService.createArticle(request);  // Attempt to create section
            redirectAttributes.addFlashAttribute("create", "Article created successfully!");
        } catch (Exception e) {
            // Handle the case where the section already exists
            redirectAttributes.addFlashAttribute("warning", e.getMessage()); // Add warning message
        }
        return "redirect:/article/list-article"; // Redirect to the sections page
    }

    @PostMapping("/update")
    public String updateSections(@ModelAttribute("article") NewsArticleRequest request, RedirectAttributes redirectAttributes) {
        try {

            newsArticleService.updateSections(Long.valueOf(request.getId()),request);  // Attempt to create section
            redirectAttributes.addFlashAttribute("update", "Article update successfully!");
        } catch (Exception e) {
            // Handle the case where the section already exists
            redirectAttributes.addFlashAttribute("warning", e.getMessage()); // Add warning message
        }
        return "redirect:/article/list-article"; // Redirect to the sections page
    }

    @GetMapping("/checkTitleDuplicate")
    public ResponseEntity<Map<String, Boolean>> checkTitleDuplicate(@RequestParam("title") String title) {
        boolean isDuplicate = newsArticleService.isTitleDuplicate(title); // Call service to check duplication

        // Prepare response
        Map<String, Boolean> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return ResponseEntity.ok(response);
    }

}
