package com.hp.blog.dao;

import com.hp.blog.entity.BlogTypeDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hongpengsun on 17/5/2.
 */
@Repository
public class BlogTypeDao extends BaseDao<BlogTypeDto,Integer>{

    public List<BlogTypeDto> selectTypeList(){
       return this.getSqlSession().selectList(getFullSqlName("selectTypeList"));
    }
}
