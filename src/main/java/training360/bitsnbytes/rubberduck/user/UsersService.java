package training360.bitsnbytes.rubberduck.user;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import training360.bitsnbytes.rubberduck.ResponseStatus;
import training360.bitsnbytes.rubberduck.Validator;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private UsersDao usersDao;
    private Validator validator = new Validator();

    public UsersService(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public ResponseStatus createUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setUserRole(UserRole.ROLE_USER);
        usersDao.createUser(user);
        return new ResponseStatus(true, "Sikeres regisztráció.");
    }

    public Optional<User> findUserByUserName(String userName) {
        return usersDao.findUserByUserName(userName);
    }

    public List<User> findUserByName(String name) {
        return usersDao.findUserByName(name);
    }

    public List<User> listAllUser() {
        return usersDao.listAllUser();
    }

    public List<User> listAllActiveUser() {
        List<User> users = usersDao.listAllActiveUser();
        return users;
    }

    public void logicalDeleteUser(String userName) {
        usersDao.logicalDeleteUser(userName);
    }

    public void modifyUser(String userName, User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        usersDao.modifyUser(userName, user);
    }
}
