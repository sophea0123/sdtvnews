package com.sdtvnews.sdtvnews.controller;

import com.sdtvnews.sdtvnews.config.security.CustomUserDetails;
import com.sdtvnews.sdtvnews.entity.User;
import com.sdtvnews.sdtvnews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Optional;

@Controller
public class IndexController {


    @Autowired
    private AuthenticationManager authenticationManager;  // Handles authentication

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserRepository userRepository;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Optional<User> lstUser= userRepository.findById(userDetails.getId());
        String fullName=lstUser.get().getFirstName()+" "+lstUser.get().getLastName();

        // Retrieve the authorities of the logged-in user
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        // Check for admin role
        boolean isAdmin = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        System.out.println("isAdmin" +  isAdmin);

        // Add username and admin status to the model
        model.addAttribute("username", fullName);
        model.addAttribute("isAdmin", isAdmin);

        return "dashboard/index";  // Return the view for the dashboard
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