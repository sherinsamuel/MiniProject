import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ImageUpload {

    public void upload(Stage primaryStage) throws Exception {

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        setExtFilters(fileChooser);
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            openNewImageWindow(file);
        }

    }


    private void setExtFilters(FileChooser chooser){
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    private void openNewImageWindow(File file) throws Exception {
        Stage secondStage = new Stage();
        String finalImg_name;

        try {
            String query = "select cust_acc_no from create_acc order by cust_acc_no desc limit 1";
            ResultSet res = Execute_query.set_con().createStatement().executeQuery(query);
            res.next();


            String img_name = res.getString(1);
            int img_name_no = Integer.parseInt(img_name);
            img_name_no += 1;
            finalImg_name = String.valueOf(img_name_no);
        }
        catch (Exception e)
        {
            finalImg_name="2000";
        }

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem menuItem_Save = new MenuItem("Upload Image");
        menuFile.getItems().addAll(menuItem_Save);
        menuBar.getMenus().addAll(menuFile);

        Label name = new Label(file.getAbsolutePath());
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView();

        String finalImg_name1 = finalImg_name;
        menuItem_Save.setOnAction(event -> {
            System.out.println(file.getAbsolutePath());

                try {
                    Path source = Paths.get(file.getAbsolutePath());
                    Path newdir = Paths.get(System.getProperty("user.home")+"/resources/"+ finalImg_name1);
                    Files.copy(source, newdir, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    Logger.getLogger(
                            ImageUpload.class.getName()).log(Level.SEVERE, null, ex);
                }

            secondStage.close();
        });

        final VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 10));
        vbox.getChildren().addAll(name, imageView);

        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);
        imageView.setImage(image);
        imageView.setSmooth(true);
        imageView.setCache(true);

        Scene scene = new Scene(new VBox(), 400, 350);
        ((VBox)scene.getRoot()).getChildren().addAll(menuBar, vbox);

        secondStage.setTitle(file.getName());
        secondStage.setScene(scene);
        secondStage.showAndWait();
    }

}
