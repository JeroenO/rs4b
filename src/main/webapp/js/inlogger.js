/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
       
const inlogURL = "http://localhost:8080/ws4/webresources/login";
let token;
//console.log("testToken " + token());
$('#btnLogin').click(function() {
	getToken();
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

                        token = saveToken(data.token);
                        console.log("testtoken " + token );
                        console.log("testtoken " + token() );
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
    let savedToken = token;
    
    let getToken = function() { 
        return savedToken;
    };
    return getToken;
}
