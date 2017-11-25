/**
 *
 */
package com.itee.bingsheng.orm;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * 自定义命名策略
 *
 * @author moxin
 */
public class UnderscoresNamingStrategy extends ImprovedNamingStrategy {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String columnName(String columnName) {
        return ImprovedNamingStrategy.addUnderscores(columnName).toUpperCase();
    }

    @Override
    public String tableName(String tableName) {
        return ImprovedNamingStrategy.addUnderscores(tableName).toLowerCase();
    }

    @Override
    public String classToTableName(String className) {
        return tableName(className);
    }

    @Override
    public String propertyToColumnName(String propertyName) {
        return ImprovedNamingStrategy.addUnderscores(propertyName).toUpperCase();
    }

}
