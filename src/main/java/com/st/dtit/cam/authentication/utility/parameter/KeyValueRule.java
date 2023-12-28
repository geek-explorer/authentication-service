package com.st.dtit.cam.authentication.utility.parameter;

import com.st.dtit.cam.authentication.enums.QueryOperator;
import com.st.dtit.cam.authentication.enums.TableProperty;
import org.hibernate.HibernateException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.st.dtit.cam.authentication.constant.CommonConstant.FIELD_VALUE_NULL;

public class KeyValueRule<T> implements QueryRule<T> {

    private static final long serialVersionUID = 1489370324678027891L;

    private Map<TableProperty, Object> propertyValueMap;

    private KeyValueRule(Map<TableProperty, Object> propertyValueMap){
        this.propertyValueMap = propertyValueMap;
    }

    public static KeyValueRule has(Map<TableProperty, Object> propertyValueMap){
            return new KeyValueRule(propertyValueMap);
    }

    @Override
    public Predicate apply(Root<T> tableDefRoot, CriteriaBuilder builder, QueryOperator queryOperator) throws HibernateException {
            Predicate[] predicates = null;
            if (QueryOperator.AND.equals(queryOperator)){
                predicates = propertyValueMap.entrySet()
                        .stream()
                        .map(criteria -> getPredicateCondition(tableDefRoot, builder, criteria.getKey(), criteria.getValue()))
                        .toArray(Predicate[]::new);
            }else{
                predicates = propertyValueMap.entrySet()
                        .stream()
                        .map(criteria -> getPredicateCondition(tableDefRoot, builder, criteria.getKey(), criteria.getValue()))
                        .toArray(Predicate[]::new);
            }
            return builder.and(predicates);
    }

    private Predicate getPredicateCondition(Root<T> tableDefRoot, CriteriaBuilder builder, TableProperty property, Object value) {
            if (null != value && value instanceof String && FIELD_VALUE_NULL.equalsIgnoreCase((String) value)) {
                return builder.isNull(getPredicateExpressions(tableDefRoot, property.getField()));
            }
            if (null != value && value instanceof String && ((String) value).contains("*")) {
                final String stringValue = ((String) value).replace("*", "") + "%";
                return builder.like(getPredicateExpressions(tableDefRoot, property.getField()), stringValue);
            } else if (null != value && value instanceof String[]) {
                final List<String> stringValues = new ArrayList<>(Arrays.asList((String[]) value));
                if (containWildcardIndicator(stringValues)){
                     return  getWildCardValuesPredicate(tableDefRoot, builder, property, stringValues);
                }else {
                     return builder.trim(getPredicateExpressions(tableDefRoot, property.getField())).in(stringValues);
                }
            } else if (null != value && value instanceof Long[]) {
                final List<Long> longValues = new ArrayList<>(Arrays.asList((Long[]) value));
                return builder.trim(getPredicateExpressions(tableDefRoot, property.getField())).in(longValues);
            } else if (null != value && value instanceof Integer[]){
                final List<Integer> integerValues = new ArrayList<>(Arrays.asList((Integer[]) value));
                return builder.trim(getPredicateExpressions(tableDefRoot, property.getField())).in(integerValues);
            }else {
                return builder.equal(getPredicateExpressions(tableDefRoot, property.getField()), value);
            }
    }

    private Predicate getWildCardValuesPredicate(Root<T> tableDefRoot, CriteriaBuilder builder, TableProperty property, List<String> stringValues){
            final Predicate[] predicates = stringValues.stream()
                                                       .map(value ->  getPredicateCondition(tableDefRoot, builder, property, value))
                                                       .toArray(Predicate[]::new);
            return builder.or(predicates);
    }

    private boolean containWildcardIndicator(final List<String> values){
            return values.stream()
                         .filter(value -> value.contains("*"))
                         .findAny()
                         .isPresent();
    }

    private Expression<String> getPredicateExpressions(final Root<T> tableDefRoot, final String tableProperty){
            final String[] tableProperties =  tableProperty.split("\\.");
            if (tableProperties.length > 1){
                return tableDefRoot.get(tableProperties[0]).get(tableProperties[1]);
            }else {
                return tableDefRoot.get(tableProperties[0]);
            }
    }
}
