/**
 *
 */
package com.itee.bingsheng.orm;

import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.NotNull;
import java.util.List;


public interface BaseDao<T> {

    public List<T> findByNameQuery(int limit, String queryName, Object... values);

    public List<T> findByNameQuery(String queryName, Object... values);

    List findObjectByNameQuery(int limit, String queryName, Object[] values);

    List findObjectByNameQuery(String queryName, Object[] values);

    /**
     * 判断指定的属性是否唯一
     *
     * @param entity
     * @param attributes
     * @return
     * @author moxin
     */
    boolean isUnique(Object entity, @NotNull SingularAttribute<?, ?>... attributes);

    T findById(Object oid);

    T findUniqueBy(String propertyName, Object value);

    List<T> findBy(String propertyName, Object value);

    void batchInsert(List list);

    /** 根据sql语句查询*/
    List<T> findListBySQL(String sql, Object... objects);
}
