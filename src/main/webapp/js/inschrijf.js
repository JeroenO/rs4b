/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
const rootURL = "https://localhost:8181/ws4/webresources/adres";

$('#inschrijfForm').submit(function() {
	
	addKlant();
        window.location.replace("inlogscherm.html");
     	return false;
});
function addKlant() {
	console.log('addKlant');
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL + "/" + $('#password2').val(),
		dataType: "json",
		data: inschrijfFormToJSON(),               
                
		success: function(data, textStatus, jqXHR){
			alert('klant created successfully'  );
                
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('addklant error: ' + textStatus);
		}
	});
}

function inschrijfFormToJSON() {
    
    let adres = {
        "straat": $('#straatnaam').val(),
                "huisnr": $('#huisnummer').val(),
                "postcode": $('#postcode').val(),
                "plaatsnaam": $('#plaatsnaam').val(),
    };
	
	return JSON.stringify({
          
		"voornaam": $('#voornaam').val(), 
		"achternaam": $('#achternaam').val(),
		"email": $('#email').val(),
                "adresCollection": [adres]
            

		});
}