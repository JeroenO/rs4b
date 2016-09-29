/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
const srootURL = "https://localhost:8181/ws4/webresources/";
let ingelogdeKlant;
let currentAdressen;
let welkAdres = 0;
init();

$('#btnWijzig').click(function () {
    invoeren();
    return false;
});
$('#btnUpdate').click(function () {
    console.log("update");
    updateKlant();
    return false;
});

$('#btnNieuwAdres').click(function () {
    console.log("nieuw adres");
    renderAdres({0: 0}, 0);
    invoeren();
    return false;
});

$('#btnSaveAdres').click(function () {
    console.log("save adres" + $('#idadres').val());
    
        console.log("nieuw adres");
        let adres = saveAdres();
        let bestaat = bestaatAl(adres);
        if (bestaat) {
            updateAdres(adres);
        }
        else {
            ingelogdeKlant.adresCollection.push(adres);
            updateKlantMetAdres();
        }
    
    return false;
});
$('#btnNextAdres').click(function () {
    console.log("btnNextAdres " + currentAdressen.length);
    if (welkAdres < currentAdressen.length-1) {
        welkAdres++;
    }
    else {
        welkAdres = 0;
    }
    renderAdres(currentAdressen, welkAdres );
    return false;
});

$(document).on('click', '#tableBodyBestelling a', function () {
    console.log('find bestelde artikelen by idbestelling');
    
    findBesteldeArtikelen($(this).data('identity'));
  
});
$(document).on('click', '#tableDataBesteldeArtikelen a', function () {
    renderArtikelDetails($(this).data('identity'));
});
$('#chat').bind('keypress', function (e){
    let code = e.which;
     console.log(code);
    if (code === 13) {
        document.getElementById("antwoord").innerHTML = getAnswer($('#chat').val());
    }
   

});


function updateKlant() {
    console.log('updateKlant');
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: srootURL + 'Klant/' + ingelogdeKlant.idklant, //$('#klantId').val(),  //potentieel gevaarlijk?
        dataType: "json",
        data: persoonsFormToJSON(),
        success: function (data, textStatus, jqXHR) {
            console.log('form ' + persoonsFormToJSON());
            alert('Klant updated successfully');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('updateKlant error: ' + textStatus);
        }
    });
}

function saveAdres() {
    console.log('saveAdres');
    let adres = {
        "idadres": $('#idadres').val(),
        "straat": $('#straat').val(),
        "huisnr": $('#huisnr').val(),
        "postcode": $('#postcode').val(),
        "plaatsnaam": $('#plaatsnaam').val()

    };
   console.log(adres);
    return adres;
}
function bestaatAl(adres) {
    console.log("vergelijken vervangofvernieuw " + adres + adres.idadres );
    console.log("bestaat oa " + ingelogdeKlant.adresCollection[0].idadres);
    for (let bestaand of ingelogdeKlant.adresCollection){
        console.log("in collectie " + bestaand.idadres);
        if (+(adres.idadres) === +(bestaand.idadres) && +(adres.idadres) !== +"" ) {
            //bestaand = adres;
            console.log("bestaand adres");
            return true;
        }
    }
    console.log("nieuw adres");
    return false;
    
   
}
function updateKlantMetAdres() {
    console.log('updateKlant');
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: srootURL + 'Klant/' + ingelogdeKlant.idklant, //$('#klantId').val(),  //potentieel gevaarlijk?
        dataType: "json",
        data: JSON.stringify(ingelogdeKlant),
        success: function (data, textStatus, jqXHR) {
            console.log('form ' + persoonsFormToJSON());
            alert('Klant updated successfully');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('updateKlant error: ' + textStatus);
        }
    });
}
function updateAdres(adres) {
    console.log('updateAdres');
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: srootURL + 'adres/' + adres.idadres, //$('#klantId').val(),  //potentieel gevaarlijk?
        dataType: "json",
        data: JSON.stringify(adres),
        success: function (data, textStatus, jqXHR) {
            
            alert('Adres updated successfully');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('updateAdres error: ' + errorThrown);
        }
    });
}



function init() {
    let tokenwoorden = token.split(" ");
    let klantEmail = tokenwoorden[tokenwoorden.length - 1];

    findByEmail(klantEmail).done(function (result) {

        renderPersonalia(result);
        renderAdres(result.adresCollection, welkAdres);
        findBestellingenKlant(ingelogdeKlant.idklant);
    }
    );



}


