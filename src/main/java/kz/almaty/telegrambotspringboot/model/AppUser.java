package kz.almaty.telegrambotspringboot.model;


import jakarta.persistence.*;
import kz.almaty.telegrambotspringboot.enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long telegramUserId;
    @CreationTimestamp
    private LocalDateTime registrationDate;
    private String firstname;
    private String lastName;
    private String username;
    private Boolean isActive;
    @Enumerated(EnumType.STRING)
    private UserState state;
}