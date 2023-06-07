/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author huyda
 */
public class Book {
    StringProperty bkName = new SimpleStringProperty();
    StringProperty bkPrice = new SimpleStringProperty();

    //Getter and Setter for the Tables
    //Book Name
    public final StringProperty bkNameProperty() { return this.bkName; }
    public final java.lang.String getbkName() { return this.bkNameProperty().get(); }
    public final void setbkName(final java.lang.String bkName) { this.bkNameProperty().set(bkName); }
    //Book Price
    public final StringProperty bkPriceProperty() { return this.bkPrice; }
    public final java.lang.String getbkPrice() { return this.bkPriceProperty().get(); }
    public final void setbkPrice(final java.lang.String bkPrice) { this.bkPriceProperty().set(bkPrice); }
    
    
}
