package fish.payara.hello.jsf;


import fish.payara.hello.restapi.IGDBService;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import software.xdev.chartjs.model.charts.PieChart;
import software.xdev.chartjs.model.data.PieData;
import software.xdev.chartjs.model.dataset.PieDataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

@Named(value = "pieChartBean")
@RequestScoped
public class PieChartBean {

    private String genreChartModel;

    @Inject
    private IGDBService igdbService;

    @Inject
    private UserGamesBean userGamesBean;

    @PostConstruct
    public void init() {
        generateGenreChartModel();
    }

    public void generateGenreChartModel() {
        Map<String, Integer> genreCount = userGamesBean.getGames().stream()
                .map(game -> igdbService.getGameByID(game.getId()))
                .filter(fullGame -> fullGame.getGenres() != null)
                .flatMap(fullGame -> fullGame.getGenres().stream())  // flatten all genres because of its datatype
                .map(genreMap -> genreMap.get("name"))
                .filter(Objects::nonNull)                            //  prevent npe
                .collect(Collectors.toMap(
                        Function.identity(),
                        g -> 1,
                        Integer::sum
                ));

        List<String> labels = new ArrayList<>(genreCount.keySet());
        List<Number> values = new ArrayList<>(genreCount.values());
        List<String> colours = new ArrayList<>();

        Set<String> usedColors = new HashSet<>();
        for (int i = 0; i < labels.size(); i++) {
            String color;
            do {
                color = generateRandomColor();
            } while (!usedColors.add(color));
            colours.add(color);
        }

        PieDataset dataSet = new PieDataset();
        dataSet.setData(values);
        dataSet.setBackgroundColor(colours);

        PieData data = new PieData();
        data.addDataset(dataSet);
        data.setLabels(labels.toArray(new String[0]));

        PieChart pieChart = new PieChart();
        pieChart.setData(data);

        this.genreChartModel = pieChart.toJson();
    }

    private String generateRandomColor() {
        return String.format("rgb(%d, %d, %d)",
                (int) (Math.random() * 256),
                (int) (Math.random() * 256),
                (int) (Math.random() * 256));
    }

    public String getGenreChartModel() {
        return genreChartModel;
    }
}
