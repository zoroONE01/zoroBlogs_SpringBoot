/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.ptit.hcm.zoroBlogs.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.hcm.zoroBlogs.entity.Account;
import vn.edu.ptit.hcm.zoroBlogs.entity.Post;
import vn.edu.ptit.hcm.zoroBlogs.entity.Profile;

/**
 *
 * @author zoroONE01
 */
@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Integer> {

    Post findAllById(int i);

//    List<Post> findAll(Account account);
    @Query(value = "SELECT x FROM Post x WHERE x.account.email = :email ORDER BY x.createAt DESC")
    List<Post> findPostByEmail(String email);
    
    @Query(value = "SELECT x FROM Post x ORDER BY x.createAt DESC")
    List<Post> findAllPost();
    
}
