package com.example.coursebookingapp;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;
import com.example.coursebookingapp.MainActivity;
import com.example.coursebookingapp.CourseActivity;


public class JUnitTest {
    Private DatabaseReference mockedDatabaseReference;

    @Before
    public void before() {
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);

        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);

        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);
    }

    @Test
    public void validation_full_name(){
        MainActivity main = new MainActivity();
        boolean result = main.validFullName("Stevieboy123");
        assertTrue(result);
    }

//    @Test
//    public void validation_userNameExists(){
//        assertThat(main.userNameExists.isTrue());
//    }

//    @Test
//    public void validation_courses(){
//
//        str = course.checkValidation("Introduction to Discrete Math", "MAT1348");
//        assertThat(str,null);
//    }

}
