package com.example.mvcdemo.controller;

import com.example.mvcdemo.domain.Book;
import com.example.mvcdemo.domain.Size;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;



@Slf4j
@RequestMapping("/mongo")
@RestController
public class MongoController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping
    public void addBook(){
        Size size = new Size();
        size.setC("red");
        size.setS("mid");
        Book book = new Book(null, "格林童话", null, null, BigDecimal.valueOf(20), 10,  size);
        Book insert = mongoTemplate.insert(book);
        log.info("addBook :{}", insert);
    }

    @PostMapping("/addBatchBook")
    public Collection<Book> addBatchBook(){
        List<Book> list = new ArrayList<>();
        for(int i = 20; i < 30; i++){
            Size size = new Size();
            size.setC("red");
            size.setS("mid");
            Book book = new Book(null, "格林童话" + i, null, null, BigDecimal.valueOf(i), i, size);
            list.add(book);
        }

        return mongoTemplate.insert(list, Book.class);
    }

    @GetMapping
    public Book getBook(@RequestParam String name){

        Book book = mongoTemplate.findOne(query(where("name").is(name)), Book.class);

        return book;
    }

    @GetMapping("getBookLike")
    public Book getBookLike(@RequestParam String name){

        Book book = mongoTemplate.findOne(query(where("name").regex("^格林.*")), Book.class);

        return book;
    }

    @PutMapping
    public void updateBook(){

        UpdateResult updateResult = mongoTemplate.updateFirst(query(where("name").is("格林童话")), update("size.s", "max"), Book.class);

        log.info("updateBook-updateResult:{}", updateResult);
    }

    @PutMapping("/findAndModifyBook")
    public Book findAndModifyBook(){
        Book book = mongoTemplate.findAndModify(query(where("name").is("格林童话")), update("size.s", "min"), new FindAndModifyOptions().returnNew(true), Book.class);

        return book;
    }

    @DeleteMapping
    public void deleteBook(){
        mongoTemplate.remove(query(where("price").is(BigDecimal.valueOf(20))), Book.class);
    }

}
