package com.smartlott.backend.persistence.domain.backend;

import javax.persistence.*;

/**
 * Created by Mrs Hoang on 04/01/2017.
 */
@Entity
@Table(name = "network",
        uniqueConstraints = @UniqueConstraint(columnNames = {"of_user","ancestor","level"})
    )
public class Network {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "of_user")
    private User ofUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ancestor")
    private User ancestor;

    private int level = 0;

    public Network() {
    }

    public Network(User ofUser, User ancestor, int level) {
        this.ofUser = ofUser;
        this.ancestor = ancestor;
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOfUser() {
        return ofUser;
    }

    public void setOfUser(User ofUser) {
        this.ofUser = ofUser;
    }

    public User getAncestor() {
        return ancestor;
    }

    public void setAncestor(User ancestor) {
        this.ancestor = ancestor;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Network{" +
                "id=" + id +
                ", ofUser=" + ofUser +
                ", ancestor=" + ancestor +
                ", level=" + level +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Network network = (Network) o;

        return id == network.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
