package com.linh.Bai10_Annotations;

import org.testng.annotations.*;

public class DemoAnnotations_2 {
//    @BeforeSuite
//    public void beforeSuite() {
//        System.out.println("beforeSuite2: Chạy trước toàn bộ suite");
//    }
//    @AfterSuite
//    public void afterSuite() {
//        System.out.println("afterSuite 2: Chạy sau toàn bộ suite");
//    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("BeforeTest 2: Chạy trước tất cả các test trong một thẻ <test>");
    }
    @AfterTest
    public void afterTest() {
        System.out.println("AfterTest 2 : Chạy sau tất cả các test trong một thẻ <test>");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("BeforeClass 2: Chạy trước tất cả các test trong class này");
        System.out.println("Thực hiện login trước testcase bằng cách get cookie");
    }
    @AfterClass
    public void afterClass() {
        System.out.println("AfterClass 2:Chạy sau tất cả các test trong class này");
    }


    @BeforeGroups ("smoke")
    public void beforeGroup(){
        System.out.println("BeforeGroup 2: chạy trước group smoke");
    }

    @Test (groups ="smoke")
    public void test_method_01(){
        System.out.println("This is test method 01");
    }
    @BeforeMethod
    public void beforeMethod() {
        System.out.println("BeforeMethod 2: Chạy trước mỗi phương thức test - Mở Browswer");
    }
    @AfterMethod
    public void afterMethod() {
        System.out.println("AfterMethod 2: Chạy sau mỗi phương thức test - Đóng Browswer");
    }

    @Test
    public void test_method_02(){
        System.out.println("This is test method 02");
    }
    @Test
    public void test_method_03(){
        System.out.println("This is test method 03");
    }



}
