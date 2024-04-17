package dk.dtu.view.campaign;


// Assuming CampaignMenu is correctly defined in your project, it might look something like this:

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CampaignMenu {

    public void showCampaign() {
        Stage campaignStage = new Stage();
        Label campaignLabel = new Label("Welcome to the Campaign mode!");
        StackPane campaignLayout = new StackPane(campaignLabel);
        Scene campaignScene = new Scene(campaignLayout, 700, 700);
        campaignStage.setScene(campaignScene);
        campaignStage.setTitle("Campaign Mode");
        campaignStage.show();
    }
}


