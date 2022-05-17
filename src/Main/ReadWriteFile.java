import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public interface ReadWriteFile {


    static ArrayList<String> ReadFromFile(String path) {
        File file = new File(path);                         //To get the files path
        if(!file.exists())return null;                      //make sure the file exists if not return null
        ArrayList<String> data = new ArrayList<>();         // ArrayList Of Strings declared with the name data
        try {
            Scanner reader = new Scanner(file);             //Scanner Declared
            while (reader.hasNextLine()){                   //while there is next line
                data.add(reader.nextLine());                //add it to the arraylist "data"
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;                                        //return the list
    }


    ///////////////////////////////Add Employee//////////////////////////////////////////

    static void WriteToFile(String path, String data) {     //for writing the data to file
        File file = new File(path);                         //To get the files path
        CheckFileExistence(file);                           //if the file doesnt exist its going to be created
        WriteToFile(file, data);                            //Write to file
    }

    static private void WriteToFile(File file, String data){        //for writing to file with the  data
        data = FixSyntax(data);                                     //for the data to be fixed if needed
        try {
            FileWriter writer = new FileWriter(file, true); //Set writer output to the file
            writer.append(data);                                    //appends data to the file
            writer.close();                                         //close the writer
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static private String FixSyntax(String data){                          //fixes the syntax
        //last char is a SPLIT_SYMBOL, since we don't have any other data, we replace that with a \n to set the new line for the next employee
        String fixed = data.substring(0, data.length() - 1);
        return fixed + "\n";
    }

    static private void CheckFileExistence(File file){          //Creates the file if it doesn't exists
        if(file.exists())return;                                //if it exists we just return

        try {
            file.createNewFile();                               //Otherwise it gets created
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   ////////////////////////////////////////////////////////////////////////////////////////////////////////

  ///////////////////////////////////////////////////Delete////////////////////////////////////////////////

    static void DeleteFromFile(String path, int id, String SPLIT_SYMBOL) {      //Delete a line from file
        File file = new File(path);                                             //gets the file from the path
        if(!file.exists()){
            System.err.println("Please make sure to write the right path");     //Check file exists
            return;
        }

        ArrayList<String> file_data =  ReadFromFile(path);                      //Get every line from the file
        for(int i = 0; i < file_data.size(); i++){                              //Go through every line
            String line = file_data.get(i);
            if(CheckID(id, line, SPLIT_SYMBOL)){                                //When you find the line that you want to delete
                file_data = CompactLines(file_data, i);                         //Save every line except this one on the list
                ReplaceFile(path, file_data);                                   //Overwrite the old file with the new list
                System.out.println("Line deleted");
                break;
            }
        }
    }

    static void ReplaceFile(String path, ArrayList<String> lines){      //Overwrites the file with new lines
        File file = new File(path);                                     //Gets the file from the file path
        try {
            FileWriter writer = new FileWriter(file);                   //writer outputs to the file
            writer.write("");                                       //Clears the file
            lines.forEach(line ->{                                      //for each line on the list
                try {
                    writer.append(line + "\n");                         //write that line and then move on a new line
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();                                             //closes the writer
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<String> CompactLines(ArrayList<String> lines, int start){  //Clones the list without the element at the index
        ArrayList<String> compacted = new ArrayList<>();                        //declares clone list as compacted
        for(int i = 0; i < lines.size(); i++){                                  //for each element on the list
            if(i >= start){                                                     //if it's the index or greater
                if(i != lines.size() - 1) compacted.add(lines.get(i + 1));      //it gets  saved on the next index's value
            }else{
                compacted.add(lines.get(i));                                    //otherwise if it's lower save the current line
            }
        }
        return compacted;                                                       //returns cloned list
    }

    static boolean CheckID(int id, String data, String SPLIT_SYMBOL){           //to check if file's line has the same id
                                                                                //line syntax: id;name;surname;age;gender;list#list
        String[] parts = data.split(SPLIT_SYMBOL);                              //splits the line when it finds the "SPLIT_SYMBOL"
        if(id == Integer.parseInt(parts[0]))return true;                        //first element needs to be the id, so it will check if it's the same
        return false;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////List All Employees///////////////////////////////////////

    static ArrayList<Employee> ListEmployees(String path, String SPLIT_SYMBOL, String SPLIT_HOLIDAYS_SYMBOL){   //method to get all the saved employees as list
        File file = new File(path);                                                 //gets file from the path
        if(!file.exists())return null;                                              //see if it exists if not returns null

        ArrayList<String> file_data = ReadFromFile(path);                           //to get  all lines from the file
        ArrayList<Employee> employees = new ArrayList<>();                          //declares a list to store all employees
        file_data.forEach(employee ->{                                              //for each line on the list
            String[] data = employee.split(SPLIT_SYMBOL);                           //split all values where it finds SPLIT_SYMBOL
            int id = Integer.parseInt(data[0]);                                     //gets the id
            String name = data[1];                                                  //gets the name
            String surname = data[2];                                               //gets the surname
            int age = Integer.parseInt(data[3]);                                    //gets the age
            char gender = data[4].charAt(0);                                        //gets the gender

            /*
            Line syntax: id; name; surname; age;gender; remaining_date; remaining_date # requested_date; requested_date
            We use SPLIT_HOLIDAYS_SYMBOL so we can know when the first date's list will stop and start the second list

            Since we have splitted every element on the list, we have to rebuild the string, so we can split again on the SPLIT_HOLIDAYS_SYMBOL
             */

            String holidays_data = "";                                              //Create the string that will rebuild the holiday section
            for(int i = 5; i < data.length; i++){                                   //Since we saved every data until the 4 element, dates will starts from the 5* element
                holidays_data += data[i] + SPLIT_SYMBOL;                            //add array's value and add SPLIT_SYMBOL
            }
            holidays_data = holidays_data.substring(0, holidays_data.length() - 1); //Last char will be a SPLIT_SYMBOL so we can remove it

            String[] all_holidays = holidays_data.split(SPLIT_HOLIDAYS_SYMBOL);     //We split the string in 2 substring, all_holidays[0] will hold
                                                                                    //every date before the SPLIT_HOLIDAYS_SYMBOL, so the remaining holidays
                                                                                    //and all_holidays[1] will hold every other dates

            ArrayList<String> holidays_remaining = new ArrayList<>();               //Create list that will hold every remaining holidays
            String[] all_remaining = all_holidays[0].split(SPLIT_SYMBOL);           //split that string when it finds SPLIT_SYMBOL
            for(int i = 0; i < all_remaining.length; i++){                          //for each element
                holidays_remaining.add(all_remaining[i]);                           //save it on the list
            }

            ArrayList<String> holidays_request = new ArrayList<>();                 //Create list that will hold every request holiday
            String[] all_requested = all_holidays[1].split(SPLIT_SYMBOL);           //split that string when it finds SPLIT_SYMBOL
            for(int i = 0; i < all_requested.length; i++){                          //for each element
                holidays_request.add(all_requested[i]);                             //save it on the list
            }
                                                                                    //Finally we can create an Employee entity and add it to the employees list
            Employee e = new Employee(name, surname, age, gender, holidays_remaining, holidays_request, false);
            e.SetID(id);
            employees.add(e);
        });

        return employees;                                                           //return employees list
    }

    /*-------------------------------------------------------------------------------------*/

    /*-------------------------------------Holiday Request------------------------------------------*/

    static void RequestHoliday(String path, String SPLIT_SYMBOL, int id ,String request_date){  //Request holiday an save it on the file
        File file = new File(path);                                                             //pick the file
        if(!file.exists()){                                                                     //
            System.err.println("Make sure to type the right path");                             //Check file exists
            return;                                                                             //
        }                                                                                       //

        ArrayList<String> file_data = ReadFromFile(path);                                       //Get file lines
        ArrayList<String> modified_file = new ArrayList<>();                                    //Create a list that will hold the new variables
        file_data.forEach(line ->{                                                              //for each line
            String[] parts = line.split(SPLIT_SYMBOL);                                          //split it when it finds SPLIT_SYMBOL
            if(Integer.parseInt(parts[0]) == id){                                               //Check if the id matches
                if(CheckRequestRules(parts, request_date)){                                     //Check if the request follows the rules
                    String built_line = "";                                                     //We need to recreate the string so we can save it
                    for(int i = 0; i < parts.length; i++){                                      //for each element
                        built_line += parts[i] + SPLIT_SYMBOL;                                  //add it to the string with the SPLIT_SYMBOL
                    }
                    built_line += request_date + SPLIT_SYMBOL;                                  //Add date at the end

                    modified_file.add(built_line);                                              //save it on the list

                }else{
                    System.out.println("Your request does not follow the rules");
                    return;
                }
            }else{
                modified_file.add(line);                                                        //if the id does not match, copy this line
            }
        });
        ReplaceFile(path, modified_file);                                                       //overwrite the life
        System.out.println("Request added");

    }

    static boolean CheckRequestRules(String[] parts, String request_date){
        return true;
    }

    /*---------------------------------------------------------------------------------------------*/

    static boolean HasID(String path, String SPLIT_SYMBOL, int id){
        File file = new File(path);                                                             //pick the file
        if(!file.exists()){                                                                     //
            System.err.println("Make sure to type the right path");                             //Check file exists
            return false;                                                                       //
        }                                                                                       //

        ArrayList<String> file_data = ReadFromFile(path);
        for(String line : file_data) {
            String[] parts = line.split(SPLIT_SYMBOL);                                          //split it when it finds SPLIT_SYMBOL
            if (Integer.parseInt(parts[0]) == id) {                                               //Check if the id matches
                return true;
            }
        }
        return false;
    }

    static int GetLastID(String path, String SPLIT_SYMBOL){
        File file = new File(path);                                                             //pick the file
        if(!file.exists()){                                                                     //
            System.err.println("Make sure to type the right path");                             //Check file exists
            return -1;                                                                       //
        }
        ArrayList<String> file_data = ReadFromFile(path);
        if(file_data.size() == 0)return 0;
        String[] parts = file_data.get(file_data.size() - 1).split(SPLIT_SYMBOL);
        return Integer.parseInt(parts[0]);
    }

}
