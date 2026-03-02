package com.congreso.domain.repository;

import com.congreso.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback
    void saveAndFindById() {
        UserEntity user = new UserEntity();
        user.setFirstname("Juan");
        user.setLastname("Perez");
        user.setEmail("juan@example.com");
        user.setPhoneNumber("12345678");
        user.setPassword("pass");
        user.setDpi("1000000000000");

        UserEntity saved = userRepository.save(user);
        assertThat(saved.getId()).isNotNull();

        UserEntity found = userRepository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("juan@example.com");
    }
}
