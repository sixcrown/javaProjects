package hextodec;

//import common.EnumCalculateMethod;
//import common.EnumFunctionType;
import common.EnumCalMethod;
import java.util.EnumSet;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 *
 * @author Bartosz Kawalkiewicz
 * @version 2.0
 */
public class ConversionView extends Region {

    /**
     * label for argument
     */
    private Label Arg;
    
    /**
     * text field for argument
     */
    private TextField textFieldArg;
   
    /**
    * label for the result
    */
    private Label labelResult;
    /**
     * text field for the result value
     */
    private TextField textFieldResult;
    /**
     * ComboBox for choosing Calculation direction
     */
    private ComboBox<EnumCalMethod> comboBoxCalculateMethod;

    /**
    * Button to make the calculation
    */
    private Button button;

    /**
     * constructor for view class which adds things to the scene 
     */
    public ConversionView() {
        this.getChildren().add(createGui());

    }

    //<editor-fold defaultstate="collapsed" desc="gui">
    /**
     * sets all the values for comboBoxes
     */
    private void setComboBoxValue() {
        EnumSet.allOf(EnumCalMethod.class).forEach(e -> {
            this.comboBoxCalculateMethod.getItems().add(e);
        });
    }

    /**
     * creates GUI for the whole view
     *
     * @return GridPane
     */
    private GridPane createGui() {
        this.Arg = new Label("Argument");
        this.labelResult = new Label("result: ");

        this.textFieldArg = new TextField();
        this.textFieldResult = new TextField();
        this.textFieldResult.setEditable(false);

        this.comboBoxCalculateMethod = new ComboBox<EnumCalMethod>();
        this.setComboBoxValue();
        this.comboBoxCalculateMethod.getSelectionModel().selectFirst();

        this.button = new Button("calculate");

        GridPane gridPane = new GridPane();

        int col = 0;
        int row = 0;
        gridPane.add(this.Arg, col++, row);
        gridPane.add(this.textFieldArg, col, row, 2, 1);
        col = 0;
        row++;
        col = 0;
        row++;
        gridPane.add(this.comboBoxCalculateMethod, col++, row);
        gridPane.add(this.button, col, row++);
        col = 0;
        row++;
        gridPane.add(this.labelResult, col++, row);
        gridPane.add(this.textFieldResult, col, row);
        

        GridPane.setHalignment(this.button, HPos.RIGHT);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(7);
        gridPane.setVgap(7);
        return gridPane;
    }
    //</editor-fold>

    /**
     *
     * @return String with Text from textField holding the argument to be converted 
     */
    public String getTextFieldArg() 
    {
        return this.textFieldArg.getText();
    }


    /**
     *
     * @return EnumCalMethod type of calculation direction hex to dec or dec to hex 
     */
    public EnumCalMethod getCalculateMethod() 
    {
        return comboBoxCalculateMethod.getSelectionModel().getSelectedItem();
    }


    /**
     *
     * @return TextField the text field which contains convertion result
     */
    public TextField getTextFieldResult() 
    {
        return this.textFieldResult;
    }

    /**
     *
     * @return Button button to calculate 
     */
    public Button getButton()
    {
        return this.button;
    }
    
    /**
     * sets argument TextField text value 
     * @param TextFieldArg String
     */
    public void setTextFieldArg(String TextFieldArg)
        {
           this.textFieldArg.setText(TextFieldArg);
        }
    
    /**
     * sets result textField text value
     *
     * @param textFieldResult String
     */
    public void setTextFieldResult(String textFieldResult) {
        this.textFieldResult.setText(textFieldResult);
    }
    
    /**
     * sets selected comboBox value
     *
     * @param methodType EnumCalculateMethod
     */
    public void setComboBoxMethodType(EnumCalMethod methodType) {
        this.comboBoxCalculateMethod.getSelectionModel().select(methodType);
    }

}
