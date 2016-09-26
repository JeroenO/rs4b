/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
const rootURL = "https://localhost:8181/ws4/webresources/";

let currentKlant;
let currentAdressen;

findAllKlanten();

$(document).on('click', '#tableBody a', function () {
    console.log('findbyid');
    findById($(this).data('identity'));
  //  findAdressenKlant($(this).data('identity'));
});

function findById(id) {
    console.log('findById: ' + id);
    $.ajax({
        type: 'GET',
        url: rootURL + 'Klant/' + id,
        dataType: "json",
        success: function (data) {
            //	$('#btnDelete').show();
            console.log('findById success: ' + data.adresCollection[0].postcode);
            currentKlant = data;
            currentAdressen = data.adresCollection;
            renderKlantDetails(currentAdressen);
        }
    });
}


function findAllKlanten() {
    console.log('findAllKlanten');
    $.ajax({
        type: 'GET',
        url: rootURL + "Klant",
        dataType: "json", // data type of response
        success: renderTabel
    });
}
//function findAdressenKlant(klantid) {
//    console.log('findAdressenKlant');
//    $.ajax({
//        type: 'GET',
//        url: rootURL + 'Klant/adressen/' +klantid,
//        dataType: "json", // data type of response
//        success: function(data) {  console.log('adressen :  ' + data);
//        }
//    });
//}

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
    ('<tr> <td><a href = "#" data-identity= "'+adresData.idadres +'" >adres</td><td>' +adresData.postcode +"</td><td>" +adresData.huisnr +"</td> <td>" +adresData.straat +"</td> <td>" +adresData.plaatsnaam +"</td></a></tr>");
}

function renderTabel(data) {
    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    let list = data === null ? [] : (data instanceof Array ? data : [data]);

    $('#tableBody tr').remove();
    $.each(list, function (index, artikel) {
   //     console.log("..");
        addToTabel(artikel); //$('#artikelList').append('<li><a href="#" data-identity="' + artikel.idartikel + '">'+artikel.naam+'</a></li>');
    });
}
function addToTabel(klantData) {
//console.log("artikel " + artikelData.idartikel);
    $('#tableBody').append
    ('<tr> <td><a href = "#" data-identity= "'+klantData.idklant +'" >zoek</td><td>' +klantData.email +"</td><td>" +klantData.voornaam +"</td> <td>" +klantData.achternaam +"</td> <td>" +klantData.idklant +"</td></a></tr>");
}
