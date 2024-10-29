package mmo.project.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "mmo_config")
@NamedQueries({
        @NamedQuery(name = "MmoConfig.findAll", query = "SELECT m FROM MmoConfig m ORDER BY m.id asc")
})
public class MmoConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long id;

    @Column(name = "site_url", length = Integer.MAX_VALUE)
    private String siteUrl;

    @Column(name = "term", length = Integer.MAX_VALUE)
    private String term;

    @Column(name = "actions_main", length = Integer.MAX_VALUE)
    private String actionsMain;

    @Column(name = "actions_ads", length = Integer.MAX_VALUE)
    private String actionsAds;

    @Column(name = "interval_s")
    private Integer intervalS;

    @Column(name = "count_t")
    private Integer countT;

    @ColumnDefault("1")
    @Column(name = "status")
    private Integer status;

    @ColumnDefault("1")
    @Column(name = "per_mobile")
    private Integer perMobile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getActionsMain() {
        return actionsMain;
    }

    public void setActionsMain(String actionsMain) {
        this.actionsMain = actionsMain;
    }

    public String getActionsAds() {
        return actionsAds;
    }

    public void setActionsAds(String actionsAds) {
        this.actionsAds = actionsAds;
    }

    public Integer getIntervalS() {
        return intervalS;
    }

    public void setIntervalS(Integer intervalS) {
        this.intervalS = intervalS;
    }

    public Integer getCountT() {
        return countT;
    }

    public void setCountT(Integer countT) {
        this.countT = countT;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPerMobile() {
        return perMobile;
    }

    public void setPerMobile(Integer perMobile) {
        this.perMobile = perMobile;
    }

    @Override
    public String toString() {
        return "MmoConfig{" +
                "id=" + id +
                ", siteUrl='" + siteUrl + '\'' +
                ", term='" + term + '\'' +
                ", actionsMain='" + actionsMain + '\'' +
                ", actionsAds='" + actionsAds + '\'' +
                ", intervalS=" + intervalS +
                ", countT=" + countT +
                ", status=" + status +
                ", per_mobile=" + perMobile +
                '}';
    }
}