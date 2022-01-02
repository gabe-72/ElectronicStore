import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ElectronicStoreApp extends Application {
    private ElectronicStore model;
    private ElectronicStoreView view;

    public ElectronicStoreApp() {
        model = ElectronicStore.createStore();
    }

    @Override
    public void start(Stage primaryStage) {
        view = new ElectronicStoreView();
        view.update(model);

        // Stock list: enable/disable add button on selecting an item in the stock list
        view.getStockList().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleStockListSelection();
            }
        });

        // Add button handler: adds selected item to cart
        view.getAddBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleAddButton();
            }
        });

        // Cart list: enable/disable remove button on selecting an item in the cart list
        view.getCartList().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleCartListSelection();
            }
        });

        // Remove button handler: removes selected item in cart
        view.getRemoveBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleRemoveButton();
            }
        });

        // Complete Sale button: sells the product in the cart
        view.getCompleteBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleCompleteButton();
            }
        });

        // Reset the store
        view.getResetBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleResetButton();
            }
        });

        primaryStage.setTitle("Electronic Store Application - " + model.getName());
        primaryStage.setScene(new Scene(view));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void handleStockListSelection() {
        view.update(model);
    }

    private void handleCartListSelection() {
        view.update(model);
    }

    private void handleAddButton() {
        Product p = view.getStockList().getSelectionModel().getSelectedItem();
        model.addProdToCart(p,1);
        view.update(model);
    }

    private void handleRemoveButton() {
        String p = view.getCartList().getSelectionModel().getSelectedItem();
        model.addProdToCart(model.findProduct(p),-1);
        view.update(model);
    }

    private void handleCompleteButton() {
        model.sellCartProducts();
        view.update(model);
    }

    private void handleResetButton() {
        model = ElectronicStore.createStore();
        view.update(model);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
