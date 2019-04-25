package training360.bitsnbytes.rubberduck.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class UsersDaoTest {
    @Autowired
    UsersDao usersDao;

    TestingAuthenticationToken admin = new TestingAuthenticationToken("adminka", "alma", "ROLE_ADMIN");
    TestingAuthenticationToken user = new TestingAuthenticationToken("userke", "user1", "ROLE_USER");


    @Test
    public void testCreateUser() {
        usersDao.createUser(new User(5, "User Elek", "userE",
                "Jelszo12345", 1, UserRole.ROLE_USER));

        assertEquals(usersDao.listAllUser().size(), 5);
        assertEquals(usersDao.listAllUser().get(4).getName(), "User Elek");
        assertEquals(usersDao.listAllUser().get(4).getEnabled(), 1);
    }

    @Test
    public void testFindUserByUserName() {

        Optional<User> foundUser = usersDao.findUserByUserName("user");

        assertEquals(foundUser.isPresent(), true);
        assertEquals(foundUser.get().getName(), "user");
        assertEquals(foundUser.get().getEnabled(), 1);

    }

    @Test
    public void testFindUserByUserId() {
        Optional<User> foundUser = usersDao.findUserByUserId(4);
        assertEquals(foundUser.get().getName(), "User Ferenc");
        assertEquals(foundUser.get().getUserName(), "user1");
        assertEquals(foundUser.get().getEnabled(), 1);

    }

    @Test
    public void testListAllUsers() {
        List<User> products = usersDao.listAllUser();
        assertEquals(products.size(), 4);
        assertEquals((products.get(0).getName()), "user");
        assertEquals((products.get(1).getName()), "admin");
    }

    @Test
    public void testLogicalDeleteUser() {

        usersDao.logicalDeleteUser("duck");
        assertEquals(usersDao.findUserByUserId(4).get().getName(), "User Ferenc");
        assertEquals(usersDao.findUserByUserId(4).get().getUserName(), "user1");


    }

    @Test
    public void testModifyUser() {
        Optional<User> foundUser = usersDao.findUserByUserId(4);
        User userForModify = new User(foundUser.get().getId(), foundUser.get().getName(), foundUser.get().getUserName(),
                foundUser.get().getPassword(), foundUser.get().getEnabled(), foundUser.get().getUserRole());
        usersDao.modifyUser(foundUser.get().getUserName(), userForModify);

        assertEquals(usersDao.findUserByUserName("user1").get().getName(), userForModify.getName());

    }
}
