/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const inlogURL = "https://localhost:8181/ws4/webresources/login";
showButton();
//console.log("testToken " + token());
$('#btnLogin').click(function () {
    getToken();
    return false;
});

$('#btnAdmin').click(function () {
    console.log("naar admin");
    window.location.replace("admin.html");
    return false;
});


function getToken() {
    console.log('logging in getting token' + $('#username').val());
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: inlogURL,

        dataType: "json",
        data: userpwToJSON(),

        success: function (data, textStatus, jqXHR) {
            alert('logged in as ' + data.username);

            saveToken(data.token);
            showButton();

        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('getToken error logging in: ' + textStatus + " " + errorThrown +
                    " DATA IS  " + userpwToJSON());
        }
    });
}
function showButton() {
    let username = extractUserName(sessionStorage.getItem('token'));
    console.log("showing button for " + username);
    if (username === "ra@ra" || username === "evil@hacker") {
        $('#btnAdmin').show();
    }
}

function userpwToJSON() {

    return JSON.stringify({
        "username": $('#username').val(),
        "password": $('#password').val()

    });
}

function saveToken(token) {
    sessionStorage.setItem('token', token);
}

function extractUserName(teken) {
    if (teken) {
        let tokenwoorden = teken.split(" ");
        let tokenmail = tokenwoorden[tokenwoorden.length - 1];
        return tokenmail;
    }
}