package com.example.application.views.helloworld;

import com.example.application.data.Role;
import com.example.application.data.User;
import com.example.application.services.UserService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.ValidationException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class UserDialog extends Dialog {
    private final User user;
    private final UserService userService;
    private TextField username = new TextField("Потребителско име");
    private EmailField emial = new EmailField("Email");
    private TextField name = new TextField("Име");
    private PasswordField passwordField1 = new PasswordField("Парола");
    private PasswordField passwordField2 = new PasswordField("Парола 2");
    private MultiSelectComboBox<Role> roles = new MultiSelectComboBox<>("Роли");
    BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
    private boolean userUpdated = false;
    private Button saveButton;

    public UserDialog(User user, UserService userService) {
        this.user = user;
        this.userService = userService;
        initContent();
    }

    private void initContent() {

        setHeaderTitle(user.getId() == null ? "Добави потребител" : "Промени потребител");

        roles.setItems(Role.values());
        roles.setItemLabelGenerator(Role::name);
        FormLayout formLayout = new FormLayout();
        formLayout.add(username, name, emial, passwordField1, passwordField2, roles);
        add(formLayout);
        binder.bindInstanceFields(this);
        binder.readBean(user);
        Button saveButton = getSaveButton();
        Button cancelButton = new Button("Откажи", e -> close());
        getFooter().add(cancelButton, saveButton);
    }

    private Button getSaveButton() {
        saveButton = new Button("Добави");
        saveButton.addClickListener(l -> {
            BinderValidationStatus<User> validationStatus = binder.validate();
            if (validationStatus.isOk()) {
                try {
                    String passwordField1Value = passwordField1.getValue();
                    String passwordField2Value = passwordField2.getValue();
                    if (StringUtils.isNoneBlank(passwordField1Value, passwordField2Value)) {
                        if (StringUtils.equals(passwordField1Value, passwordField2Value)) {
                            user.setHashedPassword(userService.encodePassword(passwordField1Value));
                        } else {
                            Notification.show("Въведете еднакви пароли!");
                            return;
                        }
                    }
                    binder.writeBean(user);
                    userService.update(user);
                    userUpdated = true;
                    close();
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return saveButton;
    }

    public void addSaveButtonClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        saveButton.addClickListener(listener);
    }

    public boolean isUserUpdated() {
        return userUpdated;
    }
}
