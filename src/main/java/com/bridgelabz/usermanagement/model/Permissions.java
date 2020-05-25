package com.bridgelabz.usermanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "permissionsDetails")
public class Permissions {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private boolean addDashboard = false;
	private boolean deleteDashboard = false;
	private boolean modifyDashboard = false;
	private boolean readDashboard = false;
	private boolean addSetting = false;
	private boolean deleteSetting = false;
	private boolean modifySetting = false;
	private boolean readSetting = false;
	private boolean addUserInformation = false;
	private boolean deleteUserInformation = false;
	private boolean modifyUserInformation = false;
	private boolean readUserInformation = false;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAddDashboard() {
		return addDashboard;
	}

	public void setAddDashboard(boolean addDashboard) {
		this.addDashboard = addDashboard;
	}

	public boolean isDeleteDashboard() {
		return deleteDashboard;
	}

	public void setDeleteDashboard(boolean deleteDashboard) {
		this.deleteDashboard = deleteDashboard;
	}

	public boolean isModifyDashboard() {
		return modifyDashboard;
	}

	public void setModifyDashboard(boolean modifyDashboard) {
		this.modifyDashboard = modifyDashboard;
	}

	public boolean isReadDashboard() {
		return readDashboard;
	}

	public void setReadDashboard(boolean readDashboard) {
		this.readDashboard = readDashboard;
	}

	public boolean isAddSetting() {
		return addSetting;
	}

	public void setAddSetting(boolean addSetting) {
		this.addSetting = addSetting;
	}

	public boolean isDeleteSetting() {
		return deleteSetting;
	}

	public void setDeleteSetting(boolean deleteSetting) {
		this.deleteSetting = deleteSetting;
	}

	public boolean isModifySetting() {
		return modifySetting;
	}

	public void setModifySetting(boolean modifySetting) {
		this.modifySetting = modifySetting;
	}

	public boolean isReadSetting() {
		return readSetting;
	}

	public void setReadSetting(boolean readSetting) {
		this.readSetting = readSetting;
	}

	public boolean isAddUserInformation() {
		return addUserInformation;
	}

	public void setAddUserInformation(boolean addUserInformation) {
		this.addUserInformation = addUserInformation;
	}

	public boolean isDeleteUserInformation() {
		return deleteUserInformation;
	}

	public void setDeleteUserInformation(boolean deleteUserInformation) {
		this.deleteUserInformation = deleteUserInformation;
	}

	public boolean isModifyUserInformation() {
		return modifyUserInformation;
	}

	public void setModifyUserInformation(boolean modifyUserInformation) {
		this.modifyUserInformation = modifyUserInformation;
	}

	public boolean isReadUserInformation() {
		return readUserInformation;
	}

	public void setReadUserInformation(boolean readUserInformation) {
		this.readUserInformation = readUserInformation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
