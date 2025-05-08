// package org.econsult.healthcare.entity;

// import jakarta.persistence.Entity;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.Id;
// import jakarta.persistence.OneToOne;


// @Entity
// public class User {
//     @Id @GeneratedValue
//     private Long id;

//     private String username;
//     private String password;

//     @Enumerated(EnumType.STRING)
//     private Role role;

//     @OneToOne(mappedBy = "user")
//     private Patient patient;

//     @OneToOne(mappedBy = "user")
//     private Doctor doctor;

//     public User() {
//     }
//     public User(String username, String password, Role role) {
//         this.username = username;
//         this.password = password;
//         this.role = role;
//     }
//     public Long getId() {
//         return id;
//     }
//     public void setId(Long id) {
//         this.id = id;
//     }
//     public String getUsername() {
//         return username;
//     }
//     public void setUsername(String username) {
//         this.username = username;
//     }
//     public String getPassword() {
//         return password;
//     }
//     public void setPassword(String password) {
//         this.password = password;
//     }
//     public Role getRole() {
//         return role;
//     }
//     public void setRole(Role role) {
//         this.role = role;
//     }
//     public Patient getPatient() {
//         return patient;
//     }
//     public void setPatient(Patient patient) {
//         this.patient = patient;
//     }
//     public Doctor getDoctor() {
//         return doctor;
//     }
//     public void setDoctor(Doctor doctor) {
//         this.doctor = doctor;
//     }
// }
