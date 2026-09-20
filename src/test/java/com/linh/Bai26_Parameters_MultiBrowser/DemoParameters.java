package com.linh.Bai26_Parameters_MultiBrowser;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class DemoParameters {
    @Test
    @Parameters({"number1" , "number2" }) // mapping voi 2 tham so trong file xml, tu 2 tham so trong file xlm truyen vao 2 bien a, b
    // 2 tham so trong file xlm -> 2 parameters -> 2 bien a, b
    public void testCong2So (int a, int b){
        System.out.println( a +" + "+ b+ " = " +(a+b));
    }

    @Test
    @Parameters({"number1" , "number3" }) // mapping voi 2 tham so trong file xml, tu 2 tham so trong file xlm truyen vao 2 bien a, b
    // 2 tham so trong file xlm -> 2 parameters -> 2 bien a, b
    public void testNhan2So (int a, int b){
        System.out.println(a +" * "+ b+ " = " +(a*b));
    }

    @Test
    // Neu khong co gia tri number thi lay 10 mac dinh
    @Parameters({"number1" , "number4" }) // mapping voi 2 tham so trong file xml, tu 2 tham so trong file xlm truyen vao 2 bien a, b
    // 2 tham so trong file xlm -> 2 parameters -> 2 bien a, b
    public void testChia2So (int a, @Optional("2") int b){
        System.out.println(a +" / "+ b+ " = " +(a/b));
    }



}
