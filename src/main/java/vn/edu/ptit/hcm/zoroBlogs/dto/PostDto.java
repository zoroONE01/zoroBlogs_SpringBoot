/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.ptit.hcm.zoroBlogs.dto;

import com.google.gson.Gson;
import static groovyjarjarantlr.build.ANTLR.root;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.validation.Path;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import static org.hibernate.annotations.common.util.StringHelper.root;
import static org.hibernate.internal.util.StringHelper.root;
import static org.hibernate.validator.internal.xml.mapping.ContainerElementTypePath.root;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import vn.edu.ptit.hcm.zoroBlogs.entity.Category;
import vn.edu.ptit.hcm.zoroBlogs.entity.PostTag;

/**
 *
 * @author zoroONE01
 */
@Data
public class PostDto {

    private Integer id;

    @NotEmpty(message = "Hãy nhập tiêu đề cho bài viết")
    @Size(max = 255, message = "Tiêu đề tối đa 100 ký tự")
    private String title;

    @NotNull(message = "Hãy chọn ảnh thumbnail cho bài viết")
    private MultipartFile thumbnail;

    private String thumbnailName;

    private Category category;

    @NotNull
    private String postTags;

//    public String postTagsList;
    @NotEmpty(message = "Hãy thêm nội dung cho bài viết")
    private String contents;

    public void convertForInputPostTags(List<PostTag> postTagsList) {
//        if (postTagsList == null || postTagsList.isEmpty()) {
//            return;
//        }

        String[] postTagNameArr = new String[postTagsList.size()];
        postTagsList.forEach(postTag -> {
            postTagNameArr[postTagsList.indexOf(postTag)] = postTag.getTagName();
        });

//        System.out.println(this.postTagsList);
        Gson gson = new Gson();
        this.setPostTags(gson.toJson(postTagNameArr));

    }

}
