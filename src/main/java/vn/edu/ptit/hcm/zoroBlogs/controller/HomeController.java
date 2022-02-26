/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.ptit.hcm.zoroBlogs.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.ptit.hcm.zoroBlogs.dto.UserRegistrationDto;
import vn.edu.ptit.hcm.zoroBlogs.entity.Account;
import vn.edu.ptit.hcm.zoroBlogs.entity.CustomUserDetails;
import vn.edu.ptit.hcm.zoroBlogs.entity.Post;
import vn.edu.ptit.hcm.zoroBlogs.service.PostService;
import vn.edu.ptit.hcm.zoroBlogs.utils.WebUtils;

/**
 *
 * @author zoroONE01
 */
@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    @Autowired
    private PostService postService;

    @ModelAttribute("heroPost")
    public Post heroPost() {
        return postService.findAll().get(0);
    }

    @ModelAttribute("latedPost")
    public List<Post> latedPost() {
        List<Post> latedPost = new ArrayList<>();
        List<Post> postList = postService.findAll();
        for (int i = 1; i < postList.size(); i++) {
            latedPost.add(postList.get(i));
        }
        return latedPost;
    }

    @GetMapping()
    public String getHome(@RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, Model model) {
        model.addAttribute("heroPost", heroPost());
//        System.out.println(heroPost().toString());
        model.addAttribute("latedPost", latedPost());

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Post> libraryPage = postService.findPaginatedLibrary(PageRequest.of(currentPage - 1, pageSize), SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("libraryPage", libraryPage);

        int totalPages = libraryPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", currentPage);
        return "home";
    }

    @GetMapping("/post-view")
    public String getPostView(@RequestParam(value = "post_id", required = true) int post_id, Model model) {
        model.addAttribute("post", postService.findAllById(post_id).get());
        return "postview";
    }

}
