package com.godcoder.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private Boolean enabled;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnore
    private List<Role>  roles=new ArrayList<>();

    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = false)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private List<Board> boards=new ArrayList<>();


}

