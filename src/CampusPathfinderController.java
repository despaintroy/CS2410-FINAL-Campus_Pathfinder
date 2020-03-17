import com.CampusGraph.Buildings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CampusPathfinderController {

    private MapView map;
    static final int TOP_HEIGHT        = 35;
    static final int LEFT_WIDTH        = 180;
    static final int VIEWPORT_WIDTH    = 967;
    static final int VIEWPORT_HEIGHT   = 683;

    @FXML
    public Pane centerPane;
    public HBox topBar;
    public VBox leftBar;
    public Button findPathSubmit;
    public Text tempReadout;
    public ComboBox buildingFrom;
    public ComboBox buildingTo;

    @FXML
    public void findPathClicked() {
        map.drawShortestPath(buildingFrom.getValue().toString(), buildingTo.getValue().toString());
        centerPane = map.getMasterPane();
    }

    @FXML
    public void initialize() {

        new Buildings();

        // Set the dimensions of the viewport
        topBar.setPrefHeight(TOP_HEIGHT);
        leftBar.setPrefWidth(LEFT_WIDTH);

        // Display the temperature
        try {
            tempReadout.setText("Temperature is " + Math.round(Weather.getTempF()) + " ºF");
        } catch (Weather.CannotGetTempException e) {
            tempReadout.setText("Temperature is -- ºF");
            e.printStackTrace();
        }

        // Construct the map view
        map = new MapView(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
//        map.showAllPaths();
        centerPane.getChildren().setAll(map.getMasterPane());

        // Handle clicks on the graph
        centerPane.setOnMouseClicked(e -> {
            map.click(e.getX(), e.getY());
        });
    }
}