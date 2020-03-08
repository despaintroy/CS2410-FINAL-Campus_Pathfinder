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
    static final int VIEWPORT_WIDTH    = 725;
    static final int VIEWPORT_HEIGHT   = 512;

    @FXML
    public Pane centerPane;
    public HBox topBar;
    public VBox leftBar;
    public Button findPathSubmit;
    public Text tempReadout;
    public ComboBox buildingFrom;
    public ComboBox buildingTo;

    @FXML
    public void findPathSubmit() {
        map.findPath(buildingFrom.getValue().toString(), buildingTo.getValue().toString());
        centerPane = map.getMasterPane();
    }

    @FXML
    public void initialize() {

        new Buildings();

        // Set the dimensions of the viewport
        topBar.setPrefHeight(TOP_HEIGHT);
        leftBar.setPrefWidth(LEFT_WIDTH);

//        try {
//            tempReadout.setText("Temperature is " + Math.round(Weather.getTempF()) + " ºF");
//        } catch (Weather.CannotGetTempException e) {
//            tempReadout.setText("Temperature is -- ºF");
//            e.printStackTrace();
//        }
        tempReadout.setText("Temperature is -- ºF");

        map = new MapView(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        map.showAllPaths();
        centerPane.getChildren().setAll(map.getMasterPane());
    }
}