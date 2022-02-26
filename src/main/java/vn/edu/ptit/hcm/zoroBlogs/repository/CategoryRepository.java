/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.ptit.hcm.zoroBlogs.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.hcm.zoroBlogs.entity.Category;

/**
 *
 * @author zoroONE01
 */
@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public Category findAllById(int i);
    
}
