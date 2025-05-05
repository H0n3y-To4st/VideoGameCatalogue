package fish.payara.hello.jsf;


import fish.payara.hello.entities.Games;
import fish.payara.hello.restapi.IGDBService;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import software.xdev.chartjs.model.charts.PieChart;
import software.xdev.chartjs.model.data.PieData;
import software.xdev.chartjs.model.dataset.PieDataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named(value = "pieChartBean")
@RequestScoped
public class PieChartBean {

    private String genreChartModel;

    @Inject
    private IGDBService igdbService;

    @Inject
    private UserGamesBean userGamesBean;

    //want to ensure piechart is created
    @PostConstruct
    public void init() {
        generateGenreChartModel();
    }

    public void generateGenreChartModel() {
        // Placeholder for games list
        List<Games> games = userGamesBean.getGames();
        Map<String, Integer> genreCount = new HashMap<>();

        for (Games game : games) {
            Games fullGame = igdbService.getGameByID(game.getId());
            for (Map<String, String> genre : fullGame.getGenres()) {
                String genreName = genre.get("name");
                genreCount.put(genreName, genreCount.getOrDefault(genreName, 0) + 1);
            }
        }

        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> colours = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : genreCount.entrySet()) {
            labels.add(entry.getKey());
            values.add(entry.getValue());

            String colour = generateRandomColor();
            while (colours.contains(colour)) {
                colour = generateRandomColor();
            }
            colours.add(colour);
        }

        PieDataset dataSet = new PieDataset();
        dataSet.setData(values);
        dataSet.setBackgroundColor(colours);

        PieData data = new PieData();
        data.addDataset(dataSet);
        data.setLabels(labels.toArray(new String[0]));

        PieChart pieChart = new PieChart();
        pieChart.setData(data);

        genreChartModel = pieChart.toJson();
    }

    private String generateRandomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return String.format("rgb(%d, %d, %d)", r, g, b);
    }

    public String getGenreChartModel() {
        return genreChartModel;
    }

    public String setGenreChartModel() {
        return genreChartModel;
    }
}
