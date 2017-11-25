package com.itee.bingsheng.orm;

import com.itee.bingsheng.utils.GenericsUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class BaseDaoImpl<T> implements BaseDao<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * 实体类类型(由构造方法自动赋值)
     */
    private Class<?> entityClass;

    /**
     * 构造方法，根据实例类自动获取实体类类型
     */
    public BaseDaoImpl() {
        entityClass = GenericsUtils.getSuperClassGenricType(getClass());
    }

    /**
     * 根据外置命名查询
     *
     * @param limit
     * @param queryName
     * @param values
     * @return
     */
    @Override
    public List<T> findByNameQuery(int limit, String queryName, Object... values) {
        return findObjectByNameQuery(limit, queryName, values);
    }

    @Override
    public List<T> findByNameQuery(String queryName, Object... values) {
        return findByNameQuery(0, queryName, values);
    }

    @Override
    public List findObjectByNameQuery(int limit, String queryName, Object... values) {
        Query queryObject = entityManager.createNamedQuery(queryName);
        if (limit > 0) {
            queryObject.setMaxResults(limit);
        }

        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                queryObject.setParameter(i + 1, values[i]);
            }
        }
        return queryObject.getResultList();
    }

    @Override
    public List findObjectByNameQuery(String queryName, Object... values) {
        return findObjectByNameQuery(0, queryName, values);
    }

    @Override
    public T findById(Object oid) {
        return (T) this.entityManager.find(entityClass, oid);
    }

    @Override
    public T findUniqueBy(String propertyName, Object value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root root = criteriaQuery.from(entityClass);
        Predicate predicate = criteriaBuilder.equal(root.get(propertyName), value);
        criteriaQuery.where(predicate);
        TypedQuery typedQuery = entityManager.createQuery(criteriaQuery);
        return (T) typedQuery.getSingleResult();
    }

    @Override
    public List<T> findBy(String propertyName, Object value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root root = criteriaQuery.from(entityClass);
        Predicate predicate = criteriaBuilder.equal(root.get(propertyName), value);
        criteriaQuery.where(predicate);
        TypedQuery typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    /**
     * 判断指定的属性是否唯一
     *
     * @param entity
     * @param attributes
     * @return
     * @author moxin
     */
    @Override
    public boolean isUnique(Object entity, @NotNull SingularAttribute<?, ?>... attributes) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);
            Root root = criteriaQuery.from(entityClass);
            List<Predicate> predicates = new ArrayList<Predicate>(attributes.length);
            for (SingularAttribute<?, ?> attribute : attributes) {
                predicates.add(criteriaBuilder.equal(root.get(attribute), PropertyUtils.getProperty(entity, attribute.getName())));
            }
            String idName = root.getModel().getId(root.getModel().getIdType().getJavaType()).getName();
            Object id = PropertyUtils.getProperty(entity, idName);
            if (id != null) {
                predicates.add(criteriaBuilder.notEqual(root.get(idName), id));
            }
            criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

            TypedQuery typedQuery = entityManager.createQuery(criteriaQuery);
            return typedQuery.getResultList().isEmpty();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            ReflectionUtils.handleReflectionException(e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void batchInsert(List list) {
        for (int i = 0; i < list.size(); i++) {
            entityManager.persist(list.get(i));
            if (i % 30 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    @Override
    public List<T> findListBySQL(String sql, Object... objects) {
        try {
            List<T> stu =  entityManager.createNativeQuery(sql,entityClass).getResultList();
            return stu;
        } catch (RuntimeException re) {
            throw re;
        }

    }

}
