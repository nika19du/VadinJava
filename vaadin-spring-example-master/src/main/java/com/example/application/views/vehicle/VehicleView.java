package com.example.application.views.vehicle;

import com.example.application.data.Vehicle;
import com.example.application.data.VehicleRepo;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Автомобили")
@Route(value = "vehicle", layout = MainLayout.class)
@AnonymousAllowed
public class VehicleView extends VerticalLayout {

    private final VehicleRepo vehicleRepo;
    private final Grid<Vehicle> grid = new Grid<>(Vehicle.class);
    private final TextField searchField = new TextField();
    private final Button addButton = new Button("Добави автомобил", new Icon(VaadinIcon.PLUS));
    private final Button editButton = new Button("Редактирай автомобил", new Icon(VaadinIcon.EDIT));
    private final Button deleteButton = new Button("Изтрий автомобил", new Icon(VaadinIcon.TRASH));

    public VehicleView(VehicleRepo vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
        configureGrid();
        configureButtons();

        HorizontalLayout header = new HorizontalLayout(searchField, addButton, editButton, deleteButton);
        add(header, grid);

        refreshGrid();
    }

    private void configureGrid() {
        grid.setItems(vehicleRepo.findAll());

        grid.removeAllColumns();
        grid.addColumn(Vehicle::getRegistrationNumber).setHeader("Регистрационен номер").setAutoWidth(true);
        grid.addColumn(Vehicle::getModel).setHeader("Модел").setAutoWidth(true);
        grid.addColumn(vehicle -> vehicle.isParked() ? "Да" : "Не").setHeader("Колата в паркинга ли е?").setAutoWidth(true);
        grid.addColumn(vehicle -> vehicle.getLastStayIn() != null ? vehicle.getLastStayIn().toString() : "-").setHeader("Последно време на влизане").setAutoWidth(true);
        grid.addColumn(vehicle -> vehicle.getLastStayOut() != null ? vehicle.getLastStayOut().toString() : "-").setHeader("Последно време на излизане").setAutoWidth(true);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
    }

    private void configureButtons() {
        addButton.addClickListener(e -> openVehicleDialog(new Vehicle()));
        editButton.setEnabled(false);
        editButton.addClickListener(e -> {
            Vehicle selectedVehicle = grid.getSelectedItems().stream().findFirst().orElse(null);
            if (selectedVehicle != null) {
                openVehicleDialog(selectedVehicle);
            } else {
                Notification.show("Моля, изберете автомобил за редакция.");
            }
        });
        deleteButton.setEnabled(false);
        deleteButton.addClickListener(e -> {
            Vehicle selectedVehicle = grid.getSelectedItems().stream().findFirst().orElse(null);
            if (selectedVehicle != null) {
                ConfirmDialog confirmDialog = new ConfirmDialog("Изтриване на автомобил",
                        "Сигурен ли сте, че искате да изтриете избрания автомобил",
                        "Изтрий",
                        event -> {
                            vehicleRepo.delete(selectedVehicle);
                            refreshGrid();
                        });
                confirmDialog.open();
            } else {
                Notification.show("Моля, изберете автомобил за изтриване.");
            }
        });

        grid.asSingleSelect().addValueChangeListener(event -> {
            boolean selectedItem = event.getValue() != null;
            editButton.setEnabled(selectedItem);
            deleteButton.setEnabled(selectedItem);
        });

        searchField.setPlaceholder("Търсене...");
        searchField.addValueChangeListener(event -> {
            String searchTerm = event.getValue().toLowerCase().trim();
            List<Vehicle> filteredList = vehicleRepo.findAll().stream()
                    .filter(vehicle -> vehicle.getRegistrationNumber().toLowerCase().contains(searchTerm) ||
                            vehicle.getModel().toLowerCase().contains(searchTerm))
                    .collect(Collectors.toList());
            grid.setItems(filteredList);
        });
    }

    private void openVehicleDialog(Vehicle vehicle) {
        VehicleDialog vehicleDialog = new VehicleDialog(vehicle, vehicleRepo, this);
        vehicleDialog.open();
    }

    public void refreshGrid() {
        List<Vehicle> vehicles = vehicleRepo.findAll();
        grid.setItems(vehicles);
    }
}
