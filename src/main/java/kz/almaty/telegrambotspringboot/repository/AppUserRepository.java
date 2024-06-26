package kz.almaty.telegrambotspringboot.repository;

import kz.almaty.telegrambotspringboot.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserByTelegramUserId(Long id);
    Boolean existsAppUsersByTelegramUserId(Long id);
}
