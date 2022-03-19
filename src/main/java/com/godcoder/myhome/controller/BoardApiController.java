package com.godcoder.myhome.controller;


import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
class BoardApiController {

    private final BoardRepository repository;

    @Autowired
    private BoardRepository boardRepository;


    BoardApiController(BoardRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title
        , @RequestParam(required = false, defaultValue = "") String content) {

        System.out.println("파라미터 : " + title);
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            return repository.findAll();
        }else{
            return repository.findByTitleOrContent(title, content);
        }
    }

    // end::get-aggregate-root[]

    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(EntityExistsException::new);
    }

    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setTitle(newBoard.getTitle());
                    employee.setContent(newBoard.getContent());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }



    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity boardDelete(@PathVariable Long id){
        log.info(" 삭제 되었습니다. :   {} " , id  );
        boardRepository.deleteById(id);
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }


    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete2/{id}")
    @ResponseBody
    public ResponseEntity boardDelete2(@PathVariable Long id){
        log.info(" 삭제 되었습니다. :   {} " , id  );
        boardRepository.deleteById(id);
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }





}