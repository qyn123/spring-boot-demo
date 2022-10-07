package com.example.demo.test;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qiaoyanan
 * date:2022/07/27 16:53
 */
public class err {
    public static void main(String[] args) {
       List<Student> students = new ArrayList<>();
        Student student1 = new Student();
        student1.setAge(18);
        student1.setName("赵");
        student1.setSex("男");
        Student student2 = new Student();
        student2.setAge(19);
        student2.setName("钱");
        student2.setSex("女");
        Student student3 = new Student();
        student3.setAge(20);
        student3.setName("孙");
        student3.setSex("女");
        Collections.addAll(students,student1,student2,student3);
        //根据条件过滤 出所有的女同学
        List<Student> result = students.stream().filter(student -> student.getSex().equals("女")).collect(Collectors.toList());
        System.out.println(result);

    }
}
@Data
class Student {
    public Integer age;

    public String name;

    public String sex;
}
