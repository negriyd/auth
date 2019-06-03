package oauth2server.component;

import oauth2server.dao.PrivilegeRepository;
import oauth2server.dao.RoleRepository;
import oauth2server.dao.UserRepository;
import oauth2server.model.Privilege;
import oauth2server.model.Role;
import oauth2server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege  = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setUserName("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setEmail("test@test.com");
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        createUserIfNotFound(user);

        Role userRole = roleRepository.findByName("ROLE_USER");
        user = new User();
        user.setUserName("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setEmail("test@test.com");
        user.setRoles(Arrays.asList(userRole));
        user.setEnabled(true);
        createUserIfNotFound(user);

        alreadySetup = true;
    }

    @Transactional
    protected User createUserIfNotFound(User newUser) {

        User user = userRepository.findByUserName(newUser.getUserName());
        if (user == null) {
            userRepository.save(newUser);
        }
        return user;
    }

    @Transactional
    protected Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    protected Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
