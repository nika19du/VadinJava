package com.example.application.views.vehicle;

import com.example.application.data.Vehicle;
import com.example.application.data.VehicleRepo;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Автомобили")
@Route(value = "vehicle", layout = MainLayout.class)
@AnonymousAllowed
public class VehicleView extends VerticalLayout {
    public VehicleView(VehicleRepo vehicleRepo) {
        initContent(vehicleRepo);
    }

    private void initContent(final VehicleRepo vehicleRepo) {
        final Grid<Vehicle> vehicleGrid = getVehicleGrid(vehicleRepo);
        final Button addButton = new Button(new Icon(VaadinIcon.PLUS));
        final Button editButton = new Button(new Icon(VaadinIcon.EDIT));
        editButton.setEnabled(false);
        final Button removeButton = new Button(new Icon(VaadinIcon.TRASH));
        removeButton.setEnabled(false);
        removeButton.addClickListener(l -> {
            ConfirmDialog confirmDialog = new ConfirmDialog("Изтриване на автомобил",
                    "Сигурен ли сте, че искате да изтриете избрания автомобил",
                    "Изтрий",
                    ll -> {
                        vehicleRepo.delete(vehicleGrid.getSelectedItems().iterator().next());
                        resetGridData(vehicleRepo, vehicleGrid);
                    });
            confirmDialog.open();
        });
        final HorizontalLayout buttons = new HorizontalLayout(addButton, editButton, removeButton);
        vehicleGrid.addSelectionListener(l -> {
            boolean empty = l.getAllSelectedItems().isEmpty();
            editButton.setEnabled(!empty);
            removeButton.setEnabled(!empty);
        });
        add(buttons, vehicleGrid);
    }

    private Grid<Vehicle> getVehicleGrid(VehicleRepo vehicleRepo) {
        Grid<Vehicle> vehicleGrid = new Grid<>(Vehicle.class, false);
        vehicleGrid.addColumn(Vehicle::getRegistration).setHeader("Регистрационен номер").setResizable(true);
        vehicleGrid.addColumn(Vehicle::getModel).setHeader("Модел").setResizable(true);
        vehicleGrid.addColumn(Vehicle::getType).setHeader("Тип автомобил").setResizable(true);
        vehicleGrid.addComponentColumn((item) -> {
            Icon icon;
            if (item.isParked()) {
                icon = VaadinIcon.CHECK.create();
                icon.setColor("green");
            } else {
                icon = VaadinIcon.CLOSE.create();
                icon.setColor("red");
            }
            return icon;
        }).setHeader("Колата в паркинга ли е?").setResizable(true);
        vehicleGrid.addColumn(Vehicle::getLastStayIn).setHeader("Последно време на влизане").setResizable(true);
        vehicleGrid.addColumn(Vehicle::getLastStayOut).setHeader("Последно време на излизане").setResizable(true);
        resetGridData(vehicleRepo, vehicleGrid);
        return vehicleGrid;
    }

    private void resetGridData(VehicleRepo vehicleRepo, Grid<Vehicle> vehicleGrid) {
        vehicleGrid.setItems(vehicleRepo.findAll());
    }
}
