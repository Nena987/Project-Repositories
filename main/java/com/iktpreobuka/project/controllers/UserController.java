package com.iktpreobuka.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.entities.UserEntity.UserRole;
import com.iktpreobuka.project.repositories.UserRepository;

@RestController
@RequestMapping(value = "/project/users")
public class UserController {

	List<UserEntity> users = new ArrayList<UserEntity>();

	@Autowired
	private UserRepository userRepository;

	/*
	 * 1.3 kreirati REST endpoint koji vraća listu korisnika aplikacije • putanja
	 * /project/users
	 */

	@GetMapping
	public Iterable<UserEntity> getAll() {
		return (List<UserEntity>) userRepository.findAll();
	}

	/*
	 * 1.4 kreirati REST endpoint koji vraća korisnika po vrednosti prosleđenog ID a
	 * • putanja /project/users/{ • u slučaju da ne postoji korisnik sa traženom
	 * vrednošću ID a vratiti null
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public UserEntity findUserById(@PathVariable Integer id) {
		if (userRepository.existsById(id)) {
			UserEntity user = userRepository.findById(id).get();
			return user;
		}
		return null;
	}

	/*
	 * 1.5 kreirati REST endpoint koji omogućava dodavanje novog korisnika • putanja
	 * /project/users • u okviru ove metode postavi vrednost atributa user role na
	 * ROLE_CUSTOMER • metoda treba da vrati dodatog korisnika
	 */

	@PostMapping
	public UserEntity createNewUser(@RequestBody UserEntity user) {
		user.setUserRole(UserRole.ROLE_CUSTOMER);
		return userRepository.save(user);
	}

	/*
	 * 1.6 kreirati REST endpoint koji omogućava izmenu postojećeg korisnika •
	 * putanja /project/users/{ • ukoliko je prosleđen ID koji ne pripada nijednom
	 * korisniku metoda treba da vrati null , a u suprotnom vraća podatke korisnika
	 * sa izmenjenim vrednostima • NAPOMENA: u okviru ove metode ne menjati vrednost
	 * atributa user role i password
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public UserEntity changeOneUser(@RequestBody UserEntity changeUser, @PathVariable Integer id) {
		if (userRepository.existsById(id)) {
			UserEntity user = userRepository.findById(id).get();
			if (changeUser.getFirstName() != null)
				user.setFirstName(changeUser.getFirstName());
			if (changeUser.getLastName() != null)
				user.setLastName(changeUser.getLastName());
			if (changeUser.getUsername() != null)
				user.setUsername(changeUser.getUsername());
			if (changeUser.getEmail() != null)
				user.setEmail(changeUser.getEmail());
			return userRepository.save(user);
		}
		return null;
	}

	/*
	 * 1.7 kreirati REST endpoint koji omogućava izmenu atributa user_role
	 * postojećeg korisnika • putanja /project/users/change/{ role/{role} • ukoliko
	 * je prosleđen ID koji ne pripada nijednom korisniku metoda treba da vrati null
	 * , a u suprotnom vraća podatke korisnika sa izmenjenom vrednošću atributa user
	 * role
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/role/{role}")
	public UserEntity changeOneUserRole(@PathVariable Integer id, @PathVariable UserRole role) {
		if (userRepository.existsById(id)) {
			UserEntity user = userRepository.findById(id).get();
			user.setUserRole(role);
			return userRepository.save(user);
		}
		return null;
	}

	/*
	 * 1.8 kreirati REST endpoint koji omogućava izmenu vrednosti atributa password
	 * postojećeg korisnika • putanja /project/ changePassword /{ • kao RequestParam
	 * proslediti vrednosti stare i nove lozinke • ukoliko je prosleđen ID koji ne
	 * pripada nijednom korisniku metoda treba da vrati null , a u suprotnom vraća
	 * podatke korisnika sa izmenjenom vrednošću atributa password • NAPOMENA : da
	 * bi vrednost atributa password mogla da bude zamenjena sa novom vrednošću,
	 * neophodno je da se vrednost stare lozinke korisnika poklapa sa vrednošću
	 * stare lozinke prosleđene kao RequestParam
	 */

	@RequestMapping(method = RequestMethod.PUT, value = "/changePassword/{id}")
	public UserEntity changeOnePassword(@RequestParam String oldPass, @RequestParam String newPass,
			@PathVariable Integer id) {
		if (userRepository.existsById(id)) {
			UserEntity user = userRepository.findById(id).get();
			if (user.getPassword().equals(oldPass))
				user.setPassword(newPass);
			return userRepository.save(user);
		}
		return null;
	}

	/*
	 * 1.9 kreirati REST endpoint koji omogućava brisanje postojećeg korisnika •
	 * putanja /project/users/{ • ukoliko je prosleđen ID koji ne pripada nijednom
	 * korisniku metoda treba da vrati null , a u suprotnom vraća podatke o
	 * korisniku koji je obrisan
	 */

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public UserEntity removeUser(@PathVariable Integer id) {
		if (userRepository.existsById(id)) {
			UserEntity user = userRepository.findById(id).get();
			userRepository.delete(user);
			return user;
		}
		return null;
	}

	/*
	 * 1.10 kreirati REST endpoint koji vraća korisnika po vrednosti prosleđenog
	 * username a • putanja /project/users/by username/{username} • u slučaju da ne
	 * postoji korisnik sa traženim username om vratiti null
	 */

	@RequestMapping(method = RequestMethod.GET, path = "/by-username/{username}")
	public UserEntity findByUsername(@PathVariable String username) {
		UserEntity user = userRepository.findByUsername(username);
		return user;
	}
}
