package com.godcoder.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(min=2, max=30, message = "제목은 2자 이상 30 이하여야 합니다.!!!")
    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName ="id")
    @JsonIgnore
    private User user;


}

