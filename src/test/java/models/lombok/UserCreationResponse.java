package models.lombok;

import lombok.Data;

@Data
public class UserCreationResponse {
    public String name, job, id, createdAt;
}
