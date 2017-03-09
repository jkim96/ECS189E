import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jessica on 3/8/17.
 */
public class TestStudent {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
        this.admin.createClass("Test", 2017, "Instructor", 5);
    }

    @Test
    public void testMakeStudent() {
        this.student.registerForClass("Student", "Test", 2017);
        assertTrue(this.student.isRegisteredFor("Student", "Test", 2017));
    }

    @Test //over capacity
    public void testMakeStudent1(){
        this.student.registerForClass("Student1","Test", 2017);
        this.student.registerForClass("Student2","Test", 2017);
        this.student.registerForClass("Student3","Test", 2017);
        this.student.registerForClass("Student4","Test", 2017);
        this.student.registerForClass("Student5","Test", 2017);
        this.student.registerForClass("Student6","Test", 2017);
        assertFalse(this.student.isRegisteredFor("Student6", "Test", 2017));
    }

    @Test //nonexistent class
    public void testMakeStudent2(){
        this.student.registerForClass("Student", "Not Test", 2017);
        assertFalse(this.student.isRegisteredFor("Student", "Not Test", 2017));
    }

    @Test //wrong name
    public void testMakeStudent3() {
        this.student.registerForClass("Student", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("Student2", "Test", 2017));
    }

    @Test //wrong year
    public void testMakeStudent4() {
        this.student.registerForClass("Student", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("Student2", "Test", 2015));
    }

    @Test
    public void testDropClass(){
        this.student.registerForClass("Student", "Test", 2017);
        this.student.dropClass("Student", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
    }

    @Test
    public void testDropClass2(){
        this.student.registerForClass("Student", "Test", 2017);
        this.student.dropClass("Student", "Test", 2015);
        assertTrue(this.student.isRegisteredFor("Student", "Test", 2017));
    }

    @Test
    public void testSubmission(){
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test",
                2017, "Homework", "Description");
        this.student.submitHomework("Student","Homework", "Answer",
                "Test", 2017);
        assertTrue(this.student.hasSubmitted("Student", "Homework", "Test",
                2017));
    }
    @Test //submit only if student enrolled
    public void testSubmission2(){
        this.instructor.addHomework("Instructor", "Test",
                2017, "Homework", "Description");
        this.student.submitHomework("Student","Homework", "Answer",
                "Test", 2017);
        assertFalse(this.student.hasSubmitted("Student", "Homework", "Test",
                2017));
    }

    @Test //nonexistent homework
    public void testSubmission3(){
        this.instructor.addHomework("Instructor", "Test",
                2017, "Homework", "Description");
        this.student.submitHomework("Student","NotHomework", "Answer",
                "Test", 2017);
        assertFalse(this.student.hasSubmitted("Student", "Homework", "Test",
                2017));

    }

    @Test //only in this year, not future
    public void testSubmission4(){
        this.admin.createClass("Class", 2020, "FInstructor", 10);
        this.student.registerForClass("FStudent", "Class", 2020);
        this.instructor.addHomework("FInstructor", "Class",
                2020, "Homework", "Description");
        this.student.submitHomework("FStudent","Homework", "Answer",
                "Class", 2020);
        assertFalse(this.student.hasSubmitted("FStudent", "Homework",
                "Class", 2020));

    }

}
