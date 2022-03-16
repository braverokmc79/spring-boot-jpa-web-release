package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model){
        List<Board> boards= boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
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
