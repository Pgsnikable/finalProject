package by.training.project.entity;

import by.training.project.entity.Entity;
import by.training.project.entity.User;
import by.training.project.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserWithoutPassword implements Entity {
    private Long userId;
    private String login;
    private UserRole role;
}
