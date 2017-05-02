package com.hp.blog.service;

import com.hp.blog.common.Enum.ErrorCodeEnum;
import com.hp.blog.dao.BlogDao;
import com.hp.blog.dao.BlogTypeDao;
import com.hp.blog.entity.BlogDto;
import com.hp.blog.entity.BlogTypeDto;
import com.hp.blog.excetion.ServerBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by hongpengsun on 17/5/2.
 */
@Service
public class BlogService {

    @Autowired
    private BlogTypeDao  blogTypeDao;

    @Autowired
    private BlogDao blogDao;

    public HashMap getBlogList(int type,int page,int size){
        HashMap resultMap=new HashMap();

        HashMap map=new HashMap();
        map.put("type",type);
        map.put("page",(page-1)*size);
        map.put("size",size);

        resultMap.put("items",blogDao.selectList(map));
        resultMap.put("total",blogDao.selectCount(map));
        return resultMap;
    }

    public BlogDto getBlogDetail(int id){
        BlogDto blogDto=new BlogDto();
        blogDto=blogDao.selectById(id);
        return blogDto;
    }

    public List<BlogTypeDto> selectTypeList(){
       return blogTypeDao.selectTypeList();
    }

    public boolean addType(String remark){
        int count =0;
        BlogTypeDto blogTypeDto=new BlogTypeDto();
        blogTypeDto.setRemark(remark);
        blogTypeDao.insert(blogTypeDto);
        return count>0;
    }

    public boolean addBlog(String title,String content,int type) throws Exception{
        int count =0;
        if(type==0){
            throw new ServerBizException(ErrorCodeEnum.BLOG_TYPE_ERROR);
        }
        BlogDto blogDto=new BlogDto();
        blogDto.setTitle(title);
        blogDto.setContent(content);
        blogDto.setType(type);
        blogDto.setCreate_time(String.valueOf(System.currentTimeMillis()/1000));
        blogDao.insert(blogDto);
        return count>0;
    }


}
