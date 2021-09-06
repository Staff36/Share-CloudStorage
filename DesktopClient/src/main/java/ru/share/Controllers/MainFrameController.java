package ru.share.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j;
import ru.share.Handlers.FilesHandler;
import ru.share.NettyServer;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


@Log4j
public class MainFrameController implements Initializable {
    private NettyServer ns;
    private FilesHandler fileHandler;
    @FXML
    ListView<String> clientFilesList;
    public TextField clientsPath;
    private Image file = new Image("https://svl.ua/image/cache/download_pdf-32x32.png");
    private Image folder = new Image("https://i0.wp.com/cdna.c3dt.com/icon/328326-com.jrdcom.filemanager.png?w=32");
    private Image disc = new Image("https://findicons.com/files/icons/998/airicons/32/hdd.png");
    private Image image = new Image("https://findicons.com/files/icons/1637/file_icons_vs_2/32/png.png");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Thread thread = new Thread(ns = new NettyServer());
//        thread.setDaemon(true);
//        thread.start();
        this.fileHandler = new FilesHandler();
        clientFilesList = new ListView<>();
        refreshClientsList(File.listRoots());
    }



    public void clientMouseClicked(MouseEvent mouseEvent) {

    }

    public void serverMouseClicked(MouseEvent mouseEvent) {

    }

    private void refreshClientsList(File[] files) {
        log.info("refreshing clients list");
        clientFilesList.getItems().clear();
        if (fileHandler.isRoot(fileHandler.getCurrentDirectory())){
            clientsPath.setText("Root");
        } else{
            clientsPath.setText(fileHandler.getCurrentDirectory());
        }
        if (files == null || files.length == 0){
            return;
        }
        fileHandler.updateFilesList(files);
        clientFilesList.getItems().addAll(fileHandler.getListOfFileNames());
        clientFilesList.setCellFactory(param -> new ListCell<String>(){
            private ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty){
                    setText(null);
                    setGraphic(null);
                } else {
                    if(item.endsWith(".png") || item.endsWith(".jpeg")){
                        imageView.setImage(image);
                    } else if (fileHandler.getFileByName(item).isFile()){
                        imageView.setImage(file);
                    } else if(fileHandler.isRoot(item)){
                        imageView.setImage(disc);
                    }else {
                        imageView.setImage(folder);
                    }
                    setText(item);
                    setGraphic(imageView);
                }
            }
        });
        clientFilesList.refresh();

    }
}
