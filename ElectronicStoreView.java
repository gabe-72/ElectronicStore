import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ElectronicStoreView extends Pane {
    private Label cartLabel;
    private TextField salesField, revenueField, avgField;
    private ListView<Product> stockList, popularList;
    private ListView<String> cartList;
    private Button resetBtn, addBtn, removeBtn, completeBtn;

    public ElectronicStoreView() {
        // creating the labels
        Label summaryLabel = new Label("Store Summary:");
        Label stockLabel = new Label("Store Stock:");
        cartLabel = new Label("Current Cart: ($0.00)");
        Label salesLabel = new Label("# Sales:");
        Label revenueLabel = new Label("Revenue:");
        Label avgLabel = new Label("$ / Sale:");
        Label popularLabel = new Label("Most Popular Items:");

        // creating the text fields
        salesField = new TextField();
        revenueField = new TextField();
        avgField = new TextField();

        salesField.setPrefWidth(95);
        revenueField.setPrefWidth(95);
        avgField.setPrefWidth(95);

        salesField.setEditable(false);
        revenueField.setEditable(false);
        avgField.setEditable(false);

        // creating the list views
        stockList = new ListView<>();
        cartList = new ListView<>();
        popularList = new ListView<>();

        // creating the buttons
        resetBtn = new Button("Reset Store");
        addBtn = new Button("Add to Cart");
        removeBtn = new Button("Remove from Cart");
        completeBtn = new Button("Complete Sale");
        resetBtn.setMinSize(140, 40); // setting the sizes
        addBtn.setMinSize(140, 40);
        removeBtn.setMinSize(140, 40);
        completeBtn.setMinSize(140, 40);


        // The entire window is divided into three parts, the left column, middle column and right column
        // a VBox is used for each of these columns
        // and these columns are then added into an HBox which will contain everything
        VBox left = new VBox(5);
        VBox middle = new VBox(5);
        VBox right = new VBox(5);


        // ---adding elements to the left VBox---
        HBox sales = new HBox(5); // stores the label + text field of # sales
        sales.getChildren().addAll(salesLabel, salesField);
        sales.setAlignment(Pos.CENTER_RIGHT);

        HBox revenue = new HBox(5); // stores the label + text field of revenue
        revenue.getChildren().addAll(revenueLabel, revenueField);
        revenue.setAlignment(Pos.CENTER_RIGHT);

        HBox price = new HBox(5); // stores the label + text field of price/sale
        price.getChildren().addAll(avgLabel, avgField);
        price.setAlignment(Pos.CENTER_RIGHT);

        left.getChildren().addAll(summaryLabel, sales, revenue, price, popularLabel, popularList, resetBtn);
        left.setPrefSize(170, 360);
        left.setAlignment(Pos.CENTER);


        // ---adding elements to the middle VBox---
        middle.getChildren().addAll(stockLabel, stockList, addBtn);
        middle.setPrefSize(280, 360);
        middle.setAlignment(Pos.CENTER);


        // ---adding elements to the right VBox---
        HBox removeCompleteBtns = new HBox(); // adding the remove and complete buttons into one HBox
        removeCompleteBtns.getChildren().addAll(removeBtn, completeBtn);

        right.getChildren().addAll(cartLabel, cartList, removeCompleteBtns);
        right.setPrefSize(280, 360);
        right.setAlignment(Pos.CENTER);

        // adding all the columns into one HBox
        HBox aHBox = new HBox(10);
        aHBox.getChildren().addAll(left, middle, right);
        aHBox.relocate(25, 20);

        getChildren().add(aHBox);
        setPrefSize(800, 400);
    }

    public ListView<Product> getStockList() { return stockList; }
    public ListView<String> getCartList() { return cartList; }
    public Button getAddBtn() { return addBtn; }
    public Button getRemoveBtn() { return removeBtn; }
    public Button getCompleteBtn() { return completeBtn; }
    public Button getResetBtn() { return resetBtn; }

    public void update(ElectronicStore model) {
        // updating the list views
        popularList.setItems(FXCollections.observableArrayList(model.getPopularItems()));
        stockList.setItems(FXCollections.observableArrayList(model.getStockList()));

        // making sure that the selected item stays selected after updating the cart list
        int selectedCartItem = cartList.getSelectionModel().getSelectedIndex();
        cartList.setItems(FXCollections.observableArrayList(model.getCartList()));
        cartList.getSelectionModel().select(selectedCartItem);

        // enable/disable buttons
        addBtn.setDisable(stockList.getSelectionModel().getSelectedIndex() < 0);
        removeBtn.setDisable(cartList.getSelectionModel().getSelectedIndex() < 0);
        completeBtn.setDisable(cartList.getItems().isEmpty());

        // updating cart label
        cartLabel.setText(String.format("Current Cart: ($%.2f)", model.getCartTotal()));

        // text field text
        salesField.setText("" + model.getNumSales());
        revenueField.setText(String.format("%.2f", model.getRevenue()));
        double averagePerSale = model.getAveragePerSale();
        if (averagePerSale == 0)
            avgField.setText("N/A");
        else
            avgField.setText(String.format("%.2f", averagePerSale));
    }
}
