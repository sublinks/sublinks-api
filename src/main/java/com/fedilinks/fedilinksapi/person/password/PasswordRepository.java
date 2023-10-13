package com.fedilinks.fedilinksapi.person.password;

import com.fedilinks.fedilinksapi.instance.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Instance, Long> {

}
