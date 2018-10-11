<%
    import com.google.appengine.api.datastore.DatastoreFailureException;
    import com.google.appengine.api.datastore.DatastoreService;
    import com.google.appengine.api.datastore.DatastoreServiceFactory;
    import com.google.appengine.api.datastore.KeyFactory;
    import com.google.appengine.api.datastore.Entity;
    import com.google.appengine.api.datastore.FetchOptions;
    import com.google.appengine.api.datastore.PreparedQuery;
    import com.google.appengine.api.datastore.Query;
    import com.google.appengine.api.datastore.Query.FilterOperator;
    import com.google.appengine.api.datastore.Query.FilterPredicate;

    import org.jsoup.Jsoup;
    import org.jsoup.safety.Whitelist;
    %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:helpPage jsIncludes="${jsIncludes}">

    <head>
        <title>Forum</title>

        <style>
            body {
                color: black;
                background-color: rgb(243,242,235);
                font-family: verdana, helvetica, arial, sans-serif;
                font-size: 73%;
                margin: 0;
                padding: 0;
            }

            html > body {
                font-size: 9pt;
            }



            .headerTitle a {
                color: black;
                background-color: transparent;
                text-decoration: none;
                font-size: 110%;
                font-weight: bold;
                font-style: italic;
            }

            .headerTitle > a {
                font-size: 138%;
            }

            .headerTitle span {
                color: white;
                background-color: transparent;
                font-weight: normal;
            }


            .subHeader a {
                color: white;
                background-color: black;
                text-decoration: none;
                font-weight: bold;
                margin: 0;
                padding: 0 1ex;
            }

            .subHeader a:hover {
                color: black;
                background-color: white;
            }

            #main-copy {
                color: black;
                background-color: white;
                text-align: justify;
                line-height: 1.5em;
                margin: 0 0 0 12.5em;
                padding: 0.5ex 15em 1em 1em;
                border-left: 1px solid rgb(216,210,195);
            }

            #main-copy h1 {
                color: rgb(166,140,83);
                background-color: transparent;
                font-family: arial, verdana, helvetica, sans-serif;
                font-size: 175%;
                font-weight: bold;
                font-style: italic;
                text-align: left;
                margin: 1em 0 0 0;
                padding: 1em 0 0 0;
                border-top: 1px solid rgb(216,210,195);
            }

            #main-copy a {
                color: rgb(168,140,83);
                background-color: transparent;
                text-decoration: underline;
            }

            #main-copy a:hover {
                text-decoration: none;
            }

            p {
                margin: 1em 0 1.5em 0;
                padding: 0;
            }

            dt {
                font-weight: bold;
                margin: 0;
                padding: 0 0 0.5ex 0;
            }

            dd {
                margin: 0 0 1.5em 1.5em;
                padding: 0;
            }



            #footer div {
                margin: 0;
                padding: 0 0 1ex 0;
            }

            #footer a {
                color: black;
                background-color: transparent;
                text-decoration: underline;
                font-weight: bold;
            }

            #footer a:hover {
                text-decoration: none;
            }


        </style>

    </head>
    <body>



    <div id="main-copy">
        <h1 id="Forum" style="border-top: none; padding-top: 0;">TEAMMATES FORUM</h1>
        <p>SEPT Major Project has two iterative incremental milestones designed to introduce students to various processes and tools including the Scrum Agile process model, refactoring and testing. You need to finish each task before beginning the next. Students will become familiar with Scrum meetings, Agile development model, collaboration, version-control and testing tools that facilitate a continuous integration (CI) approach. 5% of marks will be awarded for class progress on each milestone as specified in the Progress Marks section below. Students are expected to work individually 4-6 hours per week for this assignment outside of class time.</p>

        <h1 id="cross-browser">RMIT OPEN DAY!!!</h1>
        <tr>
            <td></td>

            <td><strong><p>by Author  &nbsp "##/##/####" &nbsp  "##" comments</p></strong></td>
        </tr>
        <p>SEPT Major Project has two iterative incremental milestones designed to introduce students to various
            processes and tools including the Scrum Agile process model, refactoring and testing. You need to finish
            each task before beginning the next. Students will become familiar with Scrum meetings, Agile development model
            , collaboration, version-control and testing tools that facilitate a continuous integration (CI) approach. 5% of marks
            will be awarded for class progress on each milestone as specified in the Progress Marks section below. Students are
            expected to work individually 4-6 hours per week for this assignment outside of class time.</p>

        <h1 id="stylesheets">RMIT DURIAN EVACUATION!!!</h1>

        <tr>
            <td></td>

            <td><strong><p>by Author  &nbsp "##/##/####" &nbsp  "##" comments</p></strong></td>
        </tr>

        <p>
            Also note that joining a course is optional. If you do not join, you can still submit feedback and view feedback responses using links sent to you by TEAMMATES. If you join, you get access to extra features such as the ability to see all your feedback sessions in a single home page.
        </p>
        <h1 id="accessibility">RMIT FINAL EXAMS!!!</h1>

        <tr>
            <td></td>

            <td><strong><p>by Author  &nbsp "##/##/####" &nbsp  "##" comments</p></strong></td>
        </tr>
        <p>Students will become familiar with Scrum meetings, Agile development model
            , collaboration, version-control and testing tools that facilitate a continuous integration (CI) approach. 5% of marks
            will be awarded for class progress on each milestone as specified in the Progress Marks section below. Students are
            expected to work individually 4-6 hours per week for this assignment outside of class time.</p>
        <p>Students will become familiar with Scrum meetings, Agile development model
            , collaboration, version-control and testing tools that facilitate a continuous integration (CI) approach. 5% of marks
            will be awarded for class progress on each milestone as specified in the Progress Marks section below. Students are
            expected to work individually 4-6 hours per week for this assignment outside of class time.</p>
    </div>

    <div id="main-copy">
        <Strong>Content:</Strong><br />
        <textarea name="content" id="content" rows="4" cols="70"></textarea><br/>
        <Strong>Author :</Strong>
        <input type="text" name="author" id="author" /><br>
        <Strong>Date : &nbsp&nbsp&nbsp  </Strong>
        <input type="text" name="date" id="date" /><br /><br />
        <button id="submitbtn">Send</button>
    </div>
</t:helpPage>


<script type="text/javascript">

    $("#date").val(getCurrentDate());


    function getCurrentDate() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
    month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
    strDate = "0" + strDate;
    }
    var currentdate = year + "/" + month + "/" + strDate;
    return currentdate;
    }
</script>

