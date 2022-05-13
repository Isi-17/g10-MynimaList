package com.example.mynimalist.task;

import com.example.mynimalist.list.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Task {

    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Long id;
    private String name;

    @ManyToOne
    private List list;

    public Task(String name, List list){
        this.name = name;
        this.list = list;
    }
}