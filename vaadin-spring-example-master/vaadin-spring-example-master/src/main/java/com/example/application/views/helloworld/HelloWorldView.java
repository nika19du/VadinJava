package com.example.application.views.helloworld;

import com.example.application.data.User;
import com.example.application.services.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.Set;
import java.util.stream.Collectors;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class HelloWorldView extends VerticalLayout {

    private final UserService userService;
    private TextField name;
    private Button sayHello;
    private Grid<User> grid = new Grid<>(User.class, false);
    private Button edit;

    public HelloWorldView(UserService userService) {
        this.userService = userService;
        init();

    }

    private void init() {
        configureSayHello();
        configureUserButtons();
        configureUserGrid();
        setSizeFull();
    }

    private void configureUserButtons() {
        Button addUser = new Button("Добави потребител", new Icon(VaadinIcon.PLUS_CIRCLE));
        addUser.addClickListener(l -> openUserDialog(new User()));
        edit = new Button(new Icon(VaadinIcon.EDIT));
        edit.setEnabled(false);
        edit.addClickListener(l -> openUserDialog(grid.getSelectedItems().iterator().next()));
        add(new HorizontalLayout(addUser, edit));
    }

    private void openUserDialog(User user) {
        UserDialog dialog = new UserDialog(user, userService);
        dialog.addSaveButtonClickListener(l1 -> {
            if (dialog.isUserUpdated()) {
                refreshGrid();
            }
        });
        dialog.open();
    }

    private void refreshGrid() {
        grid.setItems(userService.list());
    }


    private void configureUserGrid() {
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        configureGridColumns(grid);
        grid.addSelectionListener(l -> {
            Set<User> allSelectedItems = l.getAllSelectedItems();
            if (!allSelectedItems.isEmpty()) {
                String userNames = allSelectedItems.stream().map(User::getName)
                        .collect(Collectors.joining(", "));
                Notification.show("Селектираните потребители са: " + userNames,
                        5000, Notification.Position.BOTTOM_CENTER);
            }
            edit.setEnabled(l.getAllSelectedItems().size() == 1);
        });
        refreshGrid();
        grid.setSizeFull();
        add(grid);
    }

    private static void configureGridColumns(Grid<User> grid) {
        grid.addColumn(User::getName).setHeader("Име").setSortable(true);
        grid.addColumn(User::getUsername).setHeader("Потребителско име").setSortable(true);
        grid.addColumn(user -> user.getRoles().stream().map(Enum::name)
                .collect(Collectors.joining(", "))).setHeader("Роли");
    }

    private void configureSayHello() {
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> Notification.show("Hello " + name.getValue()));
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);

        HorizontalLayout horizontalLayout = new HorizontalLayout(name, sayHello);
        horizontalLayout.setVerticalComponentAlignment(Alignment.END, name, sayHello);
        add(horizontalLayout);
    }

}
