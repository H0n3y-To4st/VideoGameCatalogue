package fish.payara.hello.entities;

import fish.payara.hello.restapi.IGDBService;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.List;

public class Games implements Serializable {

    @Inject
    IGDBService igdbService;

    private Integer id;
    private String name;
    private List<Genres> genres;
//    private long releaseDate;
    private String coverUrl;
    private Double rating;

    public Games(Integer id) {
        this.id = id;
    }

    public Games() {
    }

    public Games(Integer id, String name, String coverUrl, Double rating) {
        this.id = id;
        this.name = name;
        this.coverUrl = coverUrl;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return name;
    }

    public String getName() {
        int length = 22;
        if (name.length() <= length) {
            return name;
        }
        return name.substring(0, length) + "...";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenres() {
        int length = 20;
        if (genres == null || genres.isEmpty()) {
            return "No genres available";
        }
        StringBuilder genreNames = new StringBuilder();
        for (Genres genre : genres) {
            if (genreNames.length() > 0) {
                genreNames.append(", ");
            }
            genreNames.append(genre.getName());
            if (genreNames.length() > length) {
                return genreNames.substring(0, length) + "...";
            }
        }
        return genreNames.toString();
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }
//
//    public long getReleaseDate() {
//        return releaseDate;
//    }
//
//    public void setReleaseDate(long releaseDate) {
//        this.releaseDate = releaseDate;
//    }

    public String getRating() {
        String rating;
        if (this.rating == null) {
            rating = "No rating available";
        } else {
            rating = String.valueOf(this.rating.intValue());
        }
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Games)) {
            return false;
        }
        Games other = (Games) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

//    @Override
//    public String toString() {
//        return "Games[ id=" + id + ", name=" + name + ", genres=" + genres + ", firstReleaseDate=" + releaseDate + ", rating=" + rating + " ]";
//    }
}