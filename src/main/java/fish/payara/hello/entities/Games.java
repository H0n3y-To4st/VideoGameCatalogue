package fish.payara.hello.entities;

import java.io.Serializable;
import java.util.List;

public class Games implements Serializable {

    private Integer id;
    private String name;
    private List<Genres> genres;
//    private long releaseDate;
    private Double aggregated_rating;

    public Games(Integer id) {
        this.id = id;
    }

    public Games() {
    }

    public Games(Integer id, String name, Double aggregated_rating) {
        this.id = id;
        this.name = name;
        this.aggregated_rating = aggregated_rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        int length = 15;
        if (name.length() <= length) {
            return name;
        }
        return name.substring(0, length) + "...";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenres() {
        if (genres == null || genres.isEmpty()) {
            return "No genres available";
        }
        StringBuilder genreNames = new StringBuilder();
        for (Genres genre : genres) {
            if (genreNames.length() > 0) {
                genreNames.append(", ");
            }
            genreNames.append(genre.getName());
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

    public String getAggregated_rating() {
        String rating;
        if (aggregated_rating == null) {
            rating = "No rating available";
        } else {
            rating = String.valueOf(aggregated_rating.intValue());
        }
        return rating;
    }

    public void setAggregated_rating(Double aggregated_rating) {
        this.aggregated_rating = aggregated_rating;
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

//    @Override
//    public String toString() {
//        return "Games[ id=" + id + ", name=" + name + ", genres=" + genres + ", firstReleaseDate=" + releaseDate + ", rating=" + rating + " ]";
//    }
}