package com.itee.bingsheng.persistence;

import com.itee.bingsheng.utils.GenericsUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


@SuppressWarnings("hiding")
public class CustomSpecification<T> {

    private Class<T> entityClazz;

    public CustomSpecification() {
        entityClazz = GenericsUtils.getSuperClassGenricType(getClass());
    }

    public void addConditions(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder, List<Predicate> predicates) {

    }

    public void buildFetch(Root<T> root) {

    }

    public Class<T> getEntityClazz() {
        return entityClazz;
    }
}
