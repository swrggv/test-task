package com.example.demo.models;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "persons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;

    @Column(name = "views")
    private int views = 1;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
