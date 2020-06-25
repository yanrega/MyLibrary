package com.my.library.challenge.controller;

import com.my.library.challenge.common.ResponseMessage;
import com.my.library.challenge.entity.BookData;
import com.my.library.challenge.entity.BookLoanData;
import com.my.library.challenge.entity.StudentData;
import com.my.library.challenge.repository.BookDataRepository;
import com.my.library.challenge.repository.BookLoanDataRepository;
import com.my.library.challenge.repository.StudentDataRepository;
import com.my.library.challenge.vo.BookLoanDataVO;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping( "/loan" )
public class LoanDataController {
    private static final Logger LOGGER = LogManager.getLogger(LoanDataController.class);

    @Autowired
    BookDataRepository bookDataRepository;

    @Autowired
    StudentDataRepository studentDataRepository;

    @Autowired
    BookLoanDataRepository bookLoanDataRepository;

    @RequestMapping( value = "/add", method = RequestMethod.POST )
    @ApiOperation( value = "Add New Loan" )
    public ResponseEntity<?> addLoan(@RequestBody BookLoanDataVO request) {
        Map<String, Object> mapper = new HashMap<>();
        try{
            if(request.getStudentName()!=null || request.getStudentName().isEmpty()){
                return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("01", "Student Name Cannot Be Empty!"));
            }

            if(request.getBookTitle()!=null || request.getBookTitle().isEmpty()){
                return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("01", "Book Name Cannot Be Empty!"));
            }

