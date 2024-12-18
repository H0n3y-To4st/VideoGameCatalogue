<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
          xmlns:c="jakarta.tags.core"
          version="2.1">
    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>
    <html>
    <head>
        <title>JSP Index Page</title>
        <link href="/resources/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <!-- TOP GAME DETAILS -->
        <h1>Popular Games</h1>
        <div class="card-container">
            <jsp:useBean id="gameBean" class="fish.payara.hello.jsf.GameBean" scope="request"/>
            <c:forEach var="game" items="${gameBean.games}">
                <div class="card">
                    <h2>${game.name}</h2>
                    <img src="${game.cover.url}" alt="${game.name} cover" class="game-cover"/>
                    <p>Genres:
                        <c:forEach var="genre" items="${game.genres}">
                            ${genre.name}
                            <c:if test="${game.genres.indexOf(genre) != game.genres.size() - 1}">, </c:if>
                        </c:forEach>
                    </p>
                    <p>Rating: ${game.rating}</p>
                </div>
            </c:forEach>
        </div>
    </body>
    </html>
</jsp:root>