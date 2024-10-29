package mmo.project.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "mmo_user_agent")
@NamedQuery(name = "MmoUserAgent.findAll", query = "SELECT m FROM MmoUserAgent m  ORDER BY m.id asc")
public class MmoUserAgent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long id;

    @Column(name = "user_agent", length = Integer.MAX_VALUE)
    private String userAgent;

    @Column(name = "type", length = Integer.MAX_VALUE)
    private String type;

    @ColumnDefault("1")
    @Column(name = "status")
    private Short status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MmoUserAgent{" +
                "id=" + id +
                ", userAgent='" + userAgent + '\'' +
                ", type='" + type + '\'' +
                ", status=" + status +
                '}';
    }
}