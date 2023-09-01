package fr.diginamic.gestit_back.authentication;

import fr.diginamic.gestit_back.entites.Utilisateur;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data

@NoArgsConstructor
public class LoginUser implements UserDetails {
    private Utilisateur utilisateur;
    private List<SimpleGrantedAuthority> authorities;

    public LoginUser(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.authorities == null)
            authorities = utilisateur.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        System.out.println(authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return utilisateur.getMotDePasse();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
