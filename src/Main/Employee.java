import java.util.ArrayList;
import java.util.List;

public class Employee implements ReadWriteFile {

    private static int employee_count = 0;
    private static final String PATH = "data.txt";
    private static final String SPLIT_SYMBOL = ";";
    private static final String SPLIT_HOLIDAYS_SYMBOL = "#";

    private int id;
    private String name;
    private String surname;
    private int age;
    private char gender;
    private ArrayList<String> holidays_remaining;
    private ArrayList<String> holidays_requested;
    private ArrayList<String> details;

    public Employee(String name, String surname, int age, char gender, ArrayList<String> holidays_remaining, ArrayList<String> holidays_requested, boolean save){
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
        this.holidays_remaining = holidays_remaining;
        this.holidays_requested = holidays_requested;
        details = new ArrayList<>();

        if(save){                                                           //Save on file
            id = AssignID();
            String data = id + SPLIT_SYMBOL + name + SPLIT_SYMBOL +         //for seperating the data
                    surname + SPLIT_SYMBOL + age + SPLIT_SYMBOL + gender;

            for(String s : holidays_remaining){                             //adds dates using the seperator
                data += SPLIT_SYMBOL + s;
            }

            data += SPLIT_HOLIDAYS_SYMBOL;                                  //a new separator for splitting the remaining and requested lists

            for(String s : holidays_requested){                             //adds a separator to  each requested date
                data += s + SPLIT_SYMBOL;
            }

            ReadWriteFile.WriteToFile(PATH, data);                          //Saves the data to the file
        }

    }

    public void SetDetails(ArrayList<String> details){this.details = details;}
    public List<String> GetDetails(){return details;}

    public int GetID(){return id;}
    public void SetID(int id){this.id = id;}
    public String GetName(){return name;}
    public String GetSurname(){return surname;}
    public int GetAge(){return age;}
    public char GetGender(){return gender;}
    public ArrayList<String> GetRemainingHolidays(){return holidays_remaining;}
    public ArrayList<String> GetRequestedHolidays(){return holidays_requested;}

    public static void SetCounter(){
        employee_count = ReadWriteFile.GetLastID(PATH, SPLIT_SYMBOL) + 1;
    }

    public static int AssignID(){
        employee_count++;
        return (employee_count - 1);
    }

    public static int GetIDCounter(){
        return employee_count;
    }

    public static boolean HasID(int id){
        if(id > employee_count)return false;
        return ReadWriteFile.HasID(PATH, SPLIT_SYMBOL, id);
    }


    public static void DeleteFromFile(int id){
        ReadWriteFile.DeleteFromFile(PATH, id, SPLIT_SYMBOL);
    }

    public static ArrayList<Employee> ListEmployees(){
        ArrayList<Employee> employees = ReadWriteFile.ListEmployees(PATH, SPLIT_SYMBOL, SPLIT_HOLIDAYS_SYMBOL);
        /*employees.forEach(e ->{
            System.out.println("\nNEW EMPLOYEE:");
            e.ShowAll();
        });*/
        return employees;
    }

    public void ShowAll(){
        String data = "ID : " + id + " \nName : " + name + " \nSurname : " + surname + " \nAge : " + age + " \nGender : " + gender + " \nRemaining Holidays:\n";
        for (String s : holidays_remaining) {
            data += s + " \n";
        }
        data += "Requested Holidays:\n";
        for (String s : holidays_requested) {
            data += s + " \n";
        }
        System.out.println(data);
    }

    public static void RequestHoliday(int id, String request_date){
        ReadWriteFile.RequestHoliday(PATH, SPLIT_SYMBOL, id ,request_date);
    }

    public static void ShowHolidayStatus(){
        ArrayList<Employee> employees = ReadWriteFile.ListEmployees(PATH, SPLIT_SYMBOL, SPLIT_HOLIDAYS_SYMBOL);
        employees.forEach(e ->{
            System.out.println("Employee : " + e.GetName() + " " + e.GetSurname());
            e.ShowEmployeeHolidaysStatus();
        });
    }

    public void ShowEmployeeHolidaysStatus(){
        String data = "";
        for (String s : holidays_remaining) {
            data += s + " \n";
        }
        data += "Requested Holidays:\n";
        for (String s : holidays_requested) {
            data += s + " \n";
        }
        System.out.println(data);
    }

}
