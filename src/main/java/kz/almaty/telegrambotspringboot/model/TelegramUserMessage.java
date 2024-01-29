package kz.almaty.telegrambotspringboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "messages")
public class TelegramUserMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long telegramUserId;
    @CreationTimestamp
    private LocalDateTime messageTime;
    @Column(columnDefinition = "TEXT")
    private String telegramUserMessageRequest;
    @Column(columnDefinition = "TEXT")
    private String telegramUserMessageResponse;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "message_id")
    private AppUser user;
}

