/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
const rootURL = "https://localhost:8181/ws4/webresources/";
const inlogURL = "https://localhost:8181/ws4/webresources/login";

let currentKlant;
let currentAdressen;
let currentBestellingen;
let token = sessionStorage.getItem('token');

findAllKlanten();

$(document).on('click', '#tableBody a', function () {
    console.log('findbyid');
    findById($(this).data('identity'));
    findBestellingenKlant($(this).data('identity'));
  //  findAdressenKlant($(this).data('identity'));
});
$(document).on('click', '#tableBodyBestelling a', function () {
    console.log('find bestelde artikelen by idbestelling');
    
    findBesteldeArtikelen($(this).data('identity'));
  
});

function findById(id) {
    console.log('findById: ' + id);
    $.ajax({
        type: 'GET',
        url: rootURL + 'Klant/' + id,
        dataType: "json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", "Bearer " + token);},
        success: function (data) {
            //	$('#btnDelete').show();
            console.log('findById success: ' + data.adresCollection[0].postcode);
            currentKlant = data;
            currentAdressen = data.adresCollection;
            renderKlantDetails(currentAdressen);
            $('#username').val(currentKlant.email);
          
        }
    });
}



function findAllKlanten() {
    console.log('findAllKlanten');
    
    $.ajax({
        type: 'GET',
        url: rootURL + "Klant",
        dataType: "json", // data type of response
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", "Bearer " + token);},
        success: renderTabel
    });
}
function findBestellingenKlant(klantid) {
    console.log('findBestellingenKlant');
    $.ajax({
        type: 'GET',
        url: rootURL + 'Bestelling/' +klantid,
        dataType: "json", // data type of response
        success: function(data) {  
            console.log('bestelling :  ' + data);
            console.log('bestelling 0 :  ' + data[0]);
            renderTabel2(data);
        }
    });
}
function findBesteldeArtikelen(idbestelling) {
    console.log('findBestellingenKlant');
    $.ajax({
        type: 'GET',
        url: rootURL + 'BesteldeArtikelen/' +idbestelling,
        dataType: "json", // data type of response
        success: function(data) {  
            console.log('bestelling :  ' + data);
            console.log('bestelling 0 :  ' + data[0]);
            renderTabel3(data);
        }
    });
}

function renderKlantDetails(adressen) {
    let list = adressen === null ? [] : (adressen instanceof Array ? adressen : [adressen]);
    document.getElementById('klantId').innerHTML = currentAdressen[0].postcode + ', ' + currentAdressen[0].huisnr;
    console.log("....." + adressen[0].postcode);
    $('#tableFooter tr').remove();
    $.each(list, function (index, adres) {
   //     console.log("..");
        addToFooter(adres); //$('#artikelList').append('<li><a href="#" data-identity="' + artikel.idartikel + '">'+artikel.naam+'</a></li>');
    });
 }
function addToFooter(adresData) {
//console.log("artikel " + artikelData.idartikel);
    $('#tableFooter').append
    ('<tr><td><a href = "#" data-identity= "'+adresData.idadres +'" >adres</a></td><td>' +adresData.postcode +"</td><td>" +adresData.huisnr +"</td> <td>" +adresData.straat +"</td> <td>" +adresData.plaatsnaam +"</td></a><tr>");
    }
//('<tr><td><a href = "#" data-identity= "'+adresData.idadres +'" >adres</a></td><td>' +adresData.postcode +"</td><td>" +adresData.huisnr +"</td> <td>" +adresData.straat +"</td> <td>" +adresData.plaatsnaam +"</td></a><tr>");


function renderTabel(data) {
    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    let list = data === null ? [] : (data instanceof Array ? data : [data]);

    $('#tableBody tr').remove();
    $.each(list, function (index, klant) {
        console.log("..? " + klant.idklant);
        addToTabel(klant); //$('#artikelList').append('<li><a href="#" data-identity="' + artikel.idartikel + '">'+artikel.naam+'</a></li>');
    });
}
function addToTabel(klantData) {
//console.log("artikel " + artikelData.idartikel);
    $('#tableBody').append  
    ('<tr> <td><a href = "#" data-identity= "'+klantData.idklant +'" >zoek</td><td>' +klantData.email +"</td><td>" +klantData.voornaam +"</td> <td>" +klantData.achternaam +"</td> <td>" +klantData.idklant +"</td></a></tr>");
}
function renderTabel2(data) {
    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    let list = data === null ? [] : (data instanceof Array ? data : [data]);
console.log("lengte data " + data.size);
    $('#tableBodyBestelling tr').remove();
    $.each(list, function (index, bestelling) {
        console.log("..   " + Object.getOwnPropertyNames(bestelling));
        addToTabel2(bestelling); //$('#artikelList').append('<li><a href="#" data-identity="' + artikel.idartikel + '">'+artikel.naam+'</a></li>');
    });
}
function addToTabel2(bestellingData) {
console.log("bestelling...  " + bestellingData.besteldatum);
    $('#tableBodyBestelling').append('<tr> <td><a href = "#" data-identity= "'+bestellingData.idbestelling +'" >zoek</td><td>' +bestellingData.idbestelling +"</td> <td>" +bestellingData.besteldatum +"</td></a></tr>");
}
function renderTabel3(data) {
    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    let list = data === null ? [] : (data instanceof Array ? data : [data]);
    let totaal = 0;
console.log("lengte data " + data.size);
    $('#tableBodyBesteldeArtikelen tr').remove();
    $.each(list, function (index, besteldeArtikelen) {
        console.log("..   " + Object.getOwnPropertyNames(besteldeArtikelen));
        addToTabel3(besteldeArtikelen); //$('#artikelList').append('<li><a href="#" data-identity="' + artikel.idartikel + '">'+artikel.naam+'</a></li>');
        totaal += (besteldeArtikelen.artikel.prijs * besteldeArtikelen.aantal);
    });
    
    document.getElementById("tableFooterBesteldeArtikelen").innerHTML = 
                                         '<td colspan="3"></td><td colspan="1">' + totaal + '</td>';
}
function addToTabel3(besteldeArtikelen) {
console.log("bestelling...  " + besteldeArtikelen.besteldatum);
    $('#tableBodyBesteldeArtikelen').append('<tr> <td><a href = "#" data-identity= "'+besteldeArtikelen.idbestelling +'" >..</td><td>' +besteldeArtikelen.aantal +"</td> <td>" +besteldeArtikelen.artikel.naam +"</td><td>" +besteldeArtikelen.artikel.prijs +"</td></a></tr>");
}

$('#btnLogin').click(function() {
	superUserLogin();
	return false;        
});
function superUserLogin() {
	console.log('logging in getting token' + $('#username').val());
	$.ajax({
		type: 'GET',
		contentType: 'application/json',
		url: rootURL + "login/" +  $('#username').val() ,
                
		 beforeSend: function (request) {
            request.setRequestHeader("Authorization", "Bearer " + token);
        },
                
		success: function(data, textStatus, jqXHR){
			alert('logged in  ' + data.username + " " + data.token);
                        saveToken(data.token);
                        window.location.replace("personalia.html");
                      
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('trying to play God: ' + errorThrown );
		}
	});
}

function saveToken(token) {
    sessionStorage.setItem('token', token);
}