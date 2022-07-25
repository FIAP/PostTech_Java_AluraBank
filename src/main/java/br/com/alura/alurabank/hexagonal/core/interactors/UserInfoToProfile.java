package br.com.alura.alurabank.hexagonal.core.interactors;

import br.com.alura.alurabank.hexagonal.core.entities.Profile;
import org.springframework.stereotype.Component;

@Component
public class UserInfoToProfile {
    public Profile convert(ReadProfile.UserInfo userInfo) {
        return new Profile(userInfo.getName(), userInfo.getEmail());
    }
}
