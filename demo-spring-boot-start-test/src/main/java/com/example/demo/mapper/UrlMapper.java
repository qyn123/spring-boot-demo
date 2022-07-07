package com.example.demo.mapper;

import com.example.demo.entity.Book;
import com.example.demo.entity.UrlEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qiaoyanan
 * date:2022/07/04 16:59
 */
@Mapper
public interface UrlMapper {

    void insert(List<UrlEntity> list);

    void insertBook(List<Book> bookList);
}
