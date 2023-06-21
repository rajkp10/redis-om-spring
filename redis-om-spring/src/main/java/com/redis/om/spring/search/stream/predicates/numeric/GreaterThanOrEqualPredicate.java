package com.redis.om.spring.search.stream.predicates.numeric;

import com.redis.om.spring.metamodel.SearchFieldAccessor;
import com.redis.om.spring.search.stream.predicates.BaseAbstractPredicate;
import com.redis.om.spring.search.stream.predicates.jedis.JedisValues;
import redis.clients.jedis.search.querybuilder.Node;
import redis.clients.jedis.search.querybuilder.QueryBuilders;
import redis.clients.jedis.search.querybuilder.Values;

import java.time.*;
import java.util.Date;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

public class GreaterThanOrEqualPredicate<E, T> extends BaseAbstractPredicate<E, T> {

  private final T value;

  public GreaterThanOrEqualPredicate(SearchFieldAccessor field, T value) {
    super(field);
    this.value = value;
  }

  public T getValue() {
    return value;
  }

  @Override
  public Node apply(Node root) {
    if (isEmpty(getValue()))
      return root;
    Class<?> cls = value.getClass();
    if (cls == LocalDate.class) {
      return QueryBuilders.intersect(root).add(getSearchAlias(), JedisValues.ge((LocalDate) getValue()));
    } else if (cls == Date.class) {
      return QueryBuilders.intersect(root).add(getSearchAlias(), JedisValues.ge((Date) getValue()));
    } else if (cls == LocalDateTime.class) {
      return QueryBuilders.intersect(root).add(getSearchAlias(), JedisValues.ge((LocalDateTime) getValue()));
    } else if (cls == Instant.class) {
      return QueryBuilders.intersect(root).add(getSearchAlias(), JedisValues.ge((Instant) getValue()));
    } else if (cls == Integer.class) {
      return QueryBuilders.intersect(root).add(getSearchAlias(), Values.ge(Integer.parseInt(getValue().toString())));
    } else if (cls == Long.class) {
      return QueryBuilders.intersect(root).add(getSearchAlias(), Values.ge(Long.parseLong(getValue().toString())));
    } else if (cls == Double.class) {
      return QueryBuilders.intersect(root).add(getSearchAlias(), Values.ge(Double.parseDouble(getValue().toString())));
    } else {
      return root;
    }
  }

}
