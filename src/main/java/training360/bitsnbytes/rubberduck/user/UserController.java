package training360.bitsnbytes.rubberduck.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    public UserController() {
    }

    @GetMapping("/api/user")
    public UserData getUser(Authentication authentication){
        if (authentication == null){
            return new UserData("Anonymous", UserRole.ROLE_NOT_AUTHORIZED);
        }
        return new UserData(authentication.getName(), UserRole.valueOf(authentication.getAuthorities().toArray()[0].toString()));
    }

    public List<User> listAllUser(){return usersService.listAllUser();}
}
