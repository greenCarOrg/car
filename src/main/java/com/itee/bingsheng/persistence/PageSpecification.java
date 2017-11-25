/**
 *
 */
package com.itee.bingsheng.persistence;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.transform.ResultTransformer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author gonglei
 */
public class PageSpecification<T> implements Specification<T> {

    private static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    //给默认值
    private int page = 1;
    private int pageSize = 10;
    private int take;
    private int skip;
    private List<SortDescriptor> sort;
    private List<GroupDescriptor> group;
    private List<AggregateDescriptor> aggregate;
    private HashMap<String, Object> data;

    private FilterDescriptor filter;
    private List<FilterDescriptor> groupFilters;

    private CustomSpecification<T> customSpecification;

    public PageSpecification() {
        filter = new FilterDescriptor();
        data = new HashMap<>();
    }

    public PageSpecification(int page, int pageSize) {
        this();
        this.setPage(page);
        this.setPageSize(pageSize);
        this.setTake(pageSize);
        this.setSkip((page - 1) * pageSize);
    }

    private static List<Order> sort(CriteriaBuilder builder, Path path, List<SortDescriptor> sort) {
        List<Order> orders = Lists.newArrayList();
        if (sort != null && !sort.isEmpty()) {
            for (SortDescriptor entry : sort) {
                String field = entry.getField();
                String dir = entry.getDir();
                Path expression = path;
                String[] split = field.split("\\.");
                for (String s : split) {
                    expression = expression.get(s);
                }
                if (dir.equals("asc")) {
                    orders.add(builder.asc(expression));
                } else if (dir.equals("desc")) {
                    orders.add(builder.desc(expression));
                }
            }
        }
        return orders;
    }

