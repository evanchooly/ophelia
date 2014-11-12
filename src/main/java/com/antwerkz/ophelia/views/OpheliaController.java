package com.antwerkz.ophelia.views;

import com.mongodb.MongoClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ResourceBundle;

public class OpheliaController implements Initializable {
    @FXML
    private TreeView<String> dbTree;
    @FXML
    private TextArea findQuery;
    @FXML
    private TextArea updateQuery;
    @FXML
    private TextArea updateOperations;

    private MongoClient mongoClient;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        try {
            mongoClient = new MongoClient();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        TreeItem<String> rootItem = new TreeItem<>("Database");

        dbTree.setRoot(rootItem);
        List<String> names = mongoClient.getDatabaseNames();
        for (String name : names) {
            TreeItem<String> collectionNode = new TreeItem<>(name);
            rootItem.getChildren().add(collectionNode);
            for (String collection : mongoClient.getDB(name).getCollectionNames()) {
                collectionNode.getChildren().add(new TreeItem<>(collection));
            }
        }
    }

    @FXML
    private void quitOphelia(ActionEvent event) {
        System.exit(0);
    }
}