function findByEmail(email) {
    console.log('findByEamil: ' + email + " token " + token);
    return $.ajax({
        type: 'GET',
        url: "https://localhost:8181/ws4/webresources/" + 'Klant/' + email,
        dataType: "json",
        success: function (data) {

            console.log('findByEmail success: ' + data.voornaam);
            ingelogdeKlant = data;
            currentAdressen = data.adresCollection;
            return data;
        }

    });

}
function renderPersonalia(klant) {
    console.log("rendering " + klant.email);

    $('#email').val(klant.email);
    $('#voornaam').val(klant.voornaam);
    $('#achternaam').val(klant.achternaam);
    $('#klantId').val(klant.idklant);

}

function renderAdres(adressen, index) {
    console.log("rendering adres " + adressen[index]);
    let adres = adressen[index];
    $('#idadres').val(adres.idadres);
    $('#straat').val(adres.straat);
    $('#huisnr').val(adres.huisnr);
    $('#postcode').val(adres.postcode);
    $('#plaatsnaam').val(adres.plaatsnaam);
    return false;
}


function persoonsFormToJSON() {

    return JSON.stringify({
        "idklant": ingelogdeKlant.idklant, // id niet te wijzigen en email vooralsnog ook niet
        "voornaam": $('#voornaam').val(),
        "achternaam": $('#achternaam').val(),
        "email": ingelogdeKlant.email

    });
}



function invoeren() {
    console.log("wijzigen ");
    $('input').each(function () {
        if ($(this).attr('disabled')) {
            $(this).removeAttr('disabled');
        } else {
            $(this).attr({
                'disabled': 'disabled'
            });
        }
    });
return false;
}
function findBestellingenKlant(klantid) {
    console.log('findBestellingenKlant');
    $.ajax({
        type: 'GET',
        url: srootURL + 'Bestelling/' +klantid,
        dataType: "json", // data type of response
        success: function(data) {  
            console.log('bestelling :  ' + data);
          
            renderTabel2(data);
        }
    });
}
function renderTabel2(data) {
    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    let list = data === null ? [] : (data instanceof Array ? data : [data]);
console.log("lengte data " + data.length);
    $('#tableBodyBestelling tr').remove();
    $.each(list, function (index, bestelling) {
     //   console.log("..   " + Object.getOwnPropertyNames(bestelling));
        addToTabel2(bestelling); //$('#artikelList').append('<li><a href="#" data-identity="' + artikel.idartikel + '">'+artikel.naam+'</a></li>');
    });
}
function addToTabel2(bestellingData) {
console.log("bestelling...  " + bestellingData.besteldatum);
    $('#tableBodyBestelling').append('<tr> <td><a href = "#" data-identity= "'+bestellingData.idbestelling +'" >zoek</td><td>' +bestellingData.idbestelling +"</td> <td>" +bestellingData.besteldatum +"</td></a></tr>");
}
function findBesteldeArtikelen(idbestelling) {
    console.log('findBestellingenKlant');
    $.ajax({
        type: 'GET',
        url: srootURL + 'BesteldeArtikelen/' +idbestelling,
        dataType: "json", // data type of response
        success: function(data) {  
            console.log('bestelling :  ' + data);
            console.log('bestelling 0 :  ' + data[0]);
            renderTabel3(data);
        }
    });
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
console.log("bestelling...  " + Object.getOwnPropertyNames(besteldeArtikelen));
    $('#tableBodyBesteldeArtikelen').append('<tr> <td><a href = "#" data-identity= "'+besteldeArtikelen.artikel.naam +'" >..</td><td>' +besteldeArtikelen.aantal +"</td> <td>" +besteldeArtikelen.artikel.naam +"</td><td>" +besteldeArtikelen.artikel.prijs +"</td></a></tr>");
}
function renderArtikelDetails(artikelnaam) {
    console.log("plaatje tekenen van: " + artikelnaam);
    $('#name').val(artikelnaam);
    
    
   $('#pic').attr('src', 'pics/' + artikelnaam + '.jpg');
   $('#chat').val("Maak een praatje met je vriend "+ artikelnaam + "!");
}
function getAnswer(vraag){
    
    return vraag + " weet ik niet.. ";
}