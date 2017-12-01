package main.java.application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.java.controller.ChronoThread;
import model.Tickets;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * MyFrame
 *
 * @author : Gilles Andrieu [Atecna](http://www.atecna.fr/)
 * @version : 1.0
 */

public class MyFrame extends Application{

    // Form properties
    @FXML private Label actiontargetChrono;
    @FXML private Button playStop;
    @FXML private Button clearAll;
    @FXML private Button pushAll;
    @FXML private TextField ticket_number;
    @FXML private CheckBox billed;
    @FXML private TextArea description;
    @FXML private TableView<Tickets> ticketTableView;

    // Properties
    ChronoThread chrono;
    private LinkedList<Tickets> ticket;
    private Date startTime, endTime;
    final private ObservableList<Tickets> ticketItems;

    private final static String REST_ROOT_URL = "http://localhost:8080/";
    private final Client client = ClientBuilder.newClient();


    /**
     * Constructor
     */
    public MyFrame(){
        // Init thread for the timer
        chrono = new ChronoThread();
        ticket = new LinkedList<Tickets>();

        // Init list tickets
        ticketItems = FXCollections.observableArrayList();

    }

    /**
     * Override start method
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            // Frame title
            primaryStage.setTitle("[Atecna] - Atecmer");
            // Load template config.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("root.fxml"));
            Parent root = loader.load();

            // Show content form in new window
            primaryStage.setScene(new Scene(root, 670, 300));
            // Show window
            primaryStage.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Getter / Setter
     */
    public ChronoThread getChrono() {
        return chrono;
    }


    /**
     * Start timer and event form
     * @param event
     * @throws InterruptedException
     */
    @FXML
    protected void startMyTimer(ActionEvent event) throws InterruptedException {

        try {
            // try if the libelle is start or stop
            if(playStop.getText().compareTo("Play") == 0){
                // Start chrono
                chrono.startThread(actiontargetChrono);

                // change libelle
                playStop.setText("Stop");
                ticket_number.setDisable(true);
                billed.setDisable(true);
                description.setDisable(true);

                // Get date for the timer
                startTime = new Date();

                // Show chrono
                actiontargetChrono.setText(chrono.show());

            }else{
                // Stop chrono
                chrono.show();

                // Get stop date
                endTime = new Date();

                // Add information in table form
                ticket.add(new Tickets( ticket_number.getText(),
                        actiontargetChrono.getText(),
                        startTime, endTime,
                        (billed.isSelected()?"Oui":"Non"),
                        description.getText()
                ));

                // Set the libelle
                playStop.setText("Play");

                ticket_number.setDisable(false);
                ticket_number.setText("");

                description.setDisable(false);
                description.setText("");

                billed.setDisable(false);
                billed.setSelected(true);

                // Init timer at 0
                actiontargetChrono.setText("0:00:00");

                // Add information in table form
                ticketItems.add(ticket.getLast());

                ticketTableView.setItems(ticketItems);

                clearAll.setDisable(false);
                pushAll.setDisable(false);
            }
        }catch(RuntimeException e){
            e.printStackTrace();
        }
    }

    /**
     * Push ticket to web app
     * @param actionEvent
     */
    @FXML
    protected void pushAllTicket(ActionEvent actionEvent) {

        try{
            for(int i=0; i<ticket.size(); i++) {
                if (ticket.get(i).getBilled() == "Oui")
                    ticket.get(i).setBilled("1");
                else
                    ticket.get(i).setBilled("0");


                Entity<Tickets> ticketEntity = Entity.entity(ticket.get(i), MediaType.APPLICATION_XML_TYPE);
                Response response = client.target(REST_ROOT_URL).path("ticket").path("pushticket").request().post(ticketEntity);
            }
            ticketItems.removeAll(ticket);
            ticket.removeAll(ticket);


            ticketTableView.refresh();

            clearAll.setDisable(true);
            pushAll.setDisable(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Clear list tickets
     * @param actionEvent
     */
    @FXML
    protected void clearAllTicket(ActionEvent actionEvent) {

        int index = ticketTableView.getSelectionModel().getFocusedIndex();
        ticket.remove(index);
        ticketItems.remove(index);
        ticketTableView.refresh();

        if(ticket.size() == 0)
            pushAll.setDisable(true);
    }

    /**
     * Main
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
