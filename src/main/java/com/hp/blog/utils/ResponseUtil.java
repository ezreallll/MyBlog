package com.hp.blog.utils;

import com.google.common.collect.Maps;
import com.hp.blog.common.Enum.ErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class ResponseUtil {


    // header 常量定义
	private static final String ENCODING_PREFIX = "encoding";
	public static final String NOCACHE_PREFIX = "no-cache";
	private static final String ENCODING_DEFAULT = "UTF-8";
	private static final boolean NOCACHE_DEFAULT = true;
	private static int Expires = 100;
    private static final int SUCCESS = 1; // 成功
    private static final int FAIL = -1; // 失败

    /**
     * 直接输出XML.
     * 
     */
	public static void renderXml(HttpServletResponse response, final String xml, final String... headers) {
		render(response, "text/xml", xml, headers);
	}

    /**
     * 直接输出Txt
     * 
     */
	public static void renderText(HttpServletResponse response, final String text, final String... headers) {
		render(response, "text/plain", text, headers);
	}

    public static void renderText(HttpServletResponse response, int status, final String text, final String... headers) {
        render(response, status, "text/plain", text, headers);
    }

    /**
     * 输出JSON，并使用浏览器缓存
     * 
     * @param response
     * @param string
     */
	public static void renderCacheJson(HttpServletResponse response, final String string) {
		render(response, "application/json", string,  "no-cache:false");
	}

    /**
     * 输出html页面
     * 
     * @param response
     * @param string
     * @param headers
     */
	public static void renderHtml(HttpServletResponse response, final String string, final String... headers) {
		//response.setContentType("text/html; charset=UTF-8");
		render(response, "text/html", string, headers);
	}

    /**
     * 输出html页面
     * 
     * @param response
     * @param responseData
     * @param headers
     */
    public static void renderSuccessHtml(HttpServletResponse response, final Object responseData, String tip, final String... headers) {
        Map<String,Object> retMap = Maps.<String, Object>newLinkedHashMap();
        retMap.put("success", SUCCESS);
        retMap.put("tip",tip);
        retMap.put("data",responseData);
        render(response, "text/html", JSonUtil.toJson(retMap), headers);
    }

    /**
     * 输出html页面
     * 
     * @param response
     * @param strResponse
     * @param headers
     */
    public static void renderFailHtml(HttpServletResponse response, final String strResponse, final String tip, final String... headers) {
        Map<String,Object> retMap = Maps.<String, Object>newLinkedHashMap();
        retMap.put("success", FAIL);
        retMap.put("tip",tip);
        render(response, "text/html", JSonUtil.toJson(retMap), headers);
    }

    /**
     * 调用成功，输出JSON
     * 
     * @param response
     * @param tip 提示信息
     * @param responseData 调用成功后,返回的业务数据(必须json格式)
     */
    public static void renderSuccessJson(HttpServletResponse response, final String tip, final Object responseData) {
        Map<String,Object> retMap = Maps.<String, Object>newLinkedHashMap();
        retMap.put("status", SUCCESS);

        if(responseData!=null){
            retMap.put("data",responseData);
        }
        if(tip != null){
            retMap.put("msg",tip);
        }
        render(response, "application/json", JSonUtil.toJson(retMap), "no-cache:false");
    }

    /**
     * 调用成功，输出JSON
     *
     * @param response
     * @param responseData 调用成功后,返回的业务数据(必须json格式)
     */
    public static void renderCodeJson(HttpServletResponse response, final Object responseData) {
        Map<String,Object> retMap = Maps.<String, Object>newLinkedHashMap();
        retMap.put("code", 1000);
        retMap.put("data",responseData);
        render(response, "application/json", JSonUtil.toJson(retMap), "no-cache:false");
    }

    /**
     * 返回带有提示的信息
     * @param response
     * @param tip 请求成功提示信息
     */
    public static void renderSuccessTipJson(HttpServletResponse response, String tip) {
        renderSuccessJson(response,tip,null);
    }

    /**
     * 调用失败，输出JSON
     *
     * @param response
     * @param errCode
     */
    public static void renderFailJson(HttpServletResponse response, ErrorCodeEnum errCode) {
        Map<String,Object> retMap = new LinkedHashMap<String, Object>(2);
        retMap.put("status", FAIL);
        retMap.put("code", errCode.getCode());
        retMap.put("msg", errCode.getRemark());
        render(response, "application/json", JSonUtil.toJson(retMap), "no-cache:false");
    }

    public static void renderFailJson(HttpServletResponse response, String msg) {
        Map<String,Object> retMap = new LinkedHashMap<String, Object>(2);
        retMap.put("status", FAIL);
        retMap.put("msg", msg);
        render(response, "application/json", JSonUtil.toJson(retMap), "no-cache:false");
    }

    /**调用成功，封装分页数据*/
    public static void renderSuccessPageJson(HttpServletResponse response, String tip, int totalRecord, final List recordList) {
        Map<String,Object> retMap = new LinkedHashMap<String, Object>(4);
        HashMap map=new HashMap();
        map.put("list",recordList);
        map.put("total",totalRecord);
        retMap.put("status", SUCCESS);
        retMap.put("msg", tip);
        retMap.put("data", map);
        render(response, "application/json", JSonUtil.toJson(retMap), "no-cache:false");
    }

    /**分页数据调用失败*/
    public static void renderFailPageJson(HttpServletResponse response, String tip, int totalRecord, final ArrayList recordList) {
        Map<String,Object> retMap = new LinkedHashMap<String, Object>(2);
        retMap.put("success", false);
        retMap.put("tip", tip);
        render(response, "application/json", JSonUtil.toJson(retMap), "no-cache:false");
    }

    /**
     * 直接输出内容的简便函数.
     * 
     * eg. render("text/plain", "hello", "encoding:GBK"); render("text/plain",
     * "hello", "no-cache:false"); render("text/plain", "hello", "encoding:GBK",
     * "no-cache:false");
     * 
     * @param headers
     *            可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true .
     */
    private static void render(HttpServletResponse response, final String contentType, final String content, final String... headers) {
         render(response,200,contentType,content,headers);
    }

	public static void render(HttpServletResponse response, int status, final String contentType, final String content,
                              final String... headers) {
	    
		try {
            // 分析headers参数
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName, NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else
                    throw new IllegalArgumentException(headerName
                            + "不是一个合法的header类型");
			}

            // 设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			if (noCache) {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
			}
            response.setStatus(status);
			response.getWriter().write(content);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



    /**
     * 直接输出JSON
     *
     * @param string
     *            json字符串.
     */
    public static void renderJson(HttpServletResponse response, String string, final String... headers) {
        render(response, "application/json", string, headers);
    }

}