    @SuppressWarnings({"serial", "unchecked"})
    private static Map<String, Object> calculateAggregates(Criteria criteria, List<AggregateDescriptor> aggregates) {
        return (Map<String, Object>) criteria.setProjection(createAggregatesProjection(aggregates)).setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] value, String[] aliases) {
                Map<String, Object> result = new HashMap<>();

                for (int i = 0; i < aliases.length; i++) {
                    String alias = aliases[i];
                    Map<String, Object> aggregate;

                    String name = alias.split("_")[0];
                    if (result.containsKey(name)) {
                        ((Map<String, Object>) result.get(name)).put(alias.split("_")[1], value[i]);
                    } else {
                        aggregate = new HashMap<>();
                        aggregate.put(alias.split("_")[1], value[i]);
                        result.put(name, aggregate);
                    }
                }

                return result;
            }

            @SuppressWarnings("rawtypes")
            @Override
            public List transformList(List collection) {
                return collection;
            }
        }).list().get(0);
    }

    private static ProjectionList createAggregatesProjection(List<AggregateDescriptor> aggregates) {
        ProjectionList projections = Projections.projectionList();
        for (AggregateDescriptor aggregate : aggregates) {
            String alias = aggregate.getField() + "_" + aggregate.getAggregate();
            if (aggregate.getAggregate().equals("count")) {
                projections.add(Projections.count(aggregate.getField()), alias);
            } else if (aggregate.getAggregate().equals("sum")) {
                projections.add(Projections.sum(aggregate.getField()), alias);
            } else if (aggregate.getAggregate().equals("average")) {
                projections.add(Projections.avg(aggregate.getField()), alias);
            } else if (aggregate.getAggregate().equals("min")) {
                projections.add(Projections.min(aggregate.getField()), alias);
            } else if (aggregate.getAggregate().equals("max")) {
                projections.add(Projections.max(aggregate.getField()), alias);
            }
        }
        return projections;
    }

    private static Map<String, Object> aggregate(List<AggregateDescriptor> aggregates, FilterDescriptor filters, Session session, Class<?> clazz) {
        Criteria criteria = session.createCriteria(clazz);

        // filter(criteria, filters, clazz);

        return calculateAggregates(criteria, aggregates);
    }

    public HashMap<String, Object> getData() {
        PageSpecification.FilterDescriptor filterDescriptor=this.getFilter();
        if (filterDescriptor == null) {
            List<PageSpecification.FilterDescriptor> filters=filterDescriptor.getFilters();
            if (filters!= null&&filters.size()>0) {
                Map<String, Object> map=new HashMap<String, Object>();
                for(PageSpecification.FilterDescriptor filter:filters){
                    map.put(filter.getField(),filter.getValue());
                }
                this.data.putAll(map);
            }
        }
        return data;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        data.put(key, value);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public List<SortDescriptor> getSort() {
        return sort;
    }

    public void setSort(List<SortDescriptor> sort) {
        this.sort = sort;
    }

    public List<FilterDescriptor> getGroupFilters() {
        return groupFilters;
    }

    public void setGroupFilters(List<FilterDescriptor> groupFilters) {
        this.groupFilters = groupFilters;
    }

    public FilterDescriptor getFilter() {
        return filter;
    }

    public void setFilter(FilterDescriptor filter) {
        this.filter = filter;
    }

    public void setCustomSpecification(CustomSpecification<T> customSpecification) {
        this.customSpecification = customSpecification;
    }

    private Class<?> getEntityClazz() {
        if (customSpecification != null) {
            return customSpecification.getEntityClazz();
        } else {
            return Object.class;
        }
    }

    private void restrict(List<Predicate> predicates, CriteriaBuilder builder, FilterDescriptor filter, Path path, Class<?> clazz) {
        String operator = filter.getOperator();
        String field = filter.getField();
        Object value = filter.getValue();
        boolean ignoreCase = filter.isIgnoreCase();

        try {
            Class<?> type = new PropertyDescriptor(field, clazz).getPropertyType();
            if (type == double.class || type == Double.class) {
                value = Double.parseDouble(value.toString());
            } else if (type == float.class || type == Float.class) {
                value = Float.parseFloat(value.toString());
            } else if (type == long.class || type == Long.class) {
                value = Long.parseLong(value.toString());
            } else if (type == int.class || type == Integer.class) {
                value = Integer.parseInt(value.toString());
            } else if (type == short.class || type == Short.class) {
                value = Short.parseShort(value.toString());
            } else if (type == boolean.class || type == Boolean.class) {
                value = Boolean.parseBoolean(value.toString());
            } else if (type == Date.class) {
                String input = value.toString();
                try{
                    value = DATE_TIME_FORMAT.parse(input);
                }catch (Exception e){
                    value = DATE_FORMAT.parse(input);
                }
            }
        } catch (IntrospectionException | NumberFormatException ignored) {
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Path expression = path;
        String[] split = field.split("\\.");
        for (String s : split) {
            expression = expression.get(s);
        }

        assert expression != null;

        switch (operator) {
            case "eq":
                if (value instanceof String) {
                    String eqValue = value.toString();
                    if (StringUtils.isNotBlank(eqValue)) {
                        predicates.add(builder.equal(expression, eqValue));
                    }
                } else {
                    if (value != null) {
                        predicates.add(builder.equal(expression, value));
                    }
                }
                break;
            case "neq":
                if (value instanceof String) {
                    predicates.add(builder.notLike(expression, value.toString()));
                } else {
                    predicates.add(builder.notEqual(expression, value));
                }
                break;
            case "gt":
                predicates.add(builder.greaterThan(expression, (Comparable) value));
                break;
            case "gte":
                predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) value));
                break;
            case "lt":
                predicates.add(builder.lessThan(expression, (Comparable) value));
                break;
            case "lte":
                predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) value));
                break;
            case "startswith":
                predicates.add(builder.like(expression, "%" + value));
                break;
            case "endswith":
                predicates.add(builder.like(expression, value + "%"));
                break;
            case "contains":
                predicates.add(builder.like(expression, "%" + value + "%"));
                break;
            case "doesnotcontain":
                predicates.add(builder.notLike(expression, value.toString()));
                break;
        }

    }

    private void filter(List<Predicate> predicates, CriteriaBuilder builder, FilterDescriptor filter, Path path, Class<?> clazz) {
        if (filter != null) {
            List<FilterDescriptor> filters = filter.filters;

            if (!filters.isEmpty()) {

                if (!filter.getFilters().isEmpty() && filter.getLogic().equals("or")) {
                    // junction = Restrictions.disjunction();
                }

                for (FilterDescriptor entry : filters) {
                    if (!entry.getFilters().isEmpty()) {
                        filter(predicates, builder, entry, path, clazz);
                    } else {
                        restrict(predicates, builder, entry, path, clazz);
                    }
                }

                // criteria.add(junction);
            }
        }
    }

    private List<?> groupBy(List<?> items, List<GroupDescriptor> group, Class<?> clazz, final Session session, List<SimpleExpression> parentRestrictions) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        if (!items.isEmpty() && group != null && !group.isEmpty()) {
            List<List<SimpleExpression>> restrictions = new ArrayList<List<SimpleExpression>>();

            GroupDescriptor descriptor = group.get(0);
            List<AggregateDescriptor> aggregates = descriptor.getAggregates();

            final String field = descriptor.getField();

            Method accessor = new PropertyDescriptor(field, clazz).getReadMethod();

            Object groupValue = accessor.invoke(items.get(0));

            List<Object> groupItems = createGroupItem(group.size() > 1, clazz, session, result, aggregates, field, groupValue, parentRestrictions);

            List<SimpleExpression> groupRestriction = new ArrayList<SimpleExpression>(parentRestrictions);
            groupRestriction.add(Restrictions.eq(field, groupValue));
            restrictions.add(groupRestriction);

            for (Object item : items) {
                Object currentValue = accessor.invoke(item);

                if (!groupValue.equals(currentValue)) {
                    groupValue = currentValue;
                    groupItems = createGroupItem(group.size() > 1, clazz, session, result, aggregates, field, groupValue, parentRestrictions);

                    groupRestriction = new ArrayList<>(parentRestrictions);
                    groupRestriction.add(Restrictions.eq(field, groupValue));
                    restrictions.add(groupRestriction);
                }
                groupItems.add(item);
            }

            if (group.size() > 1) {
                Integer counter = 0;
                for (Map<String, Object> g : result) {
                    g.put("items", groupBy((List<?>) g.get("items"), group.subList(1, group.size()), clazz, session, restrictions.get(counter++)));
                }
            }
        }

        return result;
    }

    private List<Object> createGroupItem(Boolean hasSubgroups, Class<?> clazz, final Session session, ArrayList<Map<String, Object>> result, List<AggregateDescriptor> aggregates, final String field, Object groupValue, List<SimpleExpression> aggregateRestrictions) {

        Map<String, Object> groupItem = new HashMap<>();
        List<Object> groupItems = new ArrayList<>();

        result.add(groupItem);

        groupItem.put("value", groupValue);
        groupItem.put("field", field);
        groupItem.put("hasSubgroups", hasSubgroups);

        if (aggregates != null && !aggregates.isEmpty()) {
            Criteria criteria = session.createCriteria(clazz);

            // filter(criteria, getFilter(), clazz); // filter the set by the
            // // selected criteria

            SimpleExpression currentRestriction = Restrictions.eq(field, groupValue);

            if (aggregateRestrictions != null && !aggregateRestrictions.isEmpty()) {
                for (SimpleExpression simpleExpression : aggregateRestrictions) {
                    criteria.add(simpleExpression);
                }
            }
            criteria.add(currentRestriction);

            groupItem.put("aggregates", calculateAggregates(criteria, aggregates));
        } else {
            groupItem.put("aggregates", new HashMap<String, Object>());
        }
        groupItem.put("items", groupItems);
        return groupItems;
    }

    private List<?> group(final Criteria criteria, final Session session, final Class<?> clazz) {
        List<?> result = new ArrayList<Object>();
        List<GroupDescriptor> group = getGroup();

        if (group != null && !group.isEmpty()) {
            try {
                result = groupBy(criteria.list(), group, clazz, session, new ArrayList<SimpleExpression>());
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                    | HibernateException | IntrospectionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    private List<SortDescriptor> sortDescriptors() {
        List<SortDescriptor> sort = new ArrayList<>();

        List<GroupDescriptor> groups = getGroup();
        List<SortDescriptor> sorts = getSort();

        if (groups != null) {
            sort.addAll(groups);
        }

        if (sorts != null) {
            sort.addAll(sorts);
        }
        return sort;
    }

    public List<GroupDescriptor> getGroup() {
        return group;
    }

    public void setGroup(List<GroupDescriptor> group) {
        this.group = group;
    }

    public List<AggregateDescriptor> getAggregate() {
        return aggregate;
    }

    public void setAggregate(List<AggregateDescriptor> aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Class<?> entityClass = getEntityClazz();
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (this.customSpecification != null) {
            this.customSpecification.addConditions(root, query, builder, predicates);
            if (Long.class != query.getResultType()) {
                this.customSpecification.buildFetch(root);
            }
        }
        if(getGroupFilters()==null || getGroupFilters().isEmpty()){
            filter(predicates, builder, getFilter(), root, entityClass);

//        List<Order> orders=sort(builder,root,sortDescriptors());
//        if (orders.size() > 0) {
//            query.orderBy(orders);
//        }

            // List<GroupDescriptor> groups = getGroup();

            // if (groups != null && !groups.isEmpty()) {
            // // result.setData(group(criteria, session, clazz));
            // } else {
            // result.setData(criteria.list());
            // }
            //
            // List<AggregateDescriptor> aggregates = getAggregate();
            // if (aggregates != null && !aggregates.isEmpty()) {
            // // result.setAggregates(aggregate(aggregates, getFilter(), session,
            // // clazz));
            // }

            // 将所有条件联合起来
            if (predicates.size() > 0) {
                return ( getFilter().getLogic()==null || getFilter().getLogic().equals("and"))?
                        builder.and(predicates.toArray(new Predicate[predicates.size()]))
                        :builder.or(predicates.toArray(new Predicate[predicates.size()]));
            }
            return builder.conjunction();
        }else {
            List<Predicate> allPredicates = new ArrayList<Predicate>();
            List<FilterDescriptor> filters = getGroupFilters();
            for(FilterDescriptor filter:filters){
                if(!predicates.isEmpty()){
                    predicates.clear();
                }
                filter(predicates, builder, filter, root, entityClass);
                Predicate predicate = filter.getLogic().equals("and")?
                        builder.and(predicates.toArray(new Predicate[predicates.size()]))
                        :builder.or(predicates.toArray(new Predicate[predicates.size()]));
                allPredicates.add(predicate);
            }

            // 将所有条件用and联合起来
            if (predicates.size() > 0) {
                return builder.and(allPredicates.toArray(new Predicate[allPredicates.size()]));
            }
            return builder.conjunction();
        }

    }

    /**
     * 创建分页请求.
     */
    public PageRequest getPageRequest() {
        PageRequest pr;
        if (this.sort != null) {
            Sort sort1 = null;
            for (SortDescriptor descriptor : this.sort) {
                if ("asc".equals(descriptor.getDir())) {
                    sort1 = new Sort(Direction.ASC, descriptor.getField());
                } else {
                    sort1 = new Sort(Direction.DESC, descriptor.getField());
                }
            }
            pr = new PageRequest(this.page - 1, this.pageSize, sort1);
        } else {
            pr = new PageRequest(this.page - 1, this.pageSize);
        }
        return pr;
    }

    public static class SortDescriptor {
        private String field;
        private String dir;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }
    }

    public static class GroupDescriptor extends SortDescriptor {
        private List<AggregateDescriptor> aggregates;

        public GroupDescriptor() {
            aggregates = new ArrayList<>();
        }

        public List<AggregateDescriptor> getAggregates() {
            return aggregates;
        }
    }

    public static class AggregateDescriptor {
        private String field;
        private String aggregate;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getAggregate() {
            return aggregate;
        }

        public void setAggregate(String aggregate) {
            this.aggregate = aggregate;
        }
    }

    public static class FilterDescriptor {
        private String logic;
        private List<FilterDescriptor> filters;
        private String field;
        private Object value;
        private String operator;
        private boolean ignoreCase = true;

        public FilterDescriptor() {
            filters = new ArrayList<>();
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getLogic() {
            return logic;
        }

        public void setLogic(String logic) {
            this.logic = logic;
        }

        public boolean isIgnoreCase() {
            return ignoreCase;
        }

        public void setIgnoreCase(boolean ignoreCase) {
            this.ignoreCase = ignoreCase;
        }

        public List<FilterDescriptor> getFilters() {
            return filters;
        }
    }

}
