package bd.edu.seu.wcfrontendnavigation.ui.student;

import bd.edu.seu.wcfrontendnavigation.enums.Status;
import bd.edu.seu.wcfrontendnavigation.model.Program;
import bd.edu.seu.wcfrontendnavigation.model.Student;
import bd.edu.seu.wcfrontendnavigation.service.ProgramService;
import bd.edu.seu.wcfrontendnavigation.service.StudentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;

public class Container extends VerticalLayout {

    private StudentService studentService;
    private ProgramService programService;
    private Binder<Student> studentBinder;
    private Student loggedStudent;

    public Container(ProgramService programService, StudentService studentService, Student loggedStudent) {
        this.studentService = studentService;
        this.loggedStudent = loggedStudent;
        this.programService = programService;
        studentBinder = new Binder<>();

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        //SideBar Nav Menu==============
        Tabs menu = new Tabs();
        Tab homeTab = new Tab("Home");
        Tab infoTab = new Tab("Update Into");
        Tab paymentTab = new Tab("Convocation");
        menu.add(homeTab, infoTab, paymentTab);

        menu.setOrientation(Tabs.Orientation.VERTICAL);
        menu.setWidth("200px");

        //ApplicationStatusForm==========
        FormLayout applicationStatusForm = new FormLayout();
        TextField userIdField = new TextField("Student ID");
        TextField feesDueField = new TextField("Fees To Pay");
        TextField feesPayedField = new TextField("Paid");
        TextField applicationStatusField = new TextField("Status");

        userIdField.setReadOnly(true);
        feesDueField.setReadOnly(true);
        feesPayedField.setReadOnly(true);
        applicationStatusField.setReadOnly(true);
        applicationStatusForm.add(userIdField, feesDueField, feesPayedField, applicationStatusField);
        applicationStatusForm.setWidth("400px");


        userIdField.setValue(loggedStudent.getId().toString());
        feesDueField.setValue("6500");              //hardcoded for demonstration purpose =============
        if(loggedStudent.getFeePaid() != null)
            feesPayedField.setValue(loggedStudent.getFeePaid().toString().trim());
        if(loggedStudent.getFeePaid() != null && loggedStudent.getFeePaid().equals(Double.parseDouble(feesDueField.getValue()))){
            loggedStudent.setPaymentStatus(Status.ACCEPTED);
            studentService.updateStudent(loggedStudent.getId(), loggedStudent);
            applicationStatusField.setValue(Status.ACCEPTED.toString());
        }
        else{
            applicationStatusField.setValue(Status.NOT_PAID.toString());

            // this feature needs to be update in future ============
        }

        //End of home page==============

        //TODO Info page================

        //Payment page==================

        //Payment Details form============
        FormLayout paymentDetailsForm = new FormLayout();

        TextField studentIdField = new TextField("Student ID");
        TextField paidAmountField = new TextField("Paid Amount");
        TextField trxIdField = new TextField("TrxID");
        TextField emailField = new TextField("Email");
        Button submitPaymentButton = new Button("Submit");
        submitPaymentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        studentIdField.setReadOnly(true);
        studentIdField.setValue(loggedStudent.getId().toString());
        paidAmountField.setRequired(true);
        trxIdField.setRequired(true);
        paymentDetailsForm.add(studentIdField, paidAmountField, trxIdField, emailField, submitPaymentButton);
        paymentDetailsForm.setWidth("400px");

        studentBinder
                .forField(studentIdField)
                .withConverter(new StringToLongConverter(""))
                .bind(Student::getId, Student::setId);

        studentBinder
                .forField(paidAmountField)
                .asRequired()
                .withConverter(new StringToDoubleConverter("Amount must be a decimal value"))
                .bind(Student::getFeePaid, Student::setFeePaid);

        studentBinder
                .forField(trxIdField)
                .asRequired()
                .bind(Student::getTrxID, Student::setTrxID);

        studentBinder
                .forField(emailField)
                .withValidator(email -> email.endsWith(".com"), "email should be like 'example@example.com'")
                .withValidator(email -> email.contains("@"), "email should be like 'example@example.com'")
                .bind(Student::getEmail, Student::setEmail);



        Div area = new Div();
        area.setWidth("200px");
        horizontalLayout.add(menu, area, applicationStatusForm);
        add(horizontalLayout);

        submitPaymentButton.addClickListener(event -> {

            try {
                studentBinder.writeBean(loggedStudent);
                Student updatedStudent = studentService.updateStudent(loggedStudent.getId(), loggedStudent);


                Notification
                        .show(updatedStudent.getId().toString() + " saved!")
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            } catch (ValidationException e) {
                Notification.show("Error").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        menu.addSelectedChangeListener(menus -> {
            String tab = menus.getSelectedTab().getLabel();
            if(tab.equals("Update Into")){
                horizontalLayout.remove(applicationStatusForm);
                horizontalLayout.remove(paymentDetailsForm);
            }
            else if(tab.equals("Convocation")){

                Program program = programService.getProgram(loggedStudent.getProgram());
                double cgReq = program.getMinReqCgpaForGraduation();
                double crReq = program.getMinCrReqForGraduation();

                if( loggedStudent.getCrCompleted() != null && loggedStudent.getCgpa() != null && loggedStudent.getCgpa() >= cgReq && loggedStudent.getCrCompleted() >= crReq) {
                    horizontalLayout.remove(applicationStatusForm);
                    horizontalLayout.add(paymentDetailsForm);
                }
                else{
                    Notification.show("You are not eligible to register for Convocation").addThemeVariants(NotificationVariant.LUMO_ERROR);
                    horizontalLayout.remove(paymentDetailsForm);
                    horizontalLayout.add(applicationStatusForm);
                }
            }
            else {
                horizontalLayout.remove(paymentDetailsForm);
                horizontalLayout.add(applicationStatusForm);
            }
        });
    }
}
