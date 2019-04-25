package training360.bitsnbytes.rubberduck.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import training360.bitsnbytes.rubberduck.ResponseStatus;
import training360.bitsnbytes.rubberduck.Validator;


import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
public class UsersController {

    private Validator validator = new Validator();
    private UserData userData;
    private UserController userController = new UserController();

    @Autowired
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/api/users")
    public List<User> listAllUser() {
        return usersService.listAllUser();
    }

    @PostMapping("/api/users")
    public ResponseStatus createUser(@RequestBody User user) {
        if (usersService.findUserByUserName(user.getUserName()).isPresent()) {
            return new ResponseStatus(false, "A felhasználónév már foglalt!");
        }
        ResponseStatus rs = validator.passwordValidation(user.getPassword());
        if (rs.isOk()) {
            usersService.createUser(user);
        }
        return rs;
    }

    @DeleteMapping("api/users/{userName}")
    public ResponseStatus logicalDeleteUser(@PathVariable String userName, Authentication authentication) {

        if (userController.getUser(authentication).getUsername().equals(userName)) {
            return new ResponseStatus(false, "Saját magadat nem törölheted!");
        }
//        if (usersService.listAllActiveUser().size() == 1 ) {
//            return new ResponseStatus(true, "Ha törölni szeretnéd ezt a felhasználót, adj hozzá egy másik adminisztrátort a fiókhoz!");
        else {
            usersService.logicalDeleteUser(userName);
            return new ResponseStatus(true, "Felhasználó törölve.");
        }
    }

    @PostMapping("api/users/{userName}")
    public ResponseStatus modifyUser(@PathVariable String userName, @RequestBody User user, Authentication authentication) {
        if (user.getName() == null || user.getName().trim().equals("")) {
            return new ResponseStatus(false, "A név mező nem lehet üres!");
        }
        if (user.getPassword() == null || user.getPassword().trim().equals("")) {
            return new ResponseStatus(false, "A jelszó mező nem lehet üres!");
        }
//        if (!isNameUnique(user.getName(), user.getId())) {
//            return new ResponseStatus(false, "A felhasználónév már foglalt!");
//        }
        if (isHash(user.getPassword())) {
            usersService.modifyUser(userName, user);
            return new ResponseStatus(true, "A felhasználó adatait sikeresen módosítottad!");
        }
        if (user.getPassword().length() > 15 || user.getPassword().length() < 8) {
            return new ResponseStatus(false, "A jelszónak 8-15 karakter hosszúságúnak kell lennie!");
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!user.getPassword().matches(upperCaseChars)) {
            return new ResponseStatus(false, "A jelszónak tartalmaznia kell legalább egy nagybetűs karaktert!");
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!user.getPassword().matches(lowerCaseChars)) {
            return new ResponseStatus(false, "A jelszónak tartalmaznia kell legalább egy kisbetűs karaktert!");
        }
        String numbers = "(.*[0-9].*)";
        if (!user.getPassword().matches(numbers)) {
            return new ResponseStatus(false, "A jelszónak tartalmaznia kell legalább egy számot!");
        }
        usersService.modifyUser(userName, user);
        return new ResponseStatus(true, "A felhasználó adatait sikeresen módosítottad!");
    }

    @PutMapping("api/userprofile")
    public ResponseStatus modifyUserProfileData(Authentication authentication, @RequestBody User user) {
        String currentUser = userController.getUser(authentication).getUsername();
//        System.out.println("current user: " + currentUser);
        ResponseStatus emptyRs = validator.isEmptyPasswordAndName(user.getPassword(), user.getName());
        ResponseStatus psRs = validator.passwordValidation(user.getPassword());
        if (!emptyRs.isOk()) {
            return emptyRs;
        }
        if (!psRs.isOk()) {
            return psRs;
        }
        usersService.modifyUser(currentUser, user);
        return new ResponseStatus(true, "Sikeres adatmódosítás!");
    }

    private boolean isNameUnique(String name, long id) {
        return usersService.findUserByName(name).size() == 0 || usersService.findUserByName(name).get(0).getId() == id;

    }

    private boolean isHash(String password){
        Pattern BCRYPT_PATTERN = Pattern.compile("^\\$2[aby]?\\$[\\d]+\\$[./A-Za-z0-9]{53}$");
        return  BCRYPT_PATTERN.matcher(password).matches();
    }
}
