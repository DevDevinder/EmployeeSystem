import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class EmployeeTest {


    private ArrayList<String> holiday_request = new ArrayList<>();
    private ArrayList<String> holiday_remaining = new ArrayList<>();
    @Test
    public void getName() {

        System.out.println("getStudentName");
        Employee instance = new  Employee("dev", "bakshi", 28, 'm', holiday_request,  holiday_remaining, true);
        String expResult = "dev";
        String result = instance.GetName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void getSurname() { System.out.println("getEmployeeSurname");
        Employee instance = new  Employee("dev", "bakshi", 28, 'm', holiday_request,  holiday_remaining, true);
        String expResult = "bakshi";
        String result = instance.GetSurname();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");

    }

    @Test
    public void getAge() {
        System.out.println("getEmployeeAge");
        Employee instance = new  Employee("dev", "bakshi", 28, 'm', holiday_request,  holiday_remaining, true);
        int expResult = 28;
        int result = instance.GetAge();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void getGender() {
        System.out.println("getEmployeeGender");
        Employee instance = new  Employee("dev", "bakshi", 28, 'm', holiday_request,  holiday_remaining, true);
        char expResult = 'm';
        char result = instance.GetGender();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void getRemainingHolidays() {
        System.out.println("getStudentName");
        Employee instance = new  Employee("dev", "bakshi", 28, 'm', holiday_request,  holiday_remaining, true);
        ArrayList expResult = holiday_remaining;
        ArrayList result = instance.GetRemainingHolidays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void getRequestedHolidays() {

        System.out.println("getStudentName");
        Employee instance = new  Employee("dev", "bakshi", 28, 'm', holiday_request,  holiday_remaining, true);
        ArrayList expResult = holiday_request;
        ArrayList result = instance.GetRequestedHolidays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}