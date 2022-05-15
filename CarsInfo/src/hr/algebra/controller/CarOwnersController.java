/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;

import hr.algebra.dao.RepositoryFactory;
import hr.algebra.utilities.FileUtils;
import hr.algebra.utilities.ImageUtils;
import hr.algebra.utilities.MaskUtils;
import hr.algebra.utilities.ValidationUtils;
import hr.algebra.viewmodel.CarOwnerViewModel;
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
public class CarOwnersController implements Initializable {

    private Map<TextField, Label> validationMap;

    private final ObservableList<CarOwnerViewModel> list = FXCollections.observableArrayList();

    private CarOwnerViewModel selectedCarOwnerViewModel;

    private final String DELETE_FAILED = "Delete failed. There are cars owned by this person.";

    @FXML
    private TableView<CarOwnerViewModel> tvCarOwners;
    @FXML
    private TableColumn<CarOwnerViewModel, String> tcFirstName;
    @FXML
    private TableColumn<CarOwnerViewModel, String> tcLastName;
    @FXML
    private TableColumn<CarOwnerViewModel, Integer> tcAge;
    @FXML
    private TableColumn<CarOwnerViewModel, String> tcEmail;
    @FXML
    private Label lbPicture;
    @FXML
    private TextField tfFirstName;
    @FXML
    private Label lbFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private Label lbLastName;
    @FXML
    private TextField tfAge;
    @FXML
    private Label lbAge;
    @FXML
    private TextField tfEmail;
    @FXML
    private Label lbEmail;
    @FXML
    private ImageView ivCarOwner;
    @FXML
    private TabPane tpOwnerContent;
    @FXML
    private Tab tabListOwners;
    @FXML
    private Tab tabEditOwner;
    @FXML
    private Label lbDeleteError;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initValidation();
        MaskUtils.addIntegerMask(tfAge);
        initCarOwner();
        initTable();
        setListeners();
    }

    @FXML
    private void edit() {
        clearDeleteError();
        if (tvCarOwners.getSelectionModel().getSelectedItem() != null) {
            bindCarOwner(tvCarOwners.getSelectionModel().getSelectedItem());
            tpOwnerContent.getSelectionModel().select(tabEditOwner);
        }
    }

    @FXML
    private void delete() {
        try {
            if (tvCarOwners.getSelectionModel().getSelectedItem() != null && RepositoryFactory.getRepository().isSafeToDeleteOwner(tvCarOwners.getSelectionModel().getSelectedItem().getCarOwnerIdProperty().get())) {
                list.remove(tvCarOwners.getSelectionModel().getSelectedItem());
            } else {
                lbDeleteError.textProperty().set(DELETE_FAILED);
                lbDeleteError.visibleProperty().set(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(CarOwnersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void upload(ActionEvent event) {
        File file = FileUtils.uploadFileDialog(tfFirstName.getScene().getWindow(), "jpg", "jpeg", "png", "jfif");
        if (file != null) {
            Image image = new Image(file.toURI().toString());

            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);

            try {
                selectedCarOwnerViewModel.getCarOwner().setPicture(ImageUtils.imageToByteArray(image, ext));
                ivCarOwner.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(CarOwnersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void commit(ActionEvent event) {
        if (formValid()) {
            selectedCarOwnerViewModel.getCarOwner().setFirstName(tfFirstName.getText().trim());
            selectedCarOwnerViewModel.getCarOwner().setLastName(tfLastName.getText().trim());
            selectedCarOwnerViewModel.getCarOwner().setEmail(tfEmail.getText().trim());
            selectedCarOwnerViewModel.getCarOwner().setAge(Integer.valueOf(tfAge.getText().trim()));

            if (selectedCarOwnerViewModel.getCarOwner().getIDCarOwner() == 0) {
                list.add(selectedCarOwnerViewModel);
            } else {
                try {
                    RepositoryFactory.getRepository().updateCarOwner(selectedCarOwnerViewModel.getCarOwner());
                    tvCarOwners.refresh();
                } catch (Exception ex) {
                    Logger.getLogger(CarOwnersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            selectedCarOwnerViewModel = null;
            tpOwnerContent.getSelectionModel().select(tabListOwners);
            resetForm();
        }
    }

    @FXML
    private void switchToMainView(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/Cars.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(CarsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initValidation() {
        validationMap = Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(tfFirstName, lbFirstName),
                new AbstractMap.SimpleImmutableEntry<>(tfLastName, lbLastName),
                new AbstractMap.SimpleImmutableEntry<>(tfAge, lbAge),
                new AbstractMap.SimpleImmutableEntry<>(tfEmail, lbEmail)
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void setListeners() {
        tpOwnerContent.setOnMouseClicked(event -> {
            if (tpOwnerContent.getSelectionModel().getSelectedItem().equals(tabEditOwner)) {
                clearDeleteError();
                bindCarOwner(null);
            }
        });

        tvCarOwners.getSelectionModel().selectedItemProperty().addListener(change -> {
            clearDeleteError();
        });

        list.addListener(new ListChangeListener<CarOwnerViewModel>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends CarOwnerViewModel> change) {
                if (change.next()) {
                    if (change.wasRemoved()) {
                        change.getRemoved().forEach(covm -> {
                            try {
                                RepositoryFactory.getRepository().deleteCarOwner(covm.getCarOwner());
                            } catch (Exception ex) {
                                Logger.getLogger(CarOwnersController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    } else if (change.wasAdded()) {
                        change.getAddedSubList().forEach(covm -> {
                            try {
                                int idOwner = RepositoryFactory.getRepository().addCarOwner(covm.getCarOwner());
                                covm.getCarOwner().setIDCarOwner(idOwner);

                            } catch (Exception ex) {
                                Logger.getLogger(CarOwnersController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                }
            }

        });
    }

    private void initTable() {
        tcFirstName.setCellValueFactory(owner -> owner.getValue().getCarOwnerFirstNameProperty());
        tcLastName.setCellValueFactory(owner -> owner.getValue().getCarOwnerLastNameProperty());
        tcEmail.setCellValueFactory(owner -> owner.getValue().getCarOwnerEmailProperty());
        tcAge.setCellValueFactory(owner -> owner.getValue().getCarOwnerAgeProperty().asObject());
        tvCarOwners.setItems(list);
    }

    private void initCarOwner() {
        try {
            RepositoryFactory.getRepository().getCarOwners().forEach(owner -> list.add(new CarOwnerViewModel(owner)));
        } catch (Exception ex) {
            Logger.getLogger(CarMakersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void bindCarOwner(CarOwnerViewModel carOwnerViewModel) {
        resetForm();

        selectedCarOwnerViewModel = carOwnerViewModel != null ? carOwnerViewModel : new CarOwnerViewModel(null);
        tfFirstName.setText(selectedCarOwnerViewModel.getCarOwnerFirstNameProperty().get());
        tfLastName.setText(selectedCarOwnerViewModel.getCarOwnerLastNameProperty().get());
        tfEmail.setText(selectedCarOwnerViewModel.getCarOwnerEmailProperty().get());
        tfAge.setText(String.valueOf(selectedCarOwnerViewModel.getCarOwnerAgeProperty().get()));

        ivCarOwner.setImage(
                selectedCarOwnerViewModel.getPictureProperty().get() != null
                ? new Image(new ByteArrayInputStream(selectedCarOwnerViewModel.getPictureProperty().get()))
                : new Image(new File("src/assets/no_image.png").toURI().toString())
        );
    }

    private void resetForm() {
        validationMap.values().forEach(label -> label.setVisible(false));
        lbPicture.setVisible(false);
    }

    private boolean formValid() {
        final AtomicBoolean ok = new AtomicBoolean(true);

        validationMap.keySet().forEach(tf -> {
            if (tf.getText().trim().isEmpty()
                    || tf.getId().contains("Email") && !ValidationUtils.isValidEmail(tf.getText().trim())) {
                ok.set(false);
                validationMap.get(tf).setVisible(true);
            } else {
                validationMap.get(tf).setVisible(false);
            }
        });

        if (selectedCarOwnerViewModel.getPictureProperty().get() == null) {
            lbPicture.setVisible(true);
            ok.set(false);
        } else {
            lbPicture.setVisible(false);
        }

        return ok.get();
    }

    private void clearDeleteError() {
        lbDeleteError.textProperty().set("");
        lbDeleteError.visibleProperty().set(false);
    }

}
