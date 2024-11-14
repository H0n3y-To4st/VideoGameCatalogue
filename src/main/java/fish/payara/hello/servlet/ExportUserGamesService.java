package fish.payara.hello.servlet;

import fish.payara.hello.entities.Games;
import fish.payara.hello.entities.UserAccount;
import fish.payara.hello.service.UserGamesService;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

//@WebServlet("/exportUserGames")
public class ExportUserGamesService extends HttpServlet {

    @Inject
    private UserGamesService userGamesService;

    public void exportUserGames() throws IOException {
        // Fetch the logged-in user from the session
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UserAccount user = (UserAccount) facesContext.getExternalContext().getSessionMap().get("user");

        // Fetch the user's saved games
        List<Games> userGames = userGamesService.listAllGamesInDashboard(user.getId());

        // Create Excel file
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("User Saved Games");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Game ID");
        headerRow.createCell(1).setCellValue("Game Name");
        headerRow.createCell(2).setCellValue("Genres");
        headerRow.createCell(3).setCellValue("Rating");

        // Populate the sheet with user game data
        int rowCount = 1;
        for (Games game : userGames) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(game.getId());
            row.createCell(1).setCellValue(game.getName());

            // Combine genres as a comma-separated string
            String genres = game.getGenres() != null ? String.join(", ", game.getGenres().toString()) : "N/A";
            row.createCell(2).setCellValue(genres);

            row.createCell(3).setCellValue(game.getRating() != null ? game.getRating() : "N/A");
        }

        // Set the content type and headers for the response
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=user-games.xlsx");

        // Write the workbook to the output stream
        try (OutputStream out = response.getOutputStream()) {
            workbook.write(out);
        } finally {
            workbook.close();
        }
    }
}
