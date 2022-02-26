/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.ptit.hcm.zoroBlogs.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

/**
 *
 * @author zoroONE01
 */
@Entity
@Data
@Table(name = "POST")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
//    @Temporal(javax.persistence.TemporalType.LOCAL)
    private LocalDateTime createAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = true)
    @ColumnDefault("CURRENT_TIMESTAMP")
//    @Temporal(javax.persistence.TemporalType.DATE)
    private LocalDateTime updateAt;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "views", nullable = false)
    @ColumnDefault("0")
    private int views;

    @Column(name = "status", nullable = false)
    @ColumnDefault("1")
    private int status;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_account", nullable = false)
    private Account account;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "POST_TAG_LIST",
            joinColumns = @JoinColumn(name = "id_post"),
            inverseJoinColumns = @JoinColumn(name = "id_post_tag"))
    private List<PostTag> postTag;
}
