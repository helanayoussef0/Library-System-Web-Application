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

public class Customer {
    StringProperty username = new SimpleStringProperty();
    StringProperty password = new SimpleStringProperty();
    StringProperty points = new SimpleStringProperty();

    //Getter and Setter for the Tables
    //Username
    public final StringProperty userProperty() { return this.username; }
    public final java.lang.String getUser() { return this.userProperty().get(); }
    public final void setUser(final java.lang.String username) { this.userProperty().set(username); }
    //Password
    public final StringProperty pwProperty() { return this.password; }
    public final java.lang.String getPw() { return this.pwProperty().get(); }
    public final void setPw(final java.lang.String password) { this.pwProperty().set(password); }
    //Points
    public final StringProperty pointsProperty() { return this.points; }
    public final java.lang.String getPoints() { return this.pointsProperty().get(); }
    public final void setPoints(final java.lang.String points) { this.pointsProperty().set(points); }

}
