package com.hp.blog.controller;

import com.hp.blog.common.Enum.ErrorCodeEnum;
import com.hp.blog.entity.BlogDto;
import com.hp.blog.excetion.ServerBizException;
import com.hp.blog.service.BlogService;
import com.hp.blog.utils.ResponseUtil;
import javafx.beans.DefaultProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Created by hongpengsun on 17/5/2.
 */
@RestController
@RequestMapping("/")
public class BlogController {

    @Autowired
    BlogService blogService;

    @RequestMapping("getBlogList")
    public void getBlogList(HttpServletResponse response,@RequestParam(required = false,defaultValue = "0")int type,int page,int size){
        try {
            HashMap map=blogService.getBlogList(type,page,size);
            ResponseUtil.renderSuccessJson(response,"ok",map);
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
        }
    }

    @RequestMapping("getBlogDetail")
    public void getBlogDetail(HttpServletResponse response,int id){
        try {
            BlogDto blogDto=blogService.getBlogDetail(id);
            ResponseUtil.renderSuccessJson(response,"ok",blogDto);
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
        }
    }

    @RequestMapping("getTypeList")
    public void getBlogTypeList(HttpServletResponse response){
        try {
            ResponseUtil.renderSuccessJson(response,"ok",blogService.selectTypeList());
        } catch (Exception e) {
             ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
        }
    }

    @RequestMapping("addType")
    public void addType(HttpServletResponse response,String remark){
        try {
            blogService.addType(remark);
            ResponseUtil.renderSuccessTipJson(response,"添加成功");
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
        }
    }



    @RequestMapping("saveBlog")
    public void saveBlog(HttpServletResponse response,String title,String content,@RequestParam(required = false,defaultValue = "0") int type){
        try {
            blogService.addBlog(title,content,type);
            ResponseUtil.renderSuccessTipJson(response,"保存成功");
        }catch (ServerBizException e){
            ResponseUtil.renderFailJson(response, e.getErrCode());
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
        }
    }

    @RequestMapping("editBlog")
    public void editBlog(HttpServletResponse response,String title,String content,@RequestParam(required = false,defaultValue = "0") int type,int id){
        try {
            blogService.editBlog(title,content,type,id);
            ResponseUtil.renderSuccessTipJson(response,"编辑成功");
        } catch (ServerBizException e){
            ResponseUtil.renderFailJson(response, e.getErrCode());
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
        }
    }


}
