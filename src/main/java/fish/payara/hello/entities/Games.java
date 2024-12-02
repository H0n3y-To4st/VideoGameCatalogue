package fish.payara.hello.entities;

import fish.payara.hello.jsf.UserGameStatesBean;
import fish.payara.hello.restapi.IGDBService;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Games implements Serializable {

    @Inject
    IGDBService igdbService;

    private Integer id;
    private String name;
    private List<Map<String, String>> genres;
//    private long releaseDate;
    private Map<String, String> cover;
    private List<Map<String, String>> screenshots;
    private Double rating;
    public String summary;

    Logger logger = Logger.getLogger(Games.class.getName());

    public Games() {
    }

    public Games(Integer id, String name, Double rating) {
        this.id = id;
        this.name = name;
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

    public void setFullName(String name) {
        this.name = name;
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

    public List<Map<String, String>> getGenres() {
        return genres;
    }

    public void setGenres(List<Map<String, String>> genres) {
        this.genres = genres;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public Map<String, String> getCover() {
        String url = cover.get("url");
        if (url.contains("thumb")) {
            url = url.replace("thumb", "cover_big");
            cover.put("url", url);
        }
        logger.info("Cover URL: " + cover.get("url"));
        return cover;
    }

    public void setCover(Map<String, String> cover) {
        this.cover = cover;
    }

    public Map<String, String> getThumbCover() {
        String url = cover.get("url");
        if (url.contains("cover_big")) {
            url = url.replace("cover_big", "thumb");
            cover.put("url", url);
        }
        logger.info("Cover URL: " + cover.get("url"));
        return cover;
    }

    public void setThumbCover(Map<String, String> cover) {
        this.cover = cover;
    }

    public Map<String, String> getBigScreenshot(Map<String, String> currentScreenshot) {
        if (currentScreenshot == null || !currentScreenshot.containsKey("url")) {
            return currentScreenshot;
        }
        Map<String, String> updatedScreenshot = new HashMap<>(currentScreenshot);
        String url = "https:" + updatedScreenshot.get("url").replace("thumb", "cover_big_2x");
        updatedScreenshot.put("url", url);
        return updatedScreenshot;
    }

    public List<Map<String, String>> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Map<String, String>> screenshots) {
        this.screenshots = screenshots;
    }
}