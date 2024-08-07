/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fish.payara.hello.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author IsmahHussain
 */
@Entity
@Table(name = "genres")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = Genres.QUERY_ALL, query = "SELECT g FROM Genres g"),
        @NamedQuery(name = Genres.QUERY_BY_ID, query = "SELECT g FROM Genres g WHERE g.id = :id"),
        @NamedQuery(name = "Genres.findByName", query = "SELECT g FROM Genres g WHERE g.name = :name"),
        @NamedQuery(name = Genres.QUERY_BY_GAME_ID, query = "SELECT g FROM Genres g INNER JOIN GamesGenresBridge gg ON g.id = gg.genre.id WHERE gg.game.id = :GameId")})

public class Genres implements Serializable {

    public static final String QUERY_ALL = "Genres.findAll";
    public static final String QUERY_BY_ID = "Genres.findById";
    public static final String QUERY_BY_GAME_ID = "Genres.findByGameId";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "genresCollection")
    private Collection<Games> gamesCollection;

    public Genres() {
    }

    public Genres(Integer id) {
        this.id = id;
    }

    public Genres(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Games> getGamesCollection() {
        return gamesCollection;
    }

    public void setGamesCollection(Collection<Games> gamesCollection) {
        this.gamesCollection = gamesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Genres)) {
            return false;
        }
        Genres other = (Genres) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fish.payara.hello.entities.Genres[ id=" + id + " ]";
    }

}
