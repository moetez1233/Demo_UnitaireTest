package com.example.demo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;

class DemoApplicationTests {

    @Test
    void contextLoads() {
        // given
        int nubr1=5;
        int nubr2=2;
        // when
        int res = Calculator.add(5,2);

        // then
        assertEquals(7,res);
    }


}
 class Calculator {
   public static int add(int a, int b){
        return a+b;
    }
}

