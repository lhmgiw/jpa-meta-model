package com.lhmgiw.jpametamodel.repository.custom;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ListSlicingDAO<T> {

    String ALL = "all";
    String START = "start";
    String LIMIT = "limit";
    String SORT_BY = "sortBy";

    Optional<List<T>> findAllParameters(Map<String, Object> paraMap);

    Long rowCount(Map<String, Object> paraMap);
}
