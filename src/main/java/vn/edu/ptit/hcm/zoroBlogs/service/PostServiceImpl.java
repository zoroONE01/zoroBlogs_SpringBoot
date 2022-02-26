/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.ptit.hcm.zoroBlogs.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.ptit.hcm.zoroBlogs.dto.PostDto;
import vn.edu.ptit.hcm.zoroBlogs.entity.Category;
import vn.edu.ptit.hcm.zoroBlogs.entity.Post;
import vn.edu.ptit.hcm.zoroBlogs.entity.PostTag;
import vn.edu.ptit.hcm.zoroBlogs.repository.AccountRepository;
import vn.edu.ptit.hcm.zoroBlogs.repository.CategoryRepository;
import vn.edu.ptit.hcm.zoroBlogs.repository.PostRepository;
import vn.edu.ptit.hcm.zoroBlogs.repository.PostTagRepository;
import vn.edu.ptit.hcm.zoroBlogs.utils.WebUtils;

/**
 *
 * @author zoroONE01
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostTagRepository postTagRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public List<PostTag> findAllPostTag() {
        return postTagRepository.findAll();
    }

    @Override
    public Post craetePost(PostDto postDto, String thumbnail) {
        Post post = new Post();
        String[] tilteTagsList = postDto.getPostTags().split(",");
        List<PostTag> postTagList = new ArrayList<>();
        for (String string : tilteTagsList) {
            PostTag pt = postTagRepository.findByTagName(string);
            if (pt == null) {
                postTagList.add(postTagRepository.save(new PostTag(string)));
            } else {
                postTagList.add(pt);
            }
        }
        post.setTitle(postDto.getTitle());
        post.setThumbnail(thumbnail);
        post.setPostTag(postTagList);
        post.setCategory(postDto.getCategory());
        post.setContents(postDto.getContents());
        post.setAccount(accountRepository.findByEmail(WebUtils.getEmail()));
        postRepository.save(post);
        System.out.println("save: " + post.toString());
        return post;
    }

    @Override
    public PostTag findByTagName(String tagName) {
        return postTagRepository.findByTagName(tagName);
    }

    @Override
    public Optional<Post> findAllById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public Post updatePost(int post_id, PostDto postDto, String thumbnail) {
        Post post = postRepository.getById(post_id);
        String[] tilteTagsList = postDto.getPostTags().split(",");
        List<PostTag> postTagList = new ArrayList<>();
        for (String string : tilteTagsList) {
            PostTag pt = postTagRepository.findByTagName(string);
            if (pt == null) {
                postTagList.add(postTagRepository.save(new PostTag(string)));
            } else {
                postTagList.add(pt);
            }
        }
        post.setTitle(postDto.getTitle());
        post.setThumbnail(thumbnail);
        post.setPostTag(postTagList);
        post.setCategory(postDto.getCategory());
        post.setContents(postDto.getContents());
        post.setUpdateAt(LocalDateTime.now());
        post.setAccount(accountRepository.findByEmail(WebUtils.getEmail()));
        postRepository.save(post);
        System.out.println(post.toString());
        return post;
    }

    @Override
    public void deletePost(int post_id) {
        postRepository.deleteById(post_id);
    }

    @Override
    public List<PostTag> findPostTagListByPost(Post post) {
        return postTagRepository.findByPostId(post.getId());
    }

    @Override
    public Post getById(int id) {

        return postRepository.getById(id);
    }

    @Override
    public List<Post> findPostByEmail(String email) {
        return postRepository.findPostByEmail(email);
    }

    @Override
    public Page<Post> findPaginatedLibrary(Pageable pageable, String email) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Post> list;
        List<Post> postList = postRepository.findPostByEmail(email);
        if (postList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, postList.size());
            list = postList.subList(startItem, toIndex);
        }
        Page<Post> libraryPage
                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), postList.size());
        return libraryPage;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createAt"));
    }

}
