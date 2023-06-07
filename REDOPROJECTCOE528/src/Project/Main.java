/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.event.ActionEvent;
//import javafx.event.Event;
import javafx.event.EventHandler;
//import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;


/**
 *
 * @author huyda
 */
public class Main extends Application {

    String adminuser = "admin";
    String adminpw = "admin";
    String checkUser1, checkPw1;
    String checkUser2, checkPw2;
    String checkUser, checkPw;



    @Override
    public void start (Stage primaryStage) throws Exception {



            //Creating the Login Screen
            primaryStage.setTitle("Bookstore - Login");
            //Setting up Borders
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(15);
            grid.setVgap(15);
            grid.setPadding(new Insets(20, 20, 20, 20));
            Scene login_start = new Scene(grid, 300, 250);
            //Login Title and TextBox
            Text scenetitle = new Text("Welcome to the BookStore");
            scenetitle.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
            grid.add(scenetitle, 0, 0, 2, 1);

            Label user = new Label("Username: ");
            Label pw = new Label("Password: ");
            PasswordField txtpw = new PasswordField();
            TextField txtuser = new TextField();
            //final PasswordField txtpw = new PasswordField();

            grid.add(user, 0, 1);
            grid.add(pw, 0, 2);
            grid.add(txtpw, 1, 2);
            grid.add(txtuser, 1, 1);

            //Button
            Button btn = new Button("Login");
            //Add ID's to Nodes
            btn.setId("btn"); //Maybe not needed
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);
            grid.add(hbBtn, 1, 4);

            //idk what this is for we'll find out
            final Text actiontarget = new Text();
            grid.add(actiontarget, 1, 6);


            //Creating the Owner-Start Screen
            Stage ownerStage = new Stage();
            ownerStage.setTitle("Owner - Home");
            VBox vb = new VBox();
            Button ownbtn1 = new Button("Books");
            Button ownbtn2 = new Button("Customers");
            Button ownbtn3 = new Button("Logout");

            vb.setId("root");
            ownbtn1.setId("btn1");
            ownbtn2.setId("btn2");
            ownbtn3.setId("btn3");
            ownbtn1.setPrefSize(100,200);
            ownbtn2.setPrefSize(100,200);
            ownbtn3.setPrefSize(100,200);

            vb.setPadding(new Insets(30,50,50,50));
            vb.setSpacing(10);
            vb.setAlignment(Pos.CENTER);
            vb.getChildren().addAll(ownbtn1, ownbtn2, ownbtn3);
            //Adding VBox to the Scene
            Scene owner_start = new Scene(vb, 250, 200);


            //Reading the Customer.txt file
            Collection<Customer>cslist = Files.readAllLines(new File("customer.txt").toPath())
                    .stream()
                    .map(line -> {
                        String[] custdetails = line.split(",");
                        Customer cd = new Customer();
                        cd.setUser(custdetails[0]);
                        cd.setPw(custdetails[1]);
                        cd.setPoints(custdetails[2]);
                        return cd;

                    })
                    .collect(Collectors.toList());
            ObservableList<Customer> custdetails = FXCollections.observableArrayList(cslist);



            //Testing to read customer txt
            for(Iterator i = cslist.iterator(); i.hasNext(); )
                System.out.println(i.next());

            System.out.println(custdetails);
            //

            //Remaking. Array Section (Owncusttable)
            TableView<Customer> owncusttable = new TableView();
            TableColumn<Customer, String> userCol = new TableColumn<>("Username");
            TableColumn<Customer, String> pwCol = new TableColumn<>("Password");
            TableColumn<Customer, String> pointsCol = new TableColumn<>("Points");

            userCol.setCellValueFactory(data1 -> data1.getValue().userProperty());
            pwCol.setCellValueFactory(data1 -> data1.getValue().pwProperty());
            pointsCol.setCellValueFactory(data1 -> data1.getValue().pointsProperty());


            //Owner-Customer Page
            Stage owncuststage = new Stage();
            owncuststage.setTitle("Owner - Customer Manager");
            owncuststage.setWidth(600);
            owncuststage.setHeight(700);
            Label custlist = new Label("Customer List");
            custlist.setFont(new Font("Arial", 18));
            //Setting up table
            owncusttable.setEditable(true);
            owncusttable.getColumns().addAll(userCol, pwCol, pointsCol);
            owncusttable.setItems(custdetails);
            //Owner-Customer Adding and Removing Customers
            TextField addUser = new TextField();
            TextField addPw = new TextField();
            addUser.setPromptText("Username");
            addUser.setMaxWidth(userCol.getPrefWidth());
            addPw.setPromptText("Password");
            addPw.setMaxWidth(pwCol.getPrefWidth());
            Button addButtoncust = new Button("Add");
            Button removeButtoncust = new Button("Remove");
            Button backButtoncust = new Button("Back");


