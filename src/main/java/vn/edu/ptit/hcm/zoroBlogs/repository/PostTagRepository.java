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
import vn.edu.ptit.hcm.zoroBlogs.entity.PostTag;

/**
 *
 * @author zoroONE01
 */
@Repository
@Transactional
public interface PostTagRepository extends JpaRepository<PostTag, Integer> {

    public PostTag findAllById(int i);

    public List<PostTag> findByPostId(int post_id);

    public PostTag findByTagName(String tagName);
}
