import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main extends Application{

    //Colors
    static Color color_background = Color.rgb(52, 73, 94, 1);
    static Color color_text = Color.rgb(241, 196, 15, 1);

    static String style_button = (
            "-fx-background-radius: 5px;" +
            "-fx-background-color: #f39c12;" +
            "-fx-min-width: 100px;"
    );

    static String style_text = (
            "-fx-font-size: 14px;" +
            "-fx-fill: #e67e22;" +
            "-fx-text-fill: #e67e22;" +
            "-fx-font-weight: bold;"
    );

    static String style_text_field = (
            "-fx-background-color: #e67e22;" +
            "-fx-background-radius: 50px;" +
            "-fx-max-width: 325px;" +
            "-fx-text-fill: #ecf0f1;" +
            "-fx-font-weight: bold;"
    );

    static String style_text_field_little = (
            "-fx-background-color: #e67e22;" +
            "-fx-background-radius: 50px;" +
            "-fx-max-width: 200px;" +
            "-fx-text-fill: #ecf0f1;" +
            "-fx-font-weight: bold;"
    );

    static String style_text_field_little_not_editable = (
        "-fx-background-color: #c0392b;" +
        "-fx-background-radius: 50px;" +
        "-fx-max-width: 200px;" +
        "-fx-text-fill: #ecf0f1;" +
        "-fx-font-weight: bold;"
    );

    static String style_area_text_field_little_not_editable = (
        "-fx-background-color: #c0392b;" +
        "-fx-color-label-visible : #c0392b;" +
        "-fx-border-radius: 50px;" +
        "-fx-font-weight: bold;"
    );

    static String style_checkbox = (
            "-fx-border-color: lightblue; " +
            "-fx-font-size: 15;" +
            "-fx-border-width: 0;" +
            "-fx-text-fill: #e67e22;" +
            "-fx-font-weight: bold;"
    );

    static String final_button = (
            "-fx-background-radius: 5px;" +
            "-fx-background-color: #2ecc71;" +
            "-fx-min-width: 200px;" +
            "-fx-min-height: 20px;"
    );

    static String style_error = (
            "-fx-font-size: 14px;" +
            "-fx-fill: #e74c3c;" +
            "-fx-text-fill: #e74c3c;" +
            "-fx-font-weight: bold;"
    );

    static String style_back = (
        "-fx-background-radius: 2px;" +
        "-fx-background-color: #95a5a6;" +
        "-fx-min-width: 100px;" +
        "-fx-min-height: 20px;"
    );

    static Stage window;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Employee Holidays System");

        HomeScene();
    }

    public static void HomeScene(){

        Employee.SetCounter();

        VBox centralMenu = new VBox();

        Label text_label = new Label();
        text_label.setText("What do you wanna do?");
        text_label.setFont(Font.font("Verdana", 20));
        text_label.setTextFill(color_text);

        Button add = new Button("Add");
        add.setStyle(style_button);
        add.setOnAction(e -> AddScene());

        Button delete = new Button("Delete");
        delete.setStyle(style_button);
        delete.setOnAction(e -> DeleteScene());

        Button list = new Button("List");
        list.setStyle(style_button);
        list.setOnAction(e -> ListScene());

        Button request = new Button("Request");
        request.setStyle(style_button);
        request.setOnAction(e -> RequestScene());

        centralMenu.getChildren().addAll(text_label, add, delete, list, request);
        centralMenu.setAlignment(Pos.CENTER);
        centralMenu.setSpacing(20);

        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(color_background, CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setCenter(centralMenu);

        SetScene(borderPane);
    }

    public static void RequestScene(){
        HBox top = new HBox();

        Button back_button = new Button("Back");
        back_button.setStyle(style_back);
        back_button.setOnAction(e->{ HomeScene(); });
        top.getChildren().setAll(back_button);

        VBox centralMenu = new VBox();

        TextField id_input = new TextField("ID");
        id_input.setStyle(style_text_field_little);
        id_input.setAlignment(Pos.CENTER);


        HBox requested = new HBox();

        Label requested_label = new Label("Requested: ");
        requested_label.setStyle(style_text);

        DatePicker holidays_requested = new DatePicker();
        ArrayList<LocalDate> holidays_requested_dates = new ArrayList<>();
        holidays_requested.setOnAction(e ->{
            if(holidays_requested_dates.indexOf(holidays_requested.getValue()) == -1){
                holidays_requested_dates.add(holidays_requested.getValue());
            }else{
                holidays_requested_dates.remove(holidays_requested.getValue());
            }
        });

        requested.getChildren().setAll(requested_label, holidays_requested);
        requested.setAlignment(Pos.CENTER);
        requested.setSpacing(10);

        Label error_label = new Label();
        error_label.setStyle(style_error);

        Button request_button = new Button("Request");
        request_button.setStyle(final_button);
        request_button.setOnAction(e->{
            ArrayList<String> requestings = FixDateSyntax(holidays_requested_dates);
            if(requestings.size() == 0){
                error_label.setText("You have to assign dates!");
                return;
            }else{
                error_label.setText("");
            }

            if(!TryIntegerParse(id_input.getText())){
                error_label.setText("You have to put integers!(id)");
                return;
            }else{
                error_label.setText("");
            }

            if(!Employee.HasID(Integer.parseInt(id_input.getText()))){
                error_label.setText("Id not saved");
                return;
            }else{
                error_label.setText("");
            }

            Employee.RequestHoliday(Integer.parseInt(id_input.getText()), requestings.get(requestings.size() - 1));
            RequestScene();
        });


        centralMenu.getChildren().setAll(id_input, requested, error_label, request_button);
        centralMenu.setAlignment(Pos.CENTER);
        centralMenu.setSpacing(10);

        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(color_background, CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setLeft(top);
        borderPane.setCenter(centralMenu);
        VBox right = new VBox();
        right.setPadding(new Insets(80));
        borderPane.setRight(right);

        SetScene(borderPane);

    }

    public static void ListScene(){
        HBox top = new HBox();

        Button back_button = new Button("Back");
        back_button.setStyle(style_back);
        back_button.setOnAction(e->{ HomeScene(); });
        top.getChildren().setAll(back_button);

        VBox centralMenu = new VBox();

        ArrayList<Employee> employees = Employee.ListEmployees();
        for(Employee e : employees){
            HBox lines = new HBox();

            TextField id_field = new TextField(String.valueOf(e.GetID()));
            id_field.setStyle(style_text_field_little_not_editable);
            id_field.setAlignment(Pos.CENTER);

            TextField name_field = new TextField(e.GetName());
            name_field.setStyle(style_text_field_little_not_editable);
            name_field.setAlignment(Pos.CENTER);

            TextField surname_field = new TextField(e.GetSurname());
            surname_field.setStyle(style_text_field_little_not_editable);
            surname_field.setAlignment(Pos.CENTER);

            TextField age_field = new TextField(String.valueOf(e.GetAge()));
            age_field.setStyle(style_text_field_little_not_editable);
            age_field.setAlignment(Pos.CENTER);

            TextField gender_field = new TextField(String.valueOf(e.GetGender()));
            gender_field.setStyle(style_text_field_little_not_editable);
            gender_field.setAlignment(Pos.CENTER);

            TextArea remaining_holidays = new TextArea();
            String remainings = "";
            for (String s : e.GetRemainingHolidays()) {
                remainings += s + "\n";
            }
            remaining_holidays.setText(remainings);
            remaining_holidays.setStyle(style_area_text_field_little_not_editable);
            remaining_holidays.setPrefSize(200, 20 * e.GetRemainingHolidays().size());

            TextArea requested_holidays = new TextArea();
            String requestes = "";
            for(String s : e.GetRequestedHolidays()){
                requestes += s + "\n";
            }
            requested_holidays.setText(requestes);
            requested_holidays.setStyle(style_area_text_field_little_not_editable);
            requested_holidays.setPrefSize(200, 25 * e.GetRequestedHolidays().size());

            lines.getChildren().setAll(id_field, name_field, surname_field, age_field, gender_field, remaining_holidays, requested_holidays);



            lines.setAlignment(Pos.CENTER);
            lines.setSpacing(10);

            centralMenu.getChildren().add(lines);
        }

        centralMenu.setAlignment(Pos.CENTER);
        centralMenu.setSpacing(10);

        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(color_background, CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setLeft(top);
        borderPane.setCenter(centralMenu);
        VBox right = new VBox();
        right.setPadding(new Insets(80));
        borderPane.setRight(right);

        SetScene(borderPane);
    }

    public static void DeleteScene(){
        HBox top = new HBox();

        Button back_button = new Button("Back");
        back_button.setStyle(style_back);
        back_button.setOnAction(e->{ HomeScene(); });

        top.getChildren().setAll(back_button);
        top.setPadding(new Insets(10));

        VBox centralMenu = new VBox();

        TextField id_input = new TextField("ID");
        id_input.setStyle(style_text_field_little);
        id_input.setAlignment(Pos.CENTER);

        Label error_label = new Label();
        error_label.setStyle(style_error);

        Button delete_button = new Button("Delete");
        delete_button.setStyle(final_button);
        delete_button.setOnAction(e ->{
            if(!TryIntegerParse(id_input.getText())){
                error_label.setText("You have to put integers!!!");
                return;
            }else {
                error_label.setText("");
            }

            if(!Employee.HasID(Integer.parseInt(id_input.getText()))){
                error_label.setText("ID not valid");
                return;
            }else {
                error_label.setText("");
            }

            Employee.DeleteFromFile(Integer.parseInt(id_input.getText()));
            DeleteScene();
        });

        centralMenu.getChildren().setAll(id_input, error_label, delete_button);
        centralMenu.setAlignment(Pos.CENTER);
        centralMenu.setSpacing(10);

        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(color_background, CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setLeft(top);
        borderPane.setCenter(centralMenu);
        VBox right = new VBox();
        right.setPadding(new Insets(80));
        borderPane.setRight(right);

        SetScene(borderPane);
    }

    public static void AddScene(){

        boolean isMale = true;

        HBox top = new HBox();

        Button back_button = new Button("Back");
        back_button.setStyle(style_back);
        back_button.setOnAction(e->{ HomeScene(); });

        top.getChildren().setAll(back_button);
        top.setPadding(new Insets(10));

        VBox centralMenu = new VBox();

        TextField name_input = new TextField("Name");
        name_input.setStyle(style_text_field);
        name_input.setAlignment(Pos.CENTER);

        TextField surname_input = new TextField("Surname");
        surname_input.setStyle(style_text_field);
        surname_input.setAlignment(Pos.CENTER);

        HBox horizontalMenu = new HBox();

        TextField id_input = new TextField(String.valueOf(Employee.GetIDCounter()));
        id_input.setStyle(style_text_field_little_not_editable);
        id_input.setEditable(false);
        id_input.setAlignment(Pos.CENTER);

        TextField age_input = new TextField("Age");
        age_input.setStyle(style_text_field_little);
        age_input.setAlignment(Pos.CENTER);

        horizontalMenu.getChildren().setAll(id_input, age_input);
        horizontalMenu.setAlignment(Pos.CENTER);
        horizontalMenu.setSpacing(25);

        HBox gender = new HBox();

        CheckBox male_checkbox = new CheckBox("Male");
        male_checkbox.setStyle(style_checkbox);
        male_checkbox.setSelected(true);

        CheckBox female_checkbox = new CheckBox("Female");
        female_checkbox.setStyle(style_checkbox);
        female_checkbox.setSelected(false);

        female_checkbox.setOnAction(e->{
             male_checkbox.setSelected(false);
        });
        male_checkbox.setOnAction(e->{
            female_checkbox.setSelected(false);
        });

        gender.getChildren().setAll(male_checkbox, female_checkbox);
        gender.setAlignment(Pos.CENTER);
        gender.setSpacing(40);

        HBox remaining = new HBox();

        Label remaining_label = new Label("Remaining: ");
        remaining_label.setStyle(style_text);

        DatePicker holidays_remaining = new DatePicker();
        ArrayList<LocalDate> holidays_remaining_dates = new ArrayList<>();
        holidays_remaining.setOnAction(e ->{
            if(holidays_remaining_dates.indexOf(holidays_remaining.getValue()) == -1){
                holidays_remaining_dates.add(holidays_remaining.getValue());
            }else{
                holidays_remaining_dates.remove(holidays_remaining.getValue());
            }
        });

        remaining.getChildren().setAll(remaining_label, holidays_remaining);
        remaining.setAlignment(Pos.CENTER);
        remaining.setSpacing(10);


        centralMenu.getChildren().setAll(name_input, surname_input, horizontalMenu, gender, remaining);
        centralMenu.setAlignment(Pos.CENTER);
        centralMenu.setSpacing(10);


        HBox requested = new HBox();

        Label requested_label = new Label("Requested: ");
        requested_label.setStyle(style_text);

        DatePicker holidays_requested = new DatePicker();
        ArrayList<LocalDate> holidays_requested_dates = new ArrayList<>();
        holidays_requested.setOnAction(e ->{
            if(holidays_requested_dates.indexOf(holidays_requested.getValue()) == -1){
                holidays_requested_dates.add(holidays_requested.getValue());
            }else{
                holidays_requested_dates.remove(holidays_requested.getValue());
            }
        });

        requested.getChildren().setAll(requested_label, holidays_requested);
        requested.setAlignment(Pos.CENTER);
        requested.setSpacing(10);

        Label error_label = new Label();
        error_label.setStyle(style_error);

        Button save_button = new Button("Save");
        save_button.setStyle(final_button);
        save_button.setOnAction(e->{
            ArrayList<String> remainings = FixDateSyntax(holidays_remaining_dates);
            ArrayList<String> requestings = FixDateSyntax(holidays_requested_dates);
            char _gender = isMale ? 'M' : 'F';

            if(!TryIntegerParse(age_input.getText())){
                error_label.setText("You have to put integers!(age)");
                return;
            }else{
                error_label.setText("");
            }

            if(remainings.size() == 0 || requestings.size() == 0){
                error_label.setText("You have to assign dates!");
                return;
            }

            new Employee(name_input.getText(), surname_input.getText(),
                    Integer.parseInt(age_input.getText()), _gender, remainings, requestings, true);
            AddScene();
        });

        centralMenu.getChildren().setAll(name_input, surname_input, horizontalMenu, gender, remaining, requested, error_label ,save_button);
        centralMenu.setAlignment(Pos.CENTER);
        centralMenu.setSpacing(10);


        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(color_background, CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setLeft(top);
        borderPane.setCenter(centralMenu);
        VBox right = new VBox();
        right.setPadding(new Insets(80));
        borderPane.setRight(right);

        SetScene(borderPane);

    }

    private static boolean TryIntegerParse(String chars){
        try{
            Integer.parseInt(chars);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    private static ArrayList<String> FixDateSyntax(ArrayList<LocalDate> dates){
        ArrayList<String> correct = new ArrayList<>();
        dates.forEach(line ->{
            String[] parts = line.toString().split("-");
            correct.add(parts[2] + "/" + parts[1] + "/" + parts[0]);
        });
        return correct;
    }

    private static void SetScene(BorderPane borderPane){
        Scene scene = new Scene(borderPane, 800, 600);
        window.setScene(scene);
        window.show();
    }
    public static boolean CheckDateSyntax(String date){
        String[] parts = date.split("/");
        if(parts.length != 3)return false;

        for(String s : parts){
            if(!TryIntegerParse(s))return false;
        }

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        if(year < 2000)return false;
        else{
            if(month < 1 || month > 12)return false;
            else {
                if(day < 1)return false;
                switch (month){
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        if(day > 31)return false;
                        else return true;

                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        if(day > 30)return false;
                        else return true;

                    case 2:
                        String string_year = parts[2];
                        String last_year_digits = string_year.substring(string_year.length() - 2);
                        if(last_year_digits.equals("00")){
                            if(year % 400 == 0){
                                if(day > 29)return false;
                                else return true;
                            }
                            if(day > 28)return false;
                            else return true;
                        }else if(year % 4 == 0){
                            if(day > 29)return false;
                            else return true;
                        }else {
                            if(day > 28)return false;
                            else return true;
                        }

                    default:
                        return false;
                }
            }
        }
    }
}
