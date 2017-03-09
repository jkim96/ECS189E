import api.IAdmin;
import api.core.impl.Admin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jessica on 3/8/17.
 */
public class TestAdmin {

    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.admin.createClass("Class", 2018, "Teacher", 10);
    }

    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    @Test
    public void testMakeClass3() {
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertTrue(this.admin.getClassCapacity("Test", 2017) > 0);
    }

    @Test //No instructor more than two classes
    public void testMakeClass5() {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        this.admin.createClass("Test2", 2017, "Instructor",15);
        this.admin.createClass("Test3", 2017, "Instructor", 20);
        assertFalse(this.admin.classExists("Test3", 2017));
    }

    @Test //no instructor
    public void testMakeClass6() {
        this.admin.createClass("Test", 2017, null, 15);
        assertFalse(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testCapacity() {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        assertEquals(this.admin.getClassCapacity("Test", 2017), 10);
    }

    @Test //lower capacity
    public void testChangeCapacity() {
        this.admin.createClass("Test1", 2017,"Instructor",15);
        this.admin.changeCapacity("Test1", 2017, 20);
        assertEquals(this.admin.getClassCapacity("Test1", 2017), 20);
    }

    @Test //higher capacity
    public void testChangeCapacity2() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test", 2017, 10);
        assertEquals(this.admin.getClassCapacity("Test", 2017), 20);
    }

    @Test //negative capacity
    public void testChangeCapacity3() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test1", 2017, -5);
        assertFalse(this.admin.getClassCapacity("Test", 2017) < 0);
    }

    @Test //same capacity
    public void testChangeCapacity4() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test", 2017, 15);
        assertTrue(this.admin.getClassCapacity("Test3", 2017) == 15);
    }



}
