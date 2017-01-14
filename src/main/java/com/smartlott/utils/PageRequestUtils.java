package com.smartlott.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Created by greenlucky on 1/14/17.
 */
public class PageRequestUtils {

    PageRequestUtils(){
        throw new AssertionError("Non instantiable");
    }

    public static PageRequest createPageRequest(Pageable pageable){
        Sort localSort = new Sort(Sort.Direction.DESC, "id");
        return new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), localSort);
    }

    public static PageRequest createPageRequest(Pageable pageable, Sort sort){
        return new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }
}
