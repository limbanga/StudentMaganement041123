package com.example.studentmanagement041123.model;

public class Student extends Person {


    protected String image;

    public String getImage() {
        return image;
    }

    public String getImageUrl() {
        return Student.getImageUriById(image);
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Student() {
    }

    public Student(String name, Integer age, String image) {
        this.name = name;
        this.age = age;
        this.image = image;
    }

    public static String getImageUriById(String imageId) {
        return "https://firebasestorage.googleapis.com" +
                "/v0/b/studentmanagement041123.appspot.com/o/images%2F" +
                imageId +
                "?alt=media";
    }
}
