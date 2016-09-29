/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
       
const inlogURL = "https://localhost:8181/ws4/webresources/login";

//console.log("testToken " + token());
$('#btnLogin').click(function() {
	getToken();
	return false;        
});

$('#btnAdmin').click(function() {
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
                
		success: function(data, textStatus, jqXHR){
			alert('logged in  ' + data.username + " " + data.token);

                        saveToken(data.token);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('getToken error logging in: ' + textStatus + " "+ errorThrown +
                                    " DATA IS  " + userpwToJSON());
		}
	});
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
