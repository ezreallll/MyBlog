package com.hp.blog.dao;

import com.hp.blog.entity.DbBaseObject;
import org.apache.ibatis.session.SqlSession;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public abstract class BaseDao<T extends DbBaseObject,ID extends Serializable> {

    @Resource(name="sqlWriteSessionTemplate")
    private SqlSession sqlSession;

    private static final String SQL_INSERT = "insert";

    private static final String SQL_UPDATE_BY_ID = "updateById";

    private static final String SQL_DELETE_BY_ID = "deleteById";

    private static final String SQL_SELECT_BY_ID = "selectById";

    /**
     * 类名就是SqlMapping命名空间
     */
    protected final String sqlNamespace = getDefaultSqlNamespace();

    private String getDefaultSqlNamespace() {
        return ((Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
    }

    /**
     * 获取sql全额限定名
     * @param sqlName
     * @return
     */
    protected String getFullSqlName(String sqlName){
         return sqlNamespace+"."+sqlName;
    }

    protected SqlSession getSqlSession() {
        return sqlSession;
    }

    public int insert(T t){
        return this.getSqlSession().insert(getFullSqlName(SQL_INSERT), t);
    }

    public int updateById(T t){
        return this.getSqlSession().update(getFullSqlName(SQL_UPDATE_BY_ID), t);
    }

    public int deleteById(ID id){
        return this.getSqlSession().delete(getFullSqlName(SQL_DELETE_BY_ID), id);
    }

    public T selectById(ID id){
        return this.getSqlSession().selectOne(getFullSqlName(SQL_SELECT_BY_ID), id);
    }

    public int selectCount(Map map){
        int count=0;
        List<T> list=this.getSqlSession().selectList(getFullSqlName("selectCount"),map);
        if(list!=null&&list.size()>0){
            count=list.get(0).getTotal();
        }
        return count;
    }

    public List<T> selectList(Map map){
        return this.getSqlSession().selectList(getFullSqlName("selectList"),map);
    }
}