            //Setting up the Owner Customer Scene
            HBox hb = new HBox(); //Fixing the Add Row
            hb.getChildren().addAll(addUser, addPw, addButtoncust);
            hb.setSpacing(5);
            HBox hb1 = new HBox(); //Fixing the Remove Row
            hb1.getChildren().addAll(removeButtoncust, backButtoncust);
            hb1.setSpacing(10);
            //Grouping Up the VBox. Vertical Elements Organized
            VBox ocvbox = new VBox();
            ocvbox.setSpacing(5);
            ocvbox.setPadding(new Insets(10, 0, 0, 10));
            ocvbox.getChildren().addAll(custlist, owncusttable, hb, hb1);
            Scene owner_customer = new Scene(new Group());
            ((Group) owner_customer.getRoot()).getChildren().addAll(ocvbox);


            //Reading Book.txt File
            Collection<Book>bklist = Files.readAllLines(new File("book.txt").toPath())
                    .stream()
                    .map(line -> {
                        String[] bookdetails = line.split(",");
                        Book bd = new Book();
                        bd.setbkName(bookdetails[0]);
                        bd.setbkPrice(bookdetails[1]);
                        return bd;

                    })
                    .collect(Collectors.toList());
            ObservableList<Book> bookdetails = FXCollections.observableArrayList(bklist);


            //Remaking Array Section (ownbooktable)
            TableView<Book> ownbooktable = new TableView();
            TableColumn<Book, String> bknameCol = new TableColumn<>("Book Name");
            TableColumn<Book, String> bkpriceCol = new TableColumn<>("Book Price");

            bknameCol.setCellValueFactory(data2 -> data2.getValue().bkNameProperty());
            bkpriceCol.setCellValueFactory(data2 -> data2.getValue().bkPriceProperty());

            //Owner - Book Scene
            Stage ownbookstage = new Stage();
            ownbookstage.setTitle("Owner - Books");
            ownbookstage.setWidth(400);
            ownbookstage.setHeight(600);
            Label booklist = new Label("Book List");
            booklist.setFont(new Font("Arial", 18));
            //Setting up Table
            ownbooktable.setEditable(true);
            ownbooktable.getColumns().addAll(bknameCol, bkpriceCol);
            ownbooktable.setItems(bookdetails);
            //Owner - Book Adding and Removing Books
            TextField addbookName = new TextField();
            TextField addbookPrice = new TextField();
            addbookName.setPromptText("Book Name");
            addbookName.setMaxWidth(bknameCol.getPrefWidth());
            addbookPrice.setPromptText("Book Price");
            addbookPrice.setMaxWidth(bkpriceCol.getPrefWidth());
            Button addButtonown = new Button("Add");
            Button removeButtonown = new Button("Remove");
            Button backButtonown = new Button("Back");

            //Setting Up the Owner Book Scene
            HBox hb2 = new HBox(); //Fixing the Adding Row
            hb2.getChildren().addAll(addbookName, addbookPrice, addButtonown);
            hb2.setSpacing(5);
            HBox hb3 = new HBox(); //Fixing the Remove Row
            hb3.getChildren().addAll(removeButtonown, backButtonown);
            hb1.setSpacing(10);
            VBox obvbox1 = new VBox();
            obvbox1.setSpacing(5);
            obvbox1.setPadding(new Insets(10, 0, 0, 10));
            obvbox1.getChildren().addAll(booklist, ownbooktable, hb2, hb3);
            Scene owner_book = new Scene(new Group());
            ((Group) owner_book.getRoot()).getChildren().addAll(obvbox1);


            //Customer Screen // The Customer Only Has 1 Screen

            //Reading Book.txt File -  Owner Books

            Collection<Book>custbklist = Files.readAllLines(new File("book.txt").toPath())
                    .stream()
                    .map(line -> {
                        String[] cusbookdetails = line.split(",");
                        Book cbd = new Book();
                        cbd.setbkName(cusbookdetails[0]);
                        cbd.setbkPrice(cusbookdetails[1]);
                        return cbd;
                    })
                    .collect(Collectors.toList());
            ObservableList<Book> cusbookdetails = FXCollections.observableArrayList(custbklist);