            StudentData studentData = studentDataRepository.findByName(request.getStudentName());
            if(studentData==null){
                return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("01", "Student Not Found."));
            }

            if(studentData.getLimit() <= 0){
                return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("01", "Student Have Exceeded Loan Limit"));
            }

            BookData bookData = bookDataRepository.findByTitle(request.getBookTitle());
            if(bookData==null){
                return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("01", "Book Not Found."));
            }

            if(bookData.getTotal() <= 0){
                return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("01", "Book Not Available."));
            }

            Date now = new Date();
            BookLoanData bookLoanData = new BookLoanData();
            bookLoanData.setBookData(bookData);
            bookLoanData.setStudentData(studentData);
            bookLoanData.setLoanDate(now);
            bookLoanData.setLoanLimitDate(addDays(now,4));
            bookLoanDataRepository.save(bookLoanData);

            int totalBefore = bookData.getTotal();
            int total = totalBefore -1;
            bookData.setTotal(total);
            bookDataRepository.save(bookData);

            int limitBefore = studentData.getLimit();
            int limit = limitBefore -1;
            studentData.setLimit(limit);
            studentDataRepository.save(studentData);

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("ERROR ADD LOAN DATA TO DATABASE");
            LOGGER.info("ERROR ==>> ", e.getMessage());
            return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("99", "Error"));
        }

        mapper.put("status", "00");
        mapper.put("message", "Success Add New Loan By Student: "+request.getStudentName()+" For Book: "+request.getBookTitle());
        return ResponseEntity.ok().body(mapper);
    }


    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    @RequestMapping( value = "/get-all", method = RequestMethod.GET )
    @ApiOperation( value = "Get All Loan Data" )
    public ResponseEntity<?> getAllLoanData() {
        Map<String, Object> mapper = new HashMap<>();
        List allData = new ArrayList();
        try{
            List<BookLoanData> bookLoanDataList = bookLoanDataRepository.findAll();
            for(BookLoanData bookLoanData : bookLoanDataList){
                HashMap<String, Object> data = new HashMap<>();
                BookData bookData = bookDataRepository.findByTitle(bookLoanData.getBookData().getTitle());
                StudentData studentData = studentDataRepository.findByName(bookLoanData.getStudentData().getName());

                data.put("id",bookLoanData.getId());
                data.put("title",bookData.getTitle());
                data.put("name",studentData.getName());
                data.put("class",studentData.getClassName());
                data.put("loan_date",bookLoanData.getLoanDate());
                data.put("loan_limit_date",bookLoanData.getLoanLimitDate());
                data.put("return_date",bookLoanData.getReturnDate());
                allData.add(data);
            }

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("ERROR GET ALL LOAN DATA");
            LOGGER.info("ERROR ==>> ", e.getMessage());
            return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("99", "Error"));
        }

        mapper.put("status", "00");
        mapper.put("message", "Success");
        mapper.put("data", allData);
        return ResponseEntity.ok().body(mapper);
    }


    @RequestMapping( value = "/get-by-id/{book_loan_id}", method = RequestMethod.GET )
    @ApiOperation( value = "Get Loan By Id" )
    public ResponseEntity<?> getLoanById(@PathVariable("book_loan_id") Long id) {
        Map<String, Object> mapper = new HashMap<>();
        HashMap<String, Object> data = null;
        try{
            Optional<BookLoanData> bookLoanData = bookLoanDataRepository.findById(id);
            BookData bookData = bookDataRepository.findByTitle(bookLoanData.get().getBookData().getTitle());
            StudentData studentData = studentDataRepository.findByName(bookLoanData.get().getStudentData().getName());
            data = new HashMap<>();
            data.put("id",bookLoanData.get().getId());
            data.put("title",bookData.getTitle());
            data.put("name",studentData.getName());
            data.put("class",studentData.getClassName());
            data.put("loan_date",bookLoanData.get().getLoanDate());
            data.put("loan_limit_date",bookLoanData.get().getLoanLimitDate());
            data.put("return_date",bookLoanData.get().getReturnDate());
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("ERROR GET LOAN DATA");
            LOGGER.info("ERROR ==>> ", e.getMessage());
            return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("99", "Error"));
        }

        mapper.put("status", "00");
        mapper.put("message", "Success");
        mapper.put("data", data);
        return ResponseEntity.ok().body(mapper);
    }

    @RequestMapping( value = "/return-by-id/{book_loan_id}", method = RequestMethod.POST )
    @ApiOperation( value = "Set Book Return" )
    public ResponseEntity<?> setBookReturn(@PathVariable("book_loan_id") Long id) {
        Map<String, Object> mapper = new HashMap<>();
        HashMap<String, Object> data = null;
        try{
            Optional<BookLoanData> bookLoanData = bookLoanDataRepository.findById(id);
            bookLoanData.get().setReturnDate(new Date());
            bookLoanDataRepository.save(bookLoanData.get());

            BookData bookData = bookDataRepository.findByTitle(bookLoanData.get().getBookData().getTitle());
            int totalBefore = bookData.getTotal();
            int total = totalBefore + 1;
            bookData.setTotal(total);

            StudentData studentData = studentDataRepository.findByName(bookLoanData.get().getStudentData().getName());
            int limitBefore = studentData.getLimit();
            int limit = limitBefore + 1;
            studentData.setLimit(limit);
            studentDataRepository.save(studentData);

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("ERROR UPDATE LOAN DATA");
            LOGGER.info("ERROR ==>> ", e.getMessage());
            return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("99", "Error"));
        }

        mapper.put("status", "00");
        mapper.put("message", "Success");
        mapper.put("data", data);
        return ResponseEntity.ok().body(mapper);
    }

    public void checkLoanLimitDate(){
        List<BookLoanData> bookLoanDataList = bookLoanDataRepository.findAll();
        for(BookLoanData bookLoanData : bookLoanDataList){
            long diff = new Date().getTime() - bookLoanData.getReturnDate().getTime();
            float day = (diff / (1000*60*60*24));
            int days = (int) day;
            if(days >=1){
                StudentData studentData = studentDataRepository.findByName(bookLoanData.getStudentData().getName());
                int fineBefore = studentData.getFine();
                int fine = fineBefore + 1000;
                studentData.setFine(fine);
                studentDataRepository.save(studentData);
                LOGGER.info("SUCCESS TO GIVE FINE");
            }
        }
    }
}
