package com.example.demo.data;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Author extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Type(type = "uuid-char")
    private int ID;

    private String name;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<Book> books;


}
