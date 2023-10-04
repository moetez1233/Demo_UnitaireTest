package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository underTest;

   @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }
   @Test
   void istShouldReturnTrueIfEmailExist(){
       // given
       Student student = new Student("moetez","moetez@gmail.com",Gender.MALE);
       // when
       underTest.save(student);
       // then
       boolean emailExist = underTest.selectExistsEmail("moetez@gmail.com");
       assertThat(emailExist).isTrue();

   }
    @Test
    void istShouldCheckIfEmailNotExist(){
        // given
        String email = "moetez@gmail.com";
        // when
        boolean emailExist = underTest.selectExistsEmail(email);
        // then
        assertThat(emailExist).isFalse();

    }




}
