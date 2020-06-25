package com.my.library.challenge.controller;

import com.my.library.challenge.common.ResponseMessage;
import com.my.library.challenge.entity.StudentData;
import com.my.library.challenge.repository.StudentDataRepository;
import com.my.library.challenge.vo.StudentDataVO;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping( "/student" )
public class StudentDataController {
    private static final Logger LOGGER = LogManager.getLogger(StudentDataController.class);

    @Autowired
    StudentDataRepository studentDataRepository;

    @RequestMapping( value = "/add", method = RequestMethod.POST )
    @ApiOperation( value = "Add New Student" )
    public ResponseEntity<?> addStudent(@RequestBody StudentDataVO request) {
        Map<String, Object> mapper = new HashMap<>();
        try{
            StudentData studentData = new StudentData();
            studentData.setName(request.getName());
            studentData.setAddress(request.getAddress());
            studentData.setPhone(request.getPhone());
            studentData.setClassName(request.getClassName());
            studentData.setFine(0);
            studentData.setLimit(5);
            studentDataRepository.save(studentData);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("ERROR ADD STUDENT DATA TO DATABASE");
            LOGGER.info("ERROR ==>> ", e.getMessage());
            return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("99", "Error"));
        }

        mapper.put("status", "00");
        mapper.put("message", "Success Add New Student: "+request.getName());
        return ResponseEntity.ok().body(mapper);
    }

    @RequestMapping( value = "/get-all", method = RequestMethod.GET )
    @ApiOperation( value = "Get All Student" )
    public ResponseEntity<?> getAllStudent() {
        Map<String, Object> mapper = new HashMap<>();
        List allData = new ArrayList();
        try{
            List<StudentData> studentDataList = studentDataRepository.findAll();
            for(StudentData studentData : studentDataList){
                HashMap<String, Object> data = new HashMap<>();
                data.put("id",studentData.getId());
                data.put("name",studentData.getName());
                data.put("address",studentData.getAddress());
                data.put("phone",studentData.getPhone());
                data.put("class_name",studentData.getClassName());
                data.put("fine",studentData.getFine());
                data.put("limit",studentData.getLimit());
                allData.add(data);
            }

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("ERROR GET ALL STUDENT DATA");
            LOGGER.info("ERROR ==>> ", e.getMessage());
            return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("99", "Error"));
        }

        mapper.put("status", "00");
        mapper.put("message", "Success");
        mapper.put("data", allData);
        return ResponseEntity.ok().body(mapper);
    }


    @RequestMapping( value = "/get-by-id/{student_id}", method = RequestMethod.GET )
    @ApiOperation( value = "Get Student By Id" )
    public ResponseEntity<?> getStudentById(@PathVariable("student_id") Long id) {
        Map<String, Object> mapper = new HashMap<>();
        HashMap<String, Object> data = null;
        try{
            Optional<StudentData> studentData = studentDataRepository.findById(id);
            data = new HashMap<>();
            data.put("id",studentData.get().getId());
            data.put("name",studentData.get().getName());
            data.put("address",studentData.get().getAddress());
            data.put("phone",studentData.get().getPhone());
            data.put("class_name",studentData.get().getClassName());
            data.put("fine",studentData.get().getFine());
            data.put("limit",studentData.get().getLimit());
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("ERROR GET STUDENT DATA");
            LOGGER.info("ERROR ==>> ", e.getMessage());
            return ResponseEntity.ok().body(ResponseMessage.responseMissingParam("99", "Error"));
        }

        mapper.put("status", "00");
        mapper.put("message", "Success");
        mapper.put("data", data);
        return ResponseEntity.ok().body(mapper);
    }
}
