package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;


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
    public String boardSubmit(@Valid Board board, BindingResult  bindingResult , Model model) {
        boardValidator.validate(board, bindingResult);

        if(bindingResult.hasErrors()){
            return "board/form";
        }
        boardRepository.save(board);
        model.addAttribute("board", board);
        return "redirect:/board/list";
    }




}
