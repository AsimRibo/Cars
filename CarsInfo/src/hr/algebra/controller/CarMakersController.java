/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;

import hr.algebra.dao.RepositoryFactory;
import hr.algebra.utilities.FileUtils;
import hr.algebra.utilities.ImageUtils;
import hr.algebra.viewmodel.CarMakerViewModel;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author asim2
 */
public class CarMakersController implements Initializable {

    private Map<TextField, Label> validationMap;
    
    private final ObservableList<CarMakerViewModel> list = FXCollections.observableArrayList();
    
    private CarMakerViewModel selectedCarMakerViewModel;
    
    private final String DELETE_FAILED = "Delete failed. There are cars made by this car maker.";
    
    
    @FXML
    private TabPane tpMakerContent;
    @FXML
    private Tab tabListMakers;
    @FXML
    private TableView<CarMakerViewModel> tvCarMakers;
    @FXML
    private TableColumn<CarMakerViewModel, String> tcCarMakerName;
    @FXML
    private Tab tabEditMakers;
    @FXML
    private ImageView ivCarMaker;
    @FXML
    private Label lbName;
    @FXML
    private TextField tfName;
    @FXML
    private Label lbDeleteError;
    @FXML
    private Label lbPicture;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initValidation();
        initCarMakers();
        initTable();
        setListeners();
    }    

    @FXML
    private void edit(ActionEvent event) {
        clearDeleteError();
        if (tvCarMakers.getSelectionModel().getSelectedItem() != null) {
            bindCarMaker(tvCarMakers.getSelectionModel().getSelectedItem());
            tpMakerContent.getSelectionModel().select(tabEditMakers);
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        try {
            if (tvCarMakers.getSelectionModel().getSelectedItem() != null && RepositoryFactory.getRepository().isSafeToDeleteMaker(tvCarMakers.getSelectionModel().getSelectedItem().getCarMaker().getIDCarMaker())) {
                list.remove(tvCarMakers.getSelectionModel().getSelectedItem());
            }
            else{
                lbDeleteError.textProperty().set(DELETE_FAILED);
                lbDeleteError.visibleProperty().set(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(CarOwnersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void upload(ActionEvent event) {
        File file = FileUtils.uploadFileDialog(tfName.getScene().getWindow(), "jpg", "jpeg", "png", "jfif");
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            
            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            
            try {
                selectedCarMakerViewModel.getCarMaker().setPicture(ImageUtils.imageToByteArray(image, ext));
                ivCarMaker.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(CarOwnersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void commit(ActionEvent event) {
        if (formValid()) {
            selectedCarMakerViewModel.getCarMaker().setCarMakerName(tfName.getText().trim());
            
            if (selectedCarMakerViewModel.getCarMaker().getIDCarMaker()== 0) {
                list.add(selectedCarMakerViewModel);
            }
            else{
                try {
                    RepositoryFactory.getRepository().updateCarMaker(selectedCarMakerViewModel.getCarMaker());
                    tvCarMakers.refresh();
                } catch (Exception ex) {
                    Logger.getLogger(CarOwnersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            selectedCarMakerViewModel = null;
            tpMakerContent.getSelectionModel().select(tabListMakers);
            resetForm();
        }
    }
    
    @FXML
    private void switchToMainView(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/Cars.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(CarsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initValidation() {
        validationMap = Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(tfName, lbName)
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); //in case of future updates
    }

    private void initCarMakers() {
        try {
            RepositoryFactory.getRepository().getCarMakers().forEach(maker -> list.add(new CarMakerViewModel(maker)));
        } catch (Exception ex) {
            Logger.getLogger(CarMakersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initTable() {
        tcCarMakerName.setCellValueFactory(maker -> maker.getValue().getCarMakerNameProperty());
        tvCarMakers.setItems(list);
    }

    private void setListeners() {
        tpMakerContent.setOnMouseClicked(event -> {
            if (tpMakerContent.getSelectionModel().getSelectedItem().equals(tabEditMakers)) {
                clearDeleteError();
                bindCarMaker(null);
            }
        });
        
        tvCarMakers.getSelectionModel().selectedItemProperty().addListener(change -> {
            clearDeleteError();
        });
        
        list.addListener(new ListChangeListener<CarMakerViewModel>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends CarMakerViewModel> change) {
                if (change.next()) {
                    if (change.wasRemoved()) {
                        change.getRemoved().forEach(cmvm -> {
                            try {
                                RepositoryFactory.getRepository().deleteCarMaker(cmvm.getCarMaker());
                            } catch (Exception ex) {
                                Logger.getLogger(CarOwnersController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    } 
                    else if (change.wasAdded()) {
                        change.getAddedSubList().forEach(cmvm -> {
                            try {
                                int idMaker = RepositoryFactory.getRepository().addCarMaker(cmvm.getCarMaker());
                                cmvm.getCarMaker().setIDCarMaker(idMaker);
                                
                                
                            } catch (Exception ex) {
                                Logger.getLogger(CarOwnersController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                }
            }
            
        });
    }

    private void clearDeleteError() {
        lbDeleteError.textProperty().set("");
        lbDeleteError.visibleProperty().set(false);
    }

    private void bindCarMaker(CarMakerViewModel carMakerViewModel) {
        resetForm();
        
        selectedCarMakerViewModel = carMakerViewModel != null ? carMakerViewModel : new CarMakerViewModel(null);
        tfName.setText(selectedCarMakerViewModel.getCarMakerNameProperty().get());
        
        ivCarMaker.setImage(
                selectedCarMakerViewModel.getPictureProperty().get() != null 
                        ? new Image(new ByteArrayInputStream(selectedCarMakerViewModel.getPictureProperty().get())) 
                        : new Image(new File("src/assets/no_image_car.jpg").toURI().toString())
        );
    }

    private void resetForm() {
        validationMap.values().forEach(label -> label.setVisible(false));
        lbPicture.setVisible(false);
    }

    private boolean formValid() {
        final AtomicBoolean ok = new AtomicBoolean(true);
        
        validationMap.keySet().forEach(tf -> {
            if (tf.getText().trim().isEmpty()) {
                ok.set(false);
                validationMap.get(tf).setVisible(true);
            }
            else{
                validationMap.get(tf).setVisible(false);
            }
        });
        
        if (selectedCarMakerViewModel.getPictureProperty().get() == null) {
            lbPicture.setVisible(true);
            ok.set(false);
        }
        else{
            lbPicture.setVisible(false);
        }
        
        return ok.get();
    }

    
    
}
