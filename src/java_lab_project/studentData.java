/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_lab_project;

public class studentData {
    private Integer id;
    private String year;
    private String fullName;
    private String gender;
    private String email;
    
    public studentData(Integer id, String year, String fullName, String gender, String email){
        this.id=id;
        this.fullName=fullName;
        this.email=email;
        this.gender=gender;
        this.year=year;
    }


    public Integer getId(){
        return id;
    }
    
    public String getYear(){
        return year;
    }
    
    public String getFullName(){
        return fullName;
    }
    
    public String getGender(){
        return gender;
    }
    
    public String getEmail(){
        return email;
    }
    
    
    
    
}
