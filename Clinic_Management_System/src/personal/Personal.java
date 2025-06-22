package personal;

import java.time.LocalDateTime;

public class Personal {

    private Integer id;
    private LocalDateTime created_at;

    public Personal(Integer id, LocalDateTime created_at) {
        this.id = id;
        this.created_at = created_at;
    }

    public Personal() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

}
