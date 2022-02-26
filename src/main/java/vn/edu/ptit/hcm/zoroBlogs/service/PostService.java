/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.ptit.hcm.zoroBlogs.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.ptit.hcm.zoroBlogs.dto.PostDto;
import vn.edu.ptit.hcm.zoroBlogs.entity.Category;
import vn.edu.ptit.hcm.zoroBlogs.entity.Post;
import vn.edu.ptit.hcm.zoroBlogs.entity.PostTag;

/**
 *
 * @author zoroONE01
 */
public interface PostService {

    List<Category> findAllCategory();

    List<PostTag> findAllPostTag();

    Optional<Post> findAllById(int id);

    Post getById(int id);

    Post craetePost(PostDto postDto, String thumbnail);

    Post updatePost(int post_id, PostDto postDto, String thumbnail);

    void deletePost(int post_id);

    PostTag findByTagName(String tagName);

    List<PostTag> findPostTagListByPost(Post post);

    List<Post> findPostByEmail(String email);

    Page<Post> findPaginatedLibrary(Pageable pageable, String email);
    
    List<Post> findAll();
}
