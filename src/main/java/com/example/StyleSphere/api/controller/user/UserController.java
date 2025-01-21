package com.example.StyleSphere.api.controller.user;


import com.example.StyleSphere.api.models.DataChange;
import com.example.StyleSphere.models.Address;
import com.example.StyleSphere.models.LocalUser;
import com.example.StyleSphere.models.repository.AddressRepository;
import com.example.StyleSphere.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private AddressRepository addressRepository;
    private SimpMessagingTemplate simpMessagingTemplate;
    private UserService userService;

    public UserController(AddressRepository addressRepository, SimpMessagingTemplate simpMessagingTemplate, UserService userService) {
        this.addressRepository = addressRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userService = userService;
    }

    @GetMapping("/{userId}/address")
    public ResponseEntity<List<Address>> getAddress(@AuthenticationPrincipal LocalUser user, @PathVariable Long userId){
        if(!userService.userHasPermissionToUser(user, userId)){
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return  ResponseEntity.ok(addressRepository.findByUser_Id(userId));
    }

    @PutMapping("/{userId}/address")
    public ResponseEntity<Address> putAddress(@AuthenticationPrincipal LocalUser user,@PathVariable Long userId,@RequestBody Address address){
        if(!userService.userHasPermissionToUser(user, userId)){
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
         address.setId(null);
        LocalUser refUser = new LocalUser();
        refUser.setId(userId);
        address.setUser(refUser);
        Address savedAddress = addressRepository.save(address);
        simpMessagingTemplate.convertAndSend("/topic/user/" + userId +"/address", new DataChange<>(DataChange.ChangeType.INSERT, address));
        return  ResponseEntity.ok(savedAddress);
    }

    @PatchMapping("/{userId}/address/{addressId}")
    public ResponseEntity<Address> patchAddress(
            @AuthenticationPrincipal LocalUser user, @PathVariable Long userId,
            @PathVariable Long addressId, @RequestBody Address address) {
        if (!userService.userHasPermissionToUser(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (address.getId() == addressId) {
            Optional<Address> opOriginalAddress = addressRepository.findById(addressId);
            if (opOriginalAddress.isPresent()) {
                LocalUser originalUser = opOriginalAddress.get().getUser();
                if (originalUser.getId() == userId) {
                    address.setUser(originalUser);
                    Address savedAddress = addressRepository.save(address);
                    simpMessagingTemplate.convertAndSend("/topic/user/" + userId +"/address", new DataChange<>(DataChange.ChangeType.UPDATE, address));
                    return  ResponseEntity.ok(savedAddress);
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }



}
