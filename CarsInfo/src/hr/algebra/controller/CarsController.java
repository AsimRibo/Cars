/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;

import hr.algebra.dao.RepositoryFactory;
import hr.algebra.model.CarMaker;
import hr.algebra.model.CarOwner;
import hr.algebra.utilities.FileUtils;
import hr.algebra.utilities.ImageUtils;
import hr.algebra.utilities.MaskUtils;
import hr.algebra.viewmodel.CarMakerViewModel;
import hr.algebra.viewmodel.CarOwnerViewModel;
import hr.algebra.viewmodel.CarViewModel;
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
import javafx.scene.control.ComboBox;
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
public class CarsController implements Initializable {

    private Map<TextField, Label> validationMap;

    private final ObservableList<CarViewModel> list = FXCollections.observableArrayList();

    private final ObservableList<CarMakerViewModel> listOfMakers = FXCollections.observableArrayList();
    private final ObservableList<CarOwnerViewModel> listOfOwners = FXCollections.observableArrayList();

    private CarViewModel selectedCarViewModel;

    @FXML
    private TableView<CarViewModel> tvCars;
    @FXML
    private TableColumn<CarViewModel, String> tcCarName;
    @FXML
    private TableColumn<CarViewModel, Integer> tcPower;
    @FXML
    private TableColumn<CarViewModel, Integer> tcYear;
    @FXML
    private TableColumn<CarViewModel, CarMaker> tcMaker;
    @FXML
    private TableColumn<CarViewModel, CarOwner> tcOwner;
    @FXML
    private TabPane tpCarsContent;
    @FXML
    private Tab tabListCars;
    @FXML
    private Tab tabEditCar;
    @FXML
    private ImageView ivPicture;
    @FXML
    private Label lbPicture;
    @FXML
    private TextField tfCarName;
    @FXML
    private Label lbCarName;
    @FXML
    private TextField tfPower;
    @FXML
    private Label lbPower;
    @FXML
    private TextField tfYear;
    @FXML
    private Label lbYear;
    @FXML
    private ComboBox<CarMakerViewModel> cbMaker;
    @FXML
    private ComboBox<CarOwnerViewModel> cbOwner;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initValidation();
        MaskUtils.addIntegerMask(tfPower);
        MaskUtils.addIntegerMask(tfYear);
        initCar();
        initCarMakers(); //for sake of speed since list will not change in this view
        initCarOwners();
        initComboBoxes();
        initTable();
        setListeners();
    }

    @FXML
    private void edit(ActionEvent event) {
        if (tvCars.getSelectionModel().getSelectedItem() != null) {
            bindCar(tvCars.getSelectionModel().getSelectedItem());
            tpCarsContent.getSelectionModel().select(tabEditCar);
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        if (tvCars.getSelectionModel().getSelectedItem() != null) {
            list.remove(tvCars.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void switchToMakersView(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/CarMakers.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(CarsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void switchToOwnersView(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/CarOwners.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(CarsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initValidation() {
         validationMap = Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(tfCarName, lbCarName),
                new AbstractMap.SimpleImmutableEntry<>(tfPower, lbPower), //will not be used because of Integer mask
                new AbstractMap.SimpleImmutableEntry<>(tfYear, lbYear) //will not be used because of Integer mask
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void initCar() {
        try {
            RepositoryFactory.getRepository().getCars().forEach(car -> list.add(new CarViewModel(car)));
        } catch (Exception ex) {
            Logger.getLogger(CarMakersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initTable() {
        tcCarName.setCellValueFactory(car -> car.getValue().getCarNameProperty());
        tcPower.setCellValueFactory(car -> car.getValue().getCarPowerProperty().asObject());
        tcYear.setCellValueFactory(car -> car.getValue().getCarYearProperty().asObject());
        tcMaker.setCellValueFactory(car -> car.getValue().getMakerProperty());
        tcOwner.setCellValueFactory(car -> car.getValue().getOwnerProperty());
        tvCars.setItems(list);
    }

    private void setListeners() {
        tpCarsContent.setOnMouseClicked(event -> {
            if (tpCarsContent.getSelectionModel().getSelectedItem().equals(tabEditCar)) {
                bindCar(null);
            }
        });
        
        list.addListener(new ListChangeListener<CarViewModel>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends CarViewModel> change) {
                if (change.next()) {
                    if (change.wasRemoved()) {
                        change.getRemoved().forEach(cvm -> {
                            try {
                                RepositoryFactory.getRepository().deleteCar(cvm.getCar());
                            } catch (Exception ex) {
                                Logger.getLogger(CarOwnersController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    } else if (change.wasAdded()) {
                        change.getAddedSubList().forEach(cvm -> {
                            try {
                                int idCar = RepositoryFactory.getRepository().addCar(cvm.getCar());
                                cvm.getCar().setIDCar(idCar);

                            } catch (Exception ex) {
                                Logger.getLogger(CarOwnersController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                }
            }

        });
    }

    @FXML
    private void upload(ActionEvent event) {
        File file = FileUtils.uploadFileDialog(tfCarName.getScene().getWindow(), "jpg", "jpeg", "png", "jfif");
        if (file != null) {
            Image image = new Image(file.toURI().toString());

            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);

            try {
                selectedCarViewModel.getCar().setPicture(ImageUtils.imageToByteArray(image, ext));
                ivPicture.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(CarOwnersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void commit(ActionEvent event) {
        if (formValid()) {
            selectedCarViewModel.getCar().setCarName(tfCarName.getText().trim());
            selectedCarViewModel.getCar().setPowerInKw(Integer.valueOf(tfPower.getText().trim()));
            selectedCarViewModel.getCar().setYearOfMaking(Integer.valueOf(tfYear.getText().trim()));
            selectedCarViewModel.getCar().setCarOwnerID(cbOwner.getSelectionModel().getSelectedItem().getCarOwner());
            selectedCarViewModel.getCar().setCarMakerID(cbMaker.getSelectionModel().getSelectedItem().getCarMaker());

            if (selectedCarViewModel.getCar().getIDCar() == 0) {
                list.add(selectedCarViewModel);
            } else {
                try {
                    RepositoryFactory.getRepository().updateCar(selectedCarViewModel.getCar());
                    tvCars.refresh();
                } catch (Exception ex) {
                    Logger.getLogger(CarOwnersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            selectedCarViewModel = null;
            tpCarsContent.getSelectionModel().select(tabListCars);
            resetForm();
        }
    }

    private void initComboBoxes() {
        try {
            bindComboBox(cbMaker, listOfMakers);
            bindComboBox(cbOwner, listOfOwners);
        } catch (Exception ex) {
            Logger.getLogger(CarsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initCarMakers() {
        try {
            RepositoryFactory.getRepository().getCarMakers().forEach(m -> listOfMakers.add(new CarMakerViewModel(m)));
        } catch (Exception ex) {
            Logger.getLogger(CarsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initCarOwners() {
        try {
            RepositoryFactory.getRepository().getCarOwners().forEach(o -> listOfOwners.add(new CarOwnerViewModel(o)));
        } catch (Exception ex) {
            Logger.getLogger(CarsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private <T> void bindComboBox(ComboBox<T> cb, ObservableList<T> list) {
        cb.setItems(list);
        cb.getSelectionModel().select(0);
    }

    private void bindCar(CarViewModel carViewModel) {
        resetForm();

        selectedCarViewModel = carViewModel != null ? carViewModel : new CarViewModel(null);
        tfCarName.setText(selectedCarViewModel.getCarNameProperty().get());
        tfPower.setText(String.valueOf(selectedCarViewModel.getCarPowerProperty().get()));
        tfYear.setText(String.valueOf(selectedCarViewModel.getCarYearProperty().get()));
        if (!selectedCarViewModel.getCarNameProperty().get().isEmpty()) {
            cbMaker.getSelectionModel().select(new CarMakerViewModel(selectedCarViewModel.getMakerProperty().get()));
            cbOwner.getSelectionModel().select(new CarOwnerViewModel(selectedCarViewModel.getOwnerProperty().get()));
        }
        ivPicture.setImage(
                selectedCarViewModel.getPictureProperty().get() != null
                ? new Image(new ByteArrayInputStream(selectedCarViewModel.getPictureProperty().get()))
                : new Image(new File("src/assets/no_image_car.jpg").toURI().toString())
        );
    }

    private void resetForm() {
        validationMap.values().forEach(label -> label.setVisible(false));
        cbMaker.getSelectionModel().select(0);
        cbOwner.getSelectionModel().select(0);
        lbPicture.setVisible(false);
    }

    private boolean formValid() {
        final AtomicBoolean ok = new AtomicBoolean(true);

        validationMap.keySet().forEach(tf -> {
            if (tf.getText().trim().isEmpty()) {
                ok.set(false);
                validationMap.get(tf).setVisible(true);
            } else {
                validationMap.get(tf).setVisible(false);
            }
        });

        if (selectedCarViewModel.getPictureProperty().get() == null) {
            lbPicture.setVisible(true);
            ok.set(false);
        } else {
            lbPicture.setVisible(false);
        }

        return ok.get();
    }

}
