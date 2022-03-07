/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgtt.core.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author Nguyen_Toan
 */
public class AppendData {

    private static final String COMMA = " , ";

    public Pageable getPageable(int page, int size, String sortByProperties, String sortBy) {
        Sort sort = null;
        if (Sort.Direction.ASC.toString().equals(sortBy)) {
            sort = Sort.by(sortByProperties.split(COMMA.trim())).ascending();
        } else {
            sort = Sort.by(sortByProperties.split(COMMA.trim())).descending();
        }
        return PageRequest.of(page, size, sort);
    }

    public StringBuilder appendOrderAndSort(final String sortByProperties, final String sortBy,
            StringBuilder sql) {

        sql.append(" ORDER BY ");
        String[] properties = sortByProperties.split(COMMA.trim());

        int j = 0;
        for (int i = 0; i < properties.length; i++) {
            j += 1;
            sql.append(properties[i]);
            if (j != properties.length) {
                sql.append(COMMA);
            }
        }

        sql.append(" ".concat(sortBy));
        return sql;
    }
}
