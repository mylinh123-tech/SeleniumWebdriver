package com.linh.Bai10_Annotations;

import org.testng.annotations.*;

public class DemoAnnotations extends ParentTest {
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("BeforeSuite 1: Chạy trước toàn bộ suite");
    }
    @AfterSuite
    public void afterSuite() {
        System.out.println("AfterSuite 1: Chạy sau toàn bộ suite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("BeforeTest 1: Chạy trước tất cả các test trong một thẻ <test>");
    }
    @AfterTest
    public void afterTest() {
        System.out.println("AfterTest 1 : Chạy sau tất cả các test trong một thẻ <test>");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("BeforeClass 1: Chạy trước tất cả các test trong class này");
        System.out.println("Thực hiện login trước testcase trong class này bằng username và password");
    }
    @AfterClass
    public void afterClass() {
        System.out.println("AfterClass 1:Chạy sau tất cả các test trong class này");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("BeforeMethod 1: Chạy trước mỗi phương thức test - Mở Browswer");
    }
    @AfterMethod
    public void afterMethod() {
        System.out.println("AfterMethod 1: Chạy sau mỗi phương thức test - Đóng Browswer");
    }

    @Test
    public void test_method_01(){
        System.out.println("This is test method 01");
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
