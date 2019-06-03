package oauth2server.model;

import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;

public class Welcome implements Serializable {

    private static final String GREETINGS_FORMAT = new String("Welcome %s!");

    public String greetings;
    public org.springframework.security.core.userdetails.User activeUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public Welcome() {
    }

    public Welcome(String who) {
        this.greetings = String.format(GREETINGS_FORMAT, who);
    }
}
