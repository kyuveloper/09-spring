package com.ohgiraffers.section01.subsection01.field;

import com.ohgiraffers.section01.common.BookDAO;
import com.ohgiraffers.section01.common.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bookServiceField") /*서비스 계층(비즈니스 로직이 수행되는 공간)임을 알려줌*/
public class BookService {

    @Autowired /*Spring Context에서 BooDAO에 들어갈 같은 타입의 값을 찾아서 자동으로 넣어준다. (의존성 주입) /// 의존성 자동주입을 하면 안되는 상황도 존재..*/
    private BookDAO bookDAO;

    public List<BookDTO> selectAllBook() {
        return bookDAO.selectBookList();
    }

    public BookDTO selectOneBook(int seq) {
        return bookDAO.selectOneBook(seq);
    }
}
