package com.collegeproject.webservice;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.collegeproject.codetype.UserRole;
import com.collegeproject.model.Seller;
import com.collegeproject.model.User;
import com.collegeproject.service.SellerService;
import com.collegeproject.service.UserService;

@Path("/demodata")
@Component
public class Demodata {

	@Autowired
	UserService userService;

	@Autowired
	SellerService sellerService;

	@POST
	public void generateDemoData() throws NoSuchAlgorithmException {
		User user = new User();
		user.setUserName("Ashwal");
		user.setPassword("p");
		user.setEmail("ashwalappi@gmail.com");
		user.setUserRole(UserRole.User);
		user.setConfirmPassword("p");
		user.setCreatedDate(LocalDateTime.now());
		user.setLastModifiedDate(LocalDateTime.now());
		user.setProfileImage(
				"https://scontent.fmaa1-1.fna.fbcdn.net/v/t1.0-1/p320x320/12651209_10208285161471976_8980166677029189801_n.jpg?_nc_cat=0&oh=e8bf5d1854b2eb9860d9890035fe3565&oe=5B8289AF");
		user = userService.validateAndCreateCustomer(user);
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("user", user);

		user = new User();
		user.setUserName("Vinay");
		user.setPassword("p");
		user.setEmail("vinaykumarcs.ait@gmail.com");
		user.setUserRole(UserRole.Seller);
		user.setConfirmPassword("p");
		user.setCreatedDate(LocalDateTime.now());
		user.setLastModifiedDate(LocalDateTime.now());
		user.setProfileImage(
				"https://upload.wikimedia.org/wikipedia/en/thumb/1/17/Batman-BenAffleck.jpg/200px-Batman-BenAffleck.jpg");
		Seller seller = new Seller();
		seller.setCreatedDate(LocalDateTime.now());
		seller.setLastModifiedDate(LocalDateTime.now());
		seller.setDescription("Test seller...");
		seller.setName("Indian Technologies..");
		seller.setUser(user);
		seller = sellerService.validateAndCreate(seller);

		System.out.println("*****************************Done with Demo Data *************************************");
	}

}
