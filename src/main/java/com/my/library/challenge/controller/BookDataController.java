package com.my.library.challenge.controller;

import com.my.library.challenge.common.ResponseMessage;
import com.my.library.challenge.entity.BookData;
import com.my.library.challenge.repository.BookDataRepository;
import com.my.library.challenge.vo.BookDataVO;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping( "/book" )
public class BookDataController {
    private static final Logger LOGGER = LogManager.getLogger(BookDataController.class);

    @Autowired
    BookDataRepository bookDataRepository;

    @RequestMapping( value = "/add", method = RequestMethod.POST )
    @ApiOperation( value = "Add New Book" )
    public ResponseEntity<?> addBook(@RequestBody BookDataVO request) {
        Map<String, Object> mapper = new HashMap<>();
        try{
            BookData book = new BookData();
            book.setTitle(request.getTitle());
            book.setAuthor(request.getAuthor());
            book.setPublisher(request.getPublisher());
            book.setPublicationYear(request.getPublicationYear());
            book.setTotal(request.getTotal());
            bookDataRepository.save(book);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("ERROR ADD BOOK DATA TO DATABASE");
            LOGGER.info("ERROR ==>> ", e.getMessage());
            return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("99", "Error"));
        }

        mapper.put("status", "00");
        mapper.put("message", "Success Add New Book: "+request.getTitle());
        return ResponseEntity.ok().body(mapper);
    }

    @RequestMapping( value = "/get-all", method = RequestMethod.GET )
    @ApiOperation( value = "Get All Book" )
    public ResponseEntity<?> getAllBook() {
        Map<String, Object> mapper = new HashMap<>();
        List allData = new ArrayList();
        try{
            List<BookData> bookDataList = bookDataRepository.findAll();
            for(BookData bookData : bookDataList){
                HashMap<String, Object> data = new HashMap<>();
                data.put("id",bookData.getId());
                data.put("title",bookData.getTitle());
                data.put("publisher",bookData.getPublisher());
                data.put("publication_year",bookData.getPublicationYear());
                data.put("author",bookData.getAuthor());
                data.put("total",bookData.getTotal());
                allData.add(data);
            }

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("ERROR GET ALL BOOK DATA");
            LOGGER.info("ERROR ==>> ", e.getMessage());
            return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("99", "Error"));
        }

        mapper.put("status", "00");
        mapper.put("message", "Success");
        mapper.put("data", allData);
        return ResponseEntity.ok().body(mapper);
    }

    @RequestMapping( value = "/get-by-id/{book_id}", method = RequestMethod.GET )
    @ApiOperation( value = "Get Book By Id" )
    public ResponseEntity<?> getBookById(@PathVariable("book_id") Long id) {
        Map<String, Object> mapper = new HashMap<>();
        HashMap<String, Object> data = null;
        try{
            Optional<BookData> bookData = bookDataRepository.findById(id);
            data = new HashMap<>();
            data.put("id",bookData.get().getId());
            data.put("title",bookData.get().getTitle());
            data.put("publisher",bookData.get().getPublisher());
            data.put("publication_year",bookData.get().getPublicationYear());
            data.put("author",bookData.get().getAuthor());
            data.put("total",bookData.get().getTotal());
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("ERROR GET BOOK DATA");
            LOGGER.info("ERROR ==>> ", e.getMessage());
            return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("99", "Error"));
        }

        mapper.put("status", "00");
        mapper.put("message", "Success");
        mapper.put("data", data);
        return ResponseEntity.ok().body(mapper);
    }
}
