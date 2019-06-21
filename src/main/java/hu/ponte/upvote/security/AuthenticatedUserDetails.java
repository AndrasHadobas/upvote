package hu.ponte.upvote.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class AuthenticatedUserDetails {

    private String userName;

    private String role;

    public AuthenticatedUserDetails() {
    }

    public AuthenticatedUserDetails(UserDetails user) {
        this.userName = user.getUsername();
        this.role = findRole(user);
    }

    private String findRole(UserDetails user) {
        String userRole = null;

        List<GrantedAuthority> roles = user.getAuthorities().stream()
                .filter(authority -> authority.getAuthority().startsWith("ROLE_"))
                .collect(Collectors.toList());
        if (!roles.isEmpty()) {
            userRole = roles.get(0).getAuthority();
        }

        return userRole;
    }

    public String getUserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }
}
