package com.linh.Bai10_Annotations;

import org.testng.annotations.*;

public class ParentTest {
    @BeforeSuite
    public void beforeSuiteParent() {
        System.out.println("PARENT ☀\uFE0F- BeforeSuite 1: Chạy trước toàn bộ suite");
    }
    @AfterSuite
    public void afterSuiteParent() {
        System.out.println("PARENT ☀\uFE0F- AfterSuite 1: Chạy sau toàn bộ suite");
    }

    @BeforeTest
    public void beforeTestParent() {
        System.out.println("PARENT ☀\uFE0F - BeforeTest 1: Chạy trước tất cả các test trong một thẻ <test>");
    }
    @AfterTest
    public void afterTestParent() {
        System.out.println("PARENT ☀\uFE0F- AfterTest 1 : Chạy sau tất cả các test trong một thẻ <test>");
    }

    @BeforeClass
    public void beforeClassParent() {
        System.out.println("PARENT ☀\uFE0F- BeforeClass 1: Chạy trước tất cả các test trong class này");
        System.out.println("PARENT ☀\uFE0F- Thực hiện login trước testcase trong class này bằng username và password");
    }
    @AfterClass
    public void afterClassParent() {
        System.out.println("PARENT ☀\uFE0F- AfterClass 1:Chạy sau tất cả các test trong class này");
    }

    @BeforeMethod
    public void beforeMethodParent() {
        System.out.println("PARENT ☀\uFE0F-BeforeMethod 1: Chạy trước mỗi phương thức test - Mở Browswer");
    }
    @AfterMethod
    public void afterMethodParent() {
        System.out.println("PARENT ☀\uFE0F-AfterMethod 1: Chạy sau mỗi phương thức test - Đóng Browswer");
    }



}