            //Remaking Array Section(custtable)
            TableView<Book> custtable = new TableView();
            TableColumn<Book, String> custbknameCol = new TableColumn<Book, String>("Book Name");
            TableColumn<Book, String> custbkpriceCol = new TableColumn<Book, String>("Book Price");
            TableColumn<Book, Boolean> custSelectCol = new TableColumn<Book, Boolean>("Select");
            custtable.getColumns().addAll(custbknameCol, custbkpriceCol, custSelectCol);
            //Setting up the Table and Comments 
            custbknameCol.setCellValueFactory(data2 -> data2.getValue().bkNameProperty());
            custbkpriceCol.setCellValueFactory(data2 -> data2.getValue().bkPriceProperty());
            custSelectCol.setCellValueFactory(new PropertyValueFactory<Book, Boolean>("Select"));
            custSelectCol.setCellFactory(CheckBoxTableCell.forTableColumn(custSelectCol));
            custSelectCol.setEditable(true);
            custtable.setEditable(true);
            custtable.setItems(cusbookdetails);


            //This will include book table, purchasing, etc.
            Stage customerstage = new Stage();
            customerstage.setTitle("Customer Screen");
            BorderPane custroot = new BorderPane();
            custroot.setCenter(custtable);
            //Setting Up the Labels and Buttons
            Label header = new Label("Welcome Jane! You have # points. Your status is silver/gold");
            HBox controls = new HBox(5);
            Button infoButton = new Button("Buy Selected Books");
            Button RedeemButton = new Button("Redeem Points and Buy");
            Button Logout = new Button("Logout");
            
            controls.getChildren().add(infoButton);
            custroot.setBottom(controls);
            //Grouping Up Vertical Elements
            VBox custbox = new VBox();
            custbox.setSpacing(5);
            custbox.setPadding(new Insets(10,10,10,10));
            custbox.getChildren().addAll(header, custtable, infoButton, RedeemButton, Logout);
            //Initializing the Scene for (Customer_Table)
            Scene customer_table = new Scene(new Group());
            ((Group) customer_table.getRoot()).getChildren().addAll(custbox);







            //Checking for Login
            btn.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    checkUser = txtuser.getText().toString();
                    checkPw = txtpw.getText().toString();


                    //Customer Input Checker //Customer Login
                    try {

                        File custcheck = new File("customer.txt");
                        Scanner custchecksc = new Scanner(custcheck);
                        while(custchecksc.hasNext()) {
                            String custext = custchecksc.nextLine();
                            Scanner checker = new Scanner(custext);
                            checker.useDelimiter(",");
                            String cusUser,cusPw;
                            int cusPoints; //This is for later points implementation
                            cusUser = checker.next(); //Current Customer's Username
                            cusPw = checker.next(); //Current Customer's Password
                            cusPoints = checker.nextInt(); //Current Customer's Points



                            if(checkUser.equalsIgnoreCase(cusUser) && checkPw.equals(cusPw) && !checkUser.equals(adminuser) && !checkPw.equals(adminpw)){
                                System.out.println("Customer Login Works"); //Debugging
                                //Putting in the Customer Screen
                                customerstage.setScene(customer_table);
                                customerstage.show();
                                primaryStage.close();
                                txtuser.clear();
                                txtpw.clear();
                                //Closing the Scanners for Checking Cus' Info
                                checker.close();
                                custchecksc.close();

                                //This Prints out the Customer Info to Terminal : Debugging
                                System.out.println(cusUser);
                                System.out.println(cusPw);
                                System.out.println(cusPoints);

                                //"Welcome Jane! You have # points. Your status is silver/gold"
                                //Setting Up the Values of the Table
                                
                                //Reading Book.txt File
                                //
                                //This is being reread so that changes in owner are met in Customer.
                                Collection<Book> custbklist = Files.readAllLines(new File("book.txt").toPath())
                                        .stream()
                                        .map(line -> {
                                            String[] cusbookdetails = line.split(",");
                                            Book cbd = new Book();
                                            cbd.setbkName(cusbookdetails[0]);
                                            cbd.setbkPrice(cusbookdetails[1]);
                                            return cbd;
                                        })
                                        .collect(Collectors.toList());
                                //After Reading the File, it is initializing the Table
                                ObservableList<Book> cusbookdetails = FXCollections.observableArrayList(custbklist);
                                custtable.setItems(cusbookdetails); 
                                ownbooktable.setItems(bookdetails);
                                //This is to Change the Label, welcoming User Based on Points.
                                String PointStatus;

                                if(cusPoints < 1000)
                                    PointStatus = "Silver";
                                else
                                    PointStatus = "Gold";
                                //This is the display message, to change the label.
                                header.setText("Welcome " + cusUser + "! You have " + cusPoints + ". Your status is " + PointStatus);

                            }
                        }
                        
                        custchecksc.close(); //Closes the customer.txt
                    //This should finish checking for customer input, then it moves on.


                    }catch (Exception e) {}
                    //For entiner the owner start screen
                    if(checkUser.equals(adminuser) && checkPw.equals(adminpw)) { //This is checking for owner
                        System.out.println("Login Valid"); //Debugging
                        ownerStage.setScene(owner_start);
                        ownerStage.show();
                        primaryStage.close();
                        txtuser.clear();
                        txtpw.clear();

                    }

