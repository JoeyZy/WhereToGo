package com.luxoft.wheretogo.controller.editor;

import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class UserEditor extends PropertyEditorSupport {

	@Autowired
	private UsersService usersService;

	@Override
	public void setAsText(String text) {
		User user = usersService.findById(Integer.valueOf(text));
		this.setValue(user);
	}
}
