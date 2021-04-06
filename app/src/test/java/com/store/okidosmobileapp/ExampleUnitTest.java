package com.store.okidosmobileapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

//    @Test
//    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//    }

    private ConfirmFinalOrderActivity confirmFinalOrderActivity;

    @Before
    public void setUp(){
        confirmFinalOrderActivity = new ConfirmFinalOrderActivity();
    }
    @Test
    public void Phone_isCorrect(){
        Boolean result = confirmFinalOrderActivity.isValidPhone("0763838372");
        assertEquals(true,result);
    }
    @Test
    public void Email_isCorrect(){
        Boolean result =confirmFinalOrderActivity.isValidEmail("kasunravilal@gmail.com");
        assertEquals(true,result);
    }

    @Test
    public void Password_isCorrect(){
        Boolean result = MyAccountFragment.isValidPassword("Abc@12345");
        assertEquals(true,result);
    }







}