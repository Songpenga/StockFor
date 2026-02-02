package hello.core;

import hello.core.MemberInfo.Grade;

//생성자 단축키 alt + insert
public class Member {

    private Long id;
    private String name;
    private Grade grade;

    public Member(Long id, Grade grade, String name) {
        this.grade = grade;
        this.name = name;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

}
