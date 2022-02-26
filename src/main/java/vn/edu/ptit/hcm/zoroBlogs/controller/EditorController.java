/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.ptit.hcm.zoroBlogs.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.ptit.hcm.zoroBlogs.dto.PostDto;
import vn.edu.ptit.hcm.zoroBlogs.entity.Category;
import vn.edu.ptit.hcm.zoroBlogs.entity.Post;
import vn.edu.ptit.hcm.zoroBlogs.service.PostService;

/**
 *
 * @author zoroONE01
 */
@Controller
@RequestMapping("/editor")
public class EditorController {

    @Autowired
    private PostService postService;

    @Value("${upload.path}")
    private String fileUpload;

    @ModelAttribute("post")
    public PostDto postDto() {
        return new PostDto();
    }

    @ModelAttribute("listCategory")
    public List<Category> getListCategory() {
        return postService.findAllCategory();
    }

    @GetMapping("/create-new-post")
    public String createNewPost(@RequestParam(value = "error", required = false) boolean error) {
        return "editor";
    }

    @GetMapping("/edit-post")
    public String editPost(@RequestParam(value = "post_id", required = true) int post_id, @RequestParam(value = "error", required = false) boolean error, Model model) throws IOException {
        Post post = postService.findAllById(post_id).get();
//        StringBuilder sb = new StringBuilder();
        System.out.println("find: " + post.toString());
        PostDto postDto = new PostDto();
        postDto.setId(post_id);
        postDto.setTitle(post.getTitle());
        postDto.setCategory(post.getCategory());
        postDto.convertForInputPostTags(post.getPostTag());
        postDto.setThumbnailName(post.getThumbnail());
        postDto.setContents(post.getContents());
        System.out.println("edit: " + postDto.toString());
        model.addAttribute("post", postDto);
        return "editor";
    }

    @PostMapping("/create-new-post")
    public String createNewPost(@ModelAttribute("post") @Valid final PostDto postDto,
            final BindingResult result,
            Errors errors, RedirectAttributes redirectAttributes, Model model, HttpSession session,
            HttpServletRequest request
    ) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.post", result);
            redirectAttributes.addFlashAttribute("post", postDto);
            return "redirect:/editor/create-new-post?success=false";
        }
        MultipartFile multipartFile = postDto.getThumbnail();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(postDto.getThumbnail().getBytes(), new File(this.fileUpload + fileName));
        } catch (IOException e) {
        }

        return "redirect:/editor/edit-post?post_id=" + postService.craetePost(postDto, fileName).getId();
    }

    @PostMapping("/update-post")
    public String updatePost(@ModelAttribute("post") @Valid final PostDto postDto,
            final BindingResult result,
            Errors errors, RedirectAttributes redirectAttributes, Model model, HttpSession session,
            HttpServletRequest request, @RequestParam(value = "post_id", required = true) int post_id
    ) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.post", result);
            redirectAttributes.addFlashAttribute("post", postDto);
            return "redirect:/editor/edit-post?post_id=" + post_id + "&success=false";
        }
        String fileName = null;
        if (postDto.getThumbnail() != null) {
            MultipartFile multipartFile = postDto.getThumbnail();
            fileName = multipartFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(postDto.getThumbnail().getBytes(), new File(this.fileUpload + fileName));
            } catch (IOException e) {
            }
        }
        if (fileName == null || fileName.isEmpty()) {
            fileName = postService.findAllById(post_id).get().getThumbnail();
        }
        System.out.println(fileName);
        return "redirect:/editor/edit-post?post_id=" + postService.updatePost(post_id, postDto, fileName).getId();
    }

    @PostMapping("/delete-post")
    public String deletePost(@ModelAttribute("post") PostDto postDto,
            @RequestParam(value = "post_id", required = true) int post_id) {

        postService.deletePost(post_id);
        return "redirect:/editor/create-new-post";
    }
}
