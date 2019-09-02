package bd.edu.seu.wcfrontendnavigation.ui.student;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;

import java.awt.*;


public class Container extends VerticalLayout {

    public Container() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Tabs menu = new Tabs();
        Tab homeTab = new Tab("Home");
        Tab infoTab = new Tab("Info");
        Tab paymentTab = new Tab("Payment");
        menu.add(homeTab, infoTab, paymentTab);
        menu.setOrientation(Tabs.Orientation.VERTICAL);

        menu.setWidth("200px");


        //ApplicationStatusForm==========
        FormLayout applicationStatusForm = new FormLayout();
        TextField userIdField = new TextField("UserId");
        TextField feesDueField = new TextField("Fees To Pay");
        TextField feesPayedField = new TextField("Paid");
        TextField applicationStatusField = new TextField("Status");
        applicationStatusForm.add(userIdField, feesDueField, feesPayedField, applicationStatusField);


        horizontalLayout.add(menu, applicationStatusForm);
        add(horizontalLayout);

        menu.addSelectedChangeListener(menus -> {
            String tab = menus.getSelectedTab().getLabel();
            if(tab.equals("Info")){
               horizontalLayout.remove(applicationStatusForm);
           }
           else if(tab.equals("Payment")){
                horizontalLayout.remove(applicationStatusForm);
            }
           else {
               horizontalLayout.add(applicationStatusForm);
            }
        });

    }
}
