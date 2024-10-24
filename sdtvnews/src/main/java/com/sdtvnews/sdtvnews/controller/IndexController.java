package com.sdtvnews.sdtvnews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexController {


    @Autowired
    private AuthenticationManager authenticationManager;  // Handles authentication

    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password."); // Error from Spring Security
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been successfully logged out.");
        }
        return "dashboard/login";  // Return login page
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Retrieve the logged-in user's username from the security context
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        // Check for admin role
        boolean isAdmin = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("username", username); // Add username to model
        model.addAttribute("isAdmin",isAdmin);

        return "dashboard/index";  // Protected page after login
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Retrieve UserDetails after successful authentication
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            model.addAttribute("username", userDetails.getUsername()); // Add username to model

            return "redirect:/dashboard"; // Redirect to dashboard on success
        } catch (BadCredentialsException e) {
            SecurityContextHolder.clearContext(); // Clear the context on failure
            model.addAttribute("error", "Invalid username or password."); // Set error message
            return "dashboard/login"; // Return to login page
        } catch (Exception e) {
            SecurityContextHolder.clearContext(); // Clear the context on failure
            model.addAttribute("error", "An error occurred while processing your request.");
            return "dashboard/login"; // Return to login page
        }
    }

}