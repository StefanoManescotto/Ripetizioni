<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP - Hello World</title>
        <script type="text/javascript" src="https://code.jquery.com/jquery-1.7.1.min.js"></script>
    </head>
    <body>
        <h1><%= "Hello World!" %></h1>
        <br/>
        <a href="ripetizioni">Hello Servlet</a>
        <a href="./AggiungiDocenteForm.jsp">Aggiungi Docente</a>
        <form action="aggiungiDocente" method="get">
            <input type="text" name="nomeAttributo">
            <p><input type="submit" name="submit" value="OK"></p>
        </form>
        <div id="dati">test</div>

        <script type="text/javascript">
            $(document).ready(function () {
               $.getJSON("ripetizioni", {}, function(data){

                  // var info = JSON.stringify(data);
                  //  let json = [{
                  //      "id" : "1",
                  //      "msg"   : "hi",
                  //      "tid" : "2013-05-05 23:35",
                  //      "fromWho": "hello1@email.se"
                  //  },
                  //      {
                  //          "id" : "2",
                  //          "msg"   : "there",
                  //          "tid" : "2013-05-05 23:45",
                  //          "fromWho": "hello2@email.se"
                  //      }];

                   for(let i = 0; i < data.length; i++) {
                       let obj = data[i];

                       console.log(obj);
                   }


                  $("#dati").html(JSON.stringify(data));
               });
            });
        </script>
    </body>
</html>