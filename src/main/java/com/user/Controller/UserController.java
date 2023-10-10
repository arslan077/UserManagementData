package com.user.Controller;

import com.user.Constants;
import com.user.DTO.ContactRequest;
import com.user.DTO.UserRequest;
import com.user.DTO.VehicleRequest;
import com.user.Entity.Contact;
import com.user.Entity.ContactDetails;
import com.user.Entity.User;
import com.user.Entity.Vehicle;
import com.user.ExtendedResponse;
import com.user.Repository.ContactDetailsRepository;
import com.user.Repository.ContactRepository;
import com.user.Repository.UserRepository;

import com.user.Repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

//import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
//@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

//    @RequestMapping("/contact/{userId}")
//    public List<Contact> getContacts(@PathVariable("userId") Long userId){
//        List<Contact> contact = contactRepository.findByUserId(userId);
//        return contact;
//    }


//    @GetMapping("/{userId}")
//    public User getUser(@PathVariable("userId") Long userId) {
//        User user = userRepository.findByUserId(userId);
//        return user;
//
//    }

    @GetMapping("/allusers")
    public List<User> getAllUser() {
        List<User> user = userRepository.findAll();
        return user;

    }

    @PostMapping("/saveuser")
    public String createroles(@Valid @ModelAttribute UserRequest userRequest) {

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPhone(userRequest.getPhone());
        user = userRepository.save(user);
        return "redirect:/listusers";
    }
    @GetMapping("/listusers")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "list-users";
    }
    @GetMapping("/user-data/{userId}")
    public String Userdata(@PathVariable(value = "userId") Long userId, Model model) {
        User user = userRepository.findByUserId(userId);
        model.addAttribute("user", user);

        List<Contact> contacts = contactRepository.findAllByUserId(userId);
        model.addAttribute("contacts", contacts);

        List<Vehicle> vehicles = vehicleRepository.findAllByUserId(userId);
        model.addAttribute("vehicles", vehicles);

        return "user-data";
    }


    @GetMapping("/newuser")
    public String newUserForm(Model model) {
        User user = new User();
        model.addAttribute("userRequest", new UserRequest());
        return "add-user";
    }
    @GetMapping("/newcontact/{userId}")
    public String newContactForm(@PathVariable(value = "userId") Long userId,Model model) {
        ContactRequest contactRequest = new ContactRequest();
        contactRequest.setUserId(userId);
        model.addAttribute("contactRequest",contactRequest);
        model.addAttribute("userId",userId);
        return "add-contact";
    }

    @GetMapping("/delete/{userId}")
    @Transactional
    public String deleteUser(@PathVariable(value = "userId") Long userId) {
        userRepository.deleteByUserId(userId);
        contactRepository.deleteByUserId(userId);
        vehicleRepository.deleteByUserId(userId);

        return "redirect:/listusers";
    }
    @GetMapping("/deletecontact/{CId}")
    @Transactional
    public String deleteUsercontact(@PathVariable(value = "CId") Long CId) {
        Contact contact = contactRepository.findByCId(CId);
        if (contact != null) {
            Long userId = contact.getUserId();
            contactRepository.deleteByCId(CId);
            return "redirect:/user-data/" + userId;
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/deletevehicle/{VId}")
    @Transactional
    public String deleteUservehicle(@PathVariable(value = "VId") Long VId) {
        Vehicle vehicle = vehicleRepository.findByVId(VId);
        if (vehicle != null) {
            Long userId = vehicle.getUserId();
            vehicleRepository.deleteByVId(VId);
            return "redirect:/user-data/"+userId;
        } else {
            return "redirect:/error";
        }

    }

    @GetMapping("/newvehicle/{userId}")
    public String newVehicleForm(@PathVariable(value = "userId") Long userId,Model model) {

        VehicleRequest vehicleRequest = new VehicleRequest();
        vehicleRequest.setUserId(userId);
        model.addAttribute("vehicleRequest",vehicleRequest);
        model.addAttribute("userId",userId);
        return "add-vehicle";
    }
//    @RequestMapping("/vehicle/{userId}")
//    public List<Vehicle> getVehicles(@PathVariable("userId") Long userId){
//        List<Vehicle> vehicle = vehicleRepository.findByUserId(userId);
//        return vehicle;
//    }

    @GetMapping("/verifyUser/{userId}")
    public ResponseEntity<Map<String, String>> verifyUser(@PathVariable Long userId) {
        try {
            User user = userRepository.findByUserId(userId);

            if (user != null) {
                log.info("User with ID {} exists.", userId);
                Map<String, String> response = new HashMap<>();
                response.put("message", "User exists");
                return ResponseEntity.ok(response);
            } else {
                log.info("User with ID {} does not exist.", userId);
                Map<String, String> response = new HashMap<>();
                response.put("message", "User does not exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception ex) {
            log.error("Error while verifying user with ID {}", userId, ex);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @Transactional
    @PostMapping("/savecontact")
    public String savecontact(@Valid @ModelAttribute ContactRequest contactRequest) {
        Long userId = contactRequest.getUserId();
        ResponseEntity<Map<String, String>> verificationResult = verifyUser(userId);
        Contact contact = new Contact();

        if (verificationResult.getStatusCode() == HttpStatus.OK) {


            ContactDetails contactDetails = new ContactDetails();
            contact.setEmail(contactRequest.getEmail());
            contact.setContactName(contactRequest.getContactName());
            contact.setUserId(contactRequest.getUserId());
            contactDetails.setDesignation(contactRequest.getDesignation());
            contactDetails.setOfficeNumber(contactRequest.getOfficeNumber());
            contact.setContactDetails(contactDetails);
            contact = contactRepository.save(contact);
            contactDetails = contactDetailsRepository.save(contactDetails);
            return "redirect:/user-data/"+contact.getUserId();
        } else if (verificationResult.getStatusCode() == HttpStatus.NOT_FOUND) {

            return "redirect:/user-data/"+contact.getUserId();
        }
        return "redirect:/user-data/"+contact.getUserId();

    }

    @PostMapping("/savevehicle")
    public String savevehicle(@Valid @ModelAttribute VehicleRequest vehicleRequest) {
        Long userId = vehicleRequest.getUserId();
        ResponseEntity<Map<String, String>> verificationResult = verifyUser(userId);
        Vehicle vehicle = new Vehicle();
        if (verificationResult.getStatusCode() == HttpStatus.OK) {


            vehicle.setVehicleName(vehicleRequest.getVehicleName());
            vehicle.setVehicleType(vehicleRequest.getVehicleType());
            vehicle.setUserId(vehicleRequest.getUserId());
            vehicle = vehicleRepository.save(vehicle);
            return "redirect:/user-data/"+vehicle.getUserId();
        }
        else if (verificationResult.getStatusCode() == HttpStatus.NOT_FOUND) {

            return "redirect:/newvehicle/"+vehicle.getUserId();
        }
        return "redirect:/newvehicle/"+vehicle.getUserId();

    }

//    @DeleteMapping("/deleteUser/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
//
//        Optional<User> optionalUser = userRepository.findByUserId(userId);
//        Constants constants = new Constants();
//        ExtendedResponse extendedResponse = new ExtendedResponse();
//
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            contactRepository.deleteByUserId(userId);
//            vehicleRepository.deleteByUserId(userId);
//            userRepository.delete(user);
//
//            extendedResponse.setStatus_code(constants.status_delete);
//            extendedResponse.setStatus_message(constants.status_message_delete);
//            extendedResponse.setUserdata(user);
//
//            return new ResponseEntity<>(extendedResponse, HttpStatus.FOUND);
//        } else {
//            throw new EntityNotFoundException();
//        }
//    }
//    @DeleteMapping("/deletevehicle/{id}")
//    public ResponseEntity<?> deletevehicle(@PathVariable String id) {
//        int vId = Integer.parseInt(id);
//        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vId);
//        Constants constants = new Constants();
//        ExtendedResponse extendedResponse = new ExtendedResponse();
//
//        if (optionalVehicle.isPresent()) {
//            Vehicle vehicle = optionalVehicle.get();
//            vehicleRepository.deleteByUserId(vId);
//            extendedResponse.setStatus_code(constants.status_delete);
//            extendedResponse.setStatus_message(constants.status_message_delete);
//            extendedResponse.setUserdata(vehicle);
//
//            return new ResponseEntity<>(extendedResponse, HttpStatus.FOUND);
//        } else {
//            throw new EntityNotFoundException();
//        }
//    }
//
//    @DeleteMapping("/deletecontact/{id}")
//    public ResponseEntity<?> deletecontact(@PathVariable String id) {
//        int c_id = Integer.parseInt(id);
//        Optional<Contact> optionalContact = contactRepository.findById(c_id);
//        Constants constants = new Constants();
//        ExtendedResponse extendedResponse = new ExtendedResponse();
//
//        if (optionalContact.isPresent()) {
//            Contact contact = optionalContact.get();
//            vehicleRepository.deleteByUserId(c_id);
//            extendedResponse.setStatus_code(constants.status_delete);
//            extendedResponse.setStatus_message(constants.status_message_delete);
//            extendedResponse.setUserdata(contact);
//
//            return new ResponseEntity<>(extendedResponse, HttpStatus.FOUND);
//        } else {
//            throw new EntityNotFoundException();
//        }
//    }





}



