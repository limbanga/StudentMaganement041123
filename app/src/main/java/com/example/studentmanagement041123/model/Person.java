package com.example.studentmanagement041123.model;

public abstract class Person {

    public static String getImageUriById(String imageId) {
        return "https://firebasestorage.googleapis.com" +
                "/v0/b/studentmanagement041123.appspot.com/o/images%2F" +
                imageId +
                "?alt=media";
    }

    protected String name;
    protected Integer age;
    protected String image;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getImageUrl() {
        return Student.getImageUriById(image);
    }

}
