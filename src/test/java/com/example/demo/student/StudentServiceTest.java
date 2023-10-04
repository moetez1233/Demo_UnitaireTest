package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)// remplce the same commented code
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Autowired
    private AutoCloseable autoCloseable;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
      //  autoCloseable= MockitoAnnotations.openMocks(this);
        underTest= new StudentService(studentRepository);
    }

    /*@AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }*/

    @Test
    void canGetAllStudents() {
        // when
        underTest.getAllStudents();
        //then
        verify(studentRepository).findAll();

    }

    @Test
    void canAddStudent() {
        // given
        Student student = new Student("moetez","moetez@gmail.com",Gender.MALE);
        //when
        underTest.addStudent(student);
        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudenSaved = studentArgumentCaptor.getValue();
        assertThat(capturedStudenSaved).isEqualTo(student);
    }



    @Test
    void willThrowWhenEmailIsTaken() {
        // given
        Student student = new Student("moetez","moetez@gmail.com",Gender.MALE);
        given(studentRepository.selectExistsEmail(anyString()))
                .willReturn(true);
        //when
        //then
        assertThatThrownBy(() -> underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(   "Email " + student.getEmail() + " taken");


        verify(studentRepository,never()).save(any());


    }
    @Test
    void willThrowWhenDeleteStudent() {
        // given
        Long idStudent = new Long(10);
        // when and then

        given(studentRepository.existsById(idStudent))
                .willReturn(false);
        assertThatThrownBy(() ->underTest.deleteStudent(idStudent))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + idStudent + " does not exists");




    }
    @Test
    void canDeleteUser() {
        // given
        given(studentRepository.existsById(10L))
                .willReturn(true);
       underTest.deleteStudent(10L);
       verify(studentRepository).deleteById(10L);
    }
    @Test
    void canUpdateStudent(){
        // given
        Long idStudent = 10L;
        Student student = new Student("moetez","moetez@gmail.com",Gender.MALE);
        given(studentRepository.existsById(10L))
                .willReturn(false);
        underTest.updateStudent(idStudent,student);
        verify(studentRepository).save(student);

    }

    @Test
    void willThrowWhenUpdateStudent() {
        // given
        Long idStudent = new Long(10);
        Student student = new Student("moetez","moetez@gmail.com",Gender.MALE);
        // when and then

        given(studentRepository.existsById(idStudent))
                .willReturn(true);
        assertThatThrownBy(() ->underTest.updateStudent(idStudent,student))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + idStudent + " does not exists");




    }


}
