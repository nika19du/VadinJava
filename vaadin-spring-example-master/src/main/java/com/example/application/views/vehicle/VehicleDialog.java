package com.example.application.views.vehicle;

import com.example.application.data.Type;
import com.example.application.data.Vehicle;
import com.example.application.data.VehicleRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.component.checkbox.Checkbox;
import java.awt.*;

public class VehicleDialog extends Dialog {
        private final VehicleRepo vehicleRepo;
        private final TextField registrationField = new TextField("Регистрационен номер");
        private final TextField modelField = new TextField("Модел");
        private final ComboBox<Type> typeComboBox = new ComboBox<>("Тип автомобил");
        private final DateTimePicker lastStayInField = new DateTimePicker("Последно време на влизане");
        private final DateTimePicker lastStayOutField = new DateTimePicker("Последно време на излизане");
        private final Checkbox isInParkingCheckbox = new Checkbox("Колата в паркинга ли е?");
        private final BeanValidationBinder<Vehicle> binder = new BeanValidationBinder<>(Vehicle.class);
        private Vehicle vehicle;
        private final VehicleView vehicleView;

        public VehicleDialog(Vehicle vehicle, VehicleRepo vehicleRepo, VehicleView vehicleView) {
            this.vehicle = vehicle;
            this.vehicleRepo = vehicleRepo;
            this.vehicleView = vehicleView; // Assign the VehicleView instance
            initialize();
        }

    private void initialize() {
            setHeaderTitle(vehicle.getId() == null ? "Добави автомобил" : "Редактирай автомобил");

            typeComboBox.setItems(Type.values());

            FormLayout formLayout = new FormLayout();
            formLayout.add(
                    registrationField,
                    modelField,
                    typeComboBox,
                    lastStayInField,
                    lastStayOutField,
                    isInParkingCheckbox
            );
            add(formLayout);

            binder.bind(registrationField, Vehicle::getRegistrationNumber, Vehicle::setRegistrationNumber);
            binder.bind(modelField, Vehicle::getModel, Vehicle::setModel);
            binder.bind(typeComboBox, Vehicle::getType, Vehicle::setType);
            binder.bind(lastStayInField, Vehicle::getLastStayIn, Vehicle::setLastStayIn);
            binder.bind(lastStayOutField, Vehicle::getLastStayOut, Vehicle::setLastStayOut);
            binder.bind(isInParkingCheckbox, Vehicle::isInParking, Vehicle::setInParking);
            binder.setBean(vehicle);

            Button saveButton = new Button(vehicle.getId() == null ? "Добави" : "Запази");
            saveButton.addClickListener(e -> saveVehicle());
            Button cancelButton = new Button("Откажи", e -> close());

            add(saveButton, cancelButton);
            vehicleView.refreshGrid();
        }

        public void setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
            binder.setBean(vehicle);
            setHeaderTitle(vehicle.getId() == null ? "Добави автомобил" : "Редактирай автомобил");
        }

        private void saveVehicle() {
            BinderValidationStatus<Vehicle> validationStatus = binder.validate();
            if (validationStatus.isOk()) {
                try {
                    binder.writeBean(vehicle);
                    vehicleRepo.save(vehicle);
                    vehicleView.refreshGrid();
                    close();
                } catch (ValidationException e) {
                    Notification.show("Грешка при запазване на автомобила: " + e.getMessage());
                }
            } else {
                Notification.show("Моля, попълнете всички задължителни полета.");
            }
        }

    }
