package com.ohgiraffers.section01.common;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("bookDAO") /*DB랑 커넥션을 맺는 데이터 계층*/
public class BookDAOImpl implements BookDAO{ /*BookDAO를 구현한 구현체 (타입이 같다)*/

    private Map<Integer, BookDTO> bookList;

    public BookDAOImpl() {
        bookList = new HashMap<>();
        bookList.put(1, new BookDTO(1, 123, "자바의 정석", "남궁성", "도우출반", new Date()));
        bookList.put(2, new BookDTO(2, 456, "칭찬은 고래도 춤추게 한다", "고래", "고래출판", new Date()));
    }

    @Override
    public List<BookDTO> selectBookList() {
        return new ArrayList<>(bookList.values());
    }

    @Override
    public BookDTO selectOneBook(int seq) {
        return bookList.get(seq);
    }
}
