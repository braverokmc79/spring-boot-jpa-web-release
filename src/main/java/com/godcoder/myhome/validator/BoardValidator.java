package com.godcoder.myhome.validator;

import com.godcoder.myhome.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import java.util.Set;

@Component
public class BoardValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

        Board board= (Board) obj;
        if(StringUtils.isEmpty(board.getContent())){
            e.rejectValue("content", "key", "내용을 입력해 주세요.11");
        }
    }


}
