package by.training.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements Entity {
    private Long userId;
    private String login;
    private String password;
    private UserRole role;
}
