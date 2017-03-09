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
public class TestInstructor {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
        this.admin.createClass("Test", 2017, "Instructor", 10);
    }

    @Test
    public void testMakeHomework() {
        this.instructor.addHomework("Instructor", "Test",
                2017, "Homework", "Description");
        assertTrue(this.instructor.homeworkExists("Test", 2017, "Homework"));
    }

    @Test //wrong professor
    public void testMakeHomework1() {
        this.instructor.addHomework("Not Instructor", "Test", 2017,
                "Homework", "Student");
        assertFalse(this.instructor.homeworkExists("Test", 2017, "Homework"));
    }

    @Test //wrong class
    public void testMakeHomework3() {
        this.instructor.addHomework("Instructor", "Not Test", 2017,
                "Homework", "Description");
        assertFalse(this.instructor.homeworkExists("Not Test", 2017,"Homework"));
    }

    @Test
    public void testGrade(){
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test",
                2017, "Homework", "Description");
        this.student.submitHomework("Student", "Homework", "Answer",
                "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017,
                "Homework", "Student", 95);
        assertTrue(this.instructor.getGrade("Test", 2017, "Homework",
                "Student") == 90);
    }
    @Test //negative score
    public void testGrade2() {
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "Homework",
                "Description");
        this.student.submitHomework("Student", "Homework", "Answer",
                "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017,
                "Homework", "Student", -10);
        assertTrue(this.instructor.getGrade("Test", 2017, "Homework",
                "Student") == null);
    }
    @Test
    public void testSubmission(){
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test",
                2017, "Homework", "Description");
        this.instructor.assignGrade("Instructor", "Test", 2017,
                "Homework", "Student", 95);
        assertTrue(this.instructor.getGrade("Test", 2017, "Homework",
                "Student") == null);
    }
}