                    //Clearing the Entries when other scenarios do not occur
                    else {
                        //Clearing the Inputs to Allow for Another Input
                        System.out.println("Clearing Inputs"); //Debugging
                        txtuser.clear();
                        txtpw.clear(); }


                }
            } );

            // Customer Screen - Features

            //Logout Button
            Logout.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    System.out.println("Logging Out - Customer"); //Debugging
                    primaryStage.setScene(login_start);
                    primaryStage.show();
                    customerstage.close();
                }
            });
            //In progres: It only checks to see what button is pressed, and prints the selected rows.
            //Buying Books Based on Selected Checkbox
            infoButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    //Fix this Code
                    for(Book p : custtable.getItems()) {
                        //System.out.printf("(%sSelected)%n", p.isSelected() "" : "not ");
                        System.out.println(custtable.getSelectionModel().getSelectedItems());
                        //Need to copy this into another file to calculate points, TC & Status
                        //CheckBox.isSelected();
                    }
                    System.out.println("Test");
                }
            });




            //Owner_Start Screen Setups
            
            //Owner_start - Opens Books
            ownbtn1.setOnAction(new EventHandler<ActionEvent>() { //Open Books Database
                public void handle(ActionEvent event) {
                    System.out.print("Owner Book"); //Debugging
                    ownbookstage.setScene(owner_book);
                    ownbookstage.show();
                    ownerStage.close();

                }
            } );

            //Owner - Books : Adding Books
            addButtonown.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {

                    try {
                        checkUser1 = addbookName.getText();
                        //Reading all the Stuff in Text File
                        byte[] bookbytes = Files.readAllBytes(Paths.get("book.txt"));
                        String s1 = new String(bookbytes);

                        //Check if the Username is already there
                        if(s1.indexOf(checkUser1) != -1)
                            System.out.println("Book Name is Already There");
                        else {
                            File ownbookWriter = new File("book.txt");
                            PrintWriter oBW = new PrintWriter(new FileOutputStream(ownbookWriter, true));
                            oBW.append("\n" + addbookName.getText() + "," + addbookPrice.getText());
                            oBW.close();
                            //Reading the File to Update the Information
                            Collection<Book>bklist = Files.readAllLines(new File("book.txt").toPath())
                                    .stream()
                                    .map(line -> {
                                        String[] bookdetails = line.split(",");
                                        Book bd = new Book();
                                        bd.setbkName(bookdetails[0]);
                                        bd.setbkPrice(bookdetails[1]);
                                        return bd;

                            })
                            .collect(Collectors.toList());
                            ObservableList<Book> bookdetails = FXCollections.observableArrayList(bklist);
                            //This is reloading the Table with the New Information
                            ownbooktable.setItems(bookdetails);
                            custtable.setItems(cusbookdetails);
                            ownbookstage.setScene(owner_book);
                            ownbookstage.show();

                        }

                    }catch (IOException e) {
                        System.out.println("Error Occured");
                        e.printStackTrace();
                    }

                    //Clearing the Book Entry so More can be added.
                    addbookName.clear();
                    addbookPrice.clear();
                    System.out.println("Added Customer!"); //Debugging: letting builder know working code

                }

            });

            //Owner -  Books : Remove Books
            removeButtonown.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    
                    System.out.println("Removing Book"); //Debugging: Removal Checker
                    Book selectedItem1 = ownbooktable.getSelectionModel().getSelectedItem();
                    ownbooktable.getItems().remove(selectedItem1); //This grabs the selected row

                    String linetoRemove1 = selectedItem1.getbkName(); //This grabs only the name
                    System.out.println(linetoRemove1); //This sends to the terminal the term being removed
                   
                    //Establishing the old file to delete, and the temp new file being renamed.
                    String filepath1 = "book.txt";
                    String tempFile1 = "tempbook.txt";
                    File oldFile1 = new File(filepath1);
                    File newFile1 = new File(tempFile1);


                    String currentLine1 = null;
                    String data1[];
                    //This is to start reading the lines and start printing.
                    try {
                        FileWriter fw1 = new FileWriter(tempFile1, true);
                        BufferedWriter bw1 = new BufferedWriter(fw1);
                        PrintWriter pw1 = new PrintWriter(bw1);

                        FileReader fr1 = new FileReader(filepath1);
                        BufferedReader br1 = new BufferedReader(fr1);

                        //Reading File to Count Lines - Books
                        int booklines = 0;
                        int bcountcondition; //This is amount of lines after removal

                        FileReader binput = new FileReader("book.txt"); //Reading the Book Inputs
                        LineNumberReader bcount = new LineNumberReader(binput); //Counter

                        String bline = bcount.readLine();

                        if(bcount.ready()) {
                            while(bline != null) {
                                booklines = bcount.getLineNumber();
                                bline = bcount.readLine(); }
                            booklines += 1;
                        }
                        bcount.close();
                        bcountcondition = booklines -2; //The 2 comes from : 1 line break and 1 term being removed
                        System.out.println("There are now: " + bcountcondition + " lines."); //Debugging: Confirming the # of lines

                        //

                        //This is for the actual iteration and making of the new file, in which a temp file is deleted
                        int y = 0;

                        while((currentLine1 = br1.readLine()) != null) {
                            data1 = currentLine1.split(",");
                            //So this will iterate n - 1 amount of times
                            if (y < bcountcondition)
                            {
                            if(!(data1[0].equalsIgnoreCase(linetoRemove1))) {
                                pw1.println(currentLine1); }  //This print and then line breaks
                            y++; }
                            else {
                                if (!(data1[0].equalsIgnoreCase(linetoRemove1))) {
                                    pw1.print(currentLine1); //This prints without the line break, covering the nth case.
                                    System.out.println("Removal Works"); //Debugging
                                    System.out.println(currentLine1); //More debugging, checking to see the last line
                                }
                            }
                        }
                        //Closing all the opened text files, to allow deletion.
                        pw1.flush();
                        pw1.close();
                        fr1.close();
                        br1.close();
                        bw1.close();
                        fw1.close();


                        //Delete, build, rename to replace.
                        oldFile1.delete();
                        File dump1 = new File(filepath1);
                        newFile1.renameTo(dump1);



                    }catch (Exception e) { }

                }

            });

            //Owner -  Books : Back Button
            backButtonown.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    //A generally simple codes that goes back in screen.
                    System.out.println("Going Back"); //Debugging
                    addbookName.clear();
                    addbookPrice.clear();
                    ownerStage.setScene(owner_start);
                    ownerStage.show();
                    ownbookstage.close();
                }
            } );
            //Owner_start - Opens Customer Manager
            ownbtn2.setOnAction(new EventHandler<ActionEvent>() { //Open Customers Database
                public void handle(ActionEvent event) {
                    System.out.println("Owner Customer"); //Debugging
                    owncuststage.setScene(owner_customer);
                    owncusttable.setItems(custdetails); //Intializing the Customer Table
                    owncuststage.show();
                    ownerStage.close();

                }
            } );

            //Owner -  Customer Manager : Adding Customers
            addButtoncust.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {


                    
                    try {
                        checkUser2 = addUser.getText();
                        //Reading all the Stuff in Text File
                        byte[] custbytes = Files.readAllBytes(Paths.get("customer.txt"));
                        String s = new String(custbytes);

                        //Check if the Username is already there
                        if(s.indexOf(checkUser2) != -1)
                            System.out.println("Username already there. Be sure to make username different from your password");
                        else {
                            File owncustWriter = new File("customer.txt");
                            PrintWriter oW = new PrintWriter(new FileOutputStream(owncustWriter, true));
                            oW.append("\n" + addUser.getText() + "," + addPw.getText() + "," + "0");
                            oW.close();

                            //Reading the file after the added entry happens, so that it upddates.
                            Collection<Customer>cslist = Files.readAllLines(new File("customer.txt").toPath())
                                    .stream()
                                    .map(line -> {
                                        String[] custdetails = line.split(",");
                                        Customer cd = new Customer();
                                        cd.setUser(custdetails[0]);
                                        cd.setPw(custdetails[1]);
                                        cd.setPoints(custdetails[2]);
                                        return cd;

                             })
                            .collect(Collectors.toList());
                            ObservableList<Customer> custdetails = FXCollections.observableArrayList(cslist);
                            //Initializing the table values so that its updated.
                            owncusttable.setItems(custdetails);
                            owncuststage.setScene(owner_customer);
                            owncuststage.show();

                        }

                    }catch (IOException e) {
                        System.out.println("Error Occured");
                        e.printStackTrace();
                    }

                    //Clearing up the username password entries, to allow for more entries.
                    addUser.clear();
                    addPw.clear();
                    System.out.println("Added Customer!");
                }

            });

            //Owner -  Customer Manager : Remove Customers
            removeButtoncust.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    //Grabbing the selected row and Deleting It
                    System.out.println("Removing Customer");
                    Customer selectedItem = owncusttable.getSelectionModel().getSelectedItem();
                    owncusttable.getItems().remove(selectedItem);

                    //Getting the user from the selected row.

                    String linetoRemove = selectedItem.getUser();
                    System.out.println(linetoRemove);

                    //Setting up the old and new files
                    String filepath = "customer.txt";
                    String tempFile = "tempcust.txt";
                    File oldFile = new File(filepath);
                    File newFile = new File(tempFile);


                    String currentLine = null;
                    String data[];



                    try {
                        FileWriter fw = new FileWriter(tempFile, true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter pw = new PrintWriter(bw);

                        FileReader fr = new FileReader(filepath);
                        BufferedReader br = new BufferedReader(fr);

                        //Read the File to Count Lines.


                        int lines = 0;
                        int countcondition;

                        FileReader input = new FileReader("customer.txt");
                        LineNumberReader count = new LineNumberReader(input);

                        String line = count.readLine();

                        if(count.ready())
                        {
                            while(line != null) {
                            lines = count.getLineNumber();
                            line = count.readLine();
                            }

                            lines+=1;
                        }

                        count.close();
                        countcondition = lines - 2;
                        System.out.println("There are now: " + countcondition + " lines.");

                        int x = 0;


                        while((currentLine = br.readLine()) != null) { //While the current line is not empty

                            data = currentLine.split(","); //It keeps seperating with the commas as intended

                            if(x < countcondition)
                            {

                            //Do the line n - 1 times conditional if statement
                              if(!(data[0].equalsIgnoreCase(linetoRemove))) { //It will do this, ignoring the case there is a removal line
                                  pw.println(currentLine);
                                  System.out.println(data[0]);
                            }  //This is the problem code/Combat this by doing it n-1 times, and then recover with the last n case.
                            x++; }
                            //Print without the return key, this is to cover the last n case, so pw.print(currentLine);
                            else {
                                if (!(data[0].equalsIgnoreCase(linetoRemove))) {
                                    pw.print(currentLine);
                                    System.out.println("Removal Works"); //Debuggings
                                }
                                //System.out.println(currentLine); //Debugging
                            }
                        }
                        //Close the Writer and Scanners for the System
                        pw.flush();
                        pw.close();
                        fr.close();
                        br.close();
                        bw.close();
                        fw.close();

                        //Delete, build, rename, replace.
                        oldFile.delete();
                        File dump = new File(filepath);
                        newFile.renameTo(dump);
                       




                    }catch (Exception e) { }

                }

            });

            //Owner -  Customer Manager : Back Button
            backButtoncust.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    System.out.println("Going Back"); //Debugging
                    addUser.clear();
                    addPw.clear();
                    ownerStage.setScene(owner_start);
                    ownerStage.show();
                    owncuststage.close();
                }
            } );

            //Owner_start - Logs Out the System
            ownbtn3.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    System.out.println("Logging Out"); //Debugging
                    primaryStage.setScene(login_start);
                    primaryStage.show();
                    ownerStage.close();
                }
            } );

            //Opening the Login
            primaryStage.show();
            primaryStage.setScene(login_start);




    }

    //Open Main
    public static void main(String[] args) { launch(args); }





}
