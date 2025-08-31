package com.yogesh.jobconnect.repositories;

import com.yogesh.jobconnect.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole("EMPLOYER");

        userRepository.save(user);

        User found = userRepository.findByUsername("testuser");
        assertThat(found).isNotNull();
        assertThat(found.getRole()).isEqualTo("EMPLOYER");
    }
}
