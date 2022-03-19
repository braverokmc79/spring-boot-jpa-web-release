package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.service.BoardService;
import com.godcoder.myhome.validator.BoardValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @Autowired
    private BoardService boardService;


    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page",defaultValue ="1", required = false) Optional<Integer> page,
                     @RequestParam(required = false, defaultValue = "")  String searchText
    ){
      //  List<Board> boards= boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        int pageSize=3;
        Sort sort1=Sort.by("id").descending();
        Pageable pageable=PageRequest.of(page.isPresent() ? page.get()-1 :0, pageSize,  sort1);

        //Page<Board> boards= boardRepository.findAll(pageable);
        Page<Board> boards= boardRepository.findByTitleContainingOrContentContaining(searchText, searchText,pageable);
        int startPage=Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage=Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber()+4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("num", boards.getTotalElements()- ((page.get()-1)*pageSize ));
        model.addAttribute("boards" , boards);
        return "board/list";
    }




    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id, Board board){
        if(id==null){
            model.addAttribute("board", board);
        }else{
            Board board2=boardRepository.findById(id).orElseThrow(EntityExistsException::new);
            model.addAttribute("board", board2);
        }
        return "board/form";
    }


    @PostMapping("/form")
    public String boardSubmit(@Valid Board board, BindingResult  bindingResult ,
                              Authentication authentication,
                              Model model) {
        boardValidator.validate(board, bindingResult);

        if(bindingResult.hasErrors()){
            return "board/form";
        }

        //Authentication a = SecurityContextHolder.getContext().getAuthentication();

        String username=authentication.getName();
        boardService.save(username, board);
        model.addAttribute("board", board);
        return "redirect:/board/list";
    }



    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity boardDelete(@PathVariable Long id, HttpServletRequest request){
        Boolean roleAdmin=request.isUserInRole("ROLE_ADMIN");
        if(roleAdmin){
            log.info(" 권한2 :   관리자2 " );
            boardRepository.deleteById(id);
            return new ResponseEntity<Long>(id,HttpStatus.OK);
        }
        return new ResponseEntity<Long>(id,HttpStatus.BAD_REQUEST);
    }


    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete2/{id}")
    @ResponseBody
    public ResponseEntity boardDelete2(@PathVariable Long id){
        log.info(" 삭제 되었습니다. :   {} " , id  );
        boardRepository.deleteById(id);
        return new ResponseEntity<Long>(id,HttpStatus.OK);
    }




}
