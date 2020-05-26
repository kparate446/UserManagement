package com.bridgelabz.usermanagement.services;

import com.bridgelabz.usermanagement.response.Response;

public interface IDashboardServices {
	Response getAllTimeAgeGroup(String token);
	Response getAllLocation(String token);
	Response getAllGender(String token);
}
