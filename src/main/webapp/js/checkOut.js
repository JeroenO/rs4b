/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
const rootURL = "https://localhost:8181/ws4/webresources/Klant";
let token = sessionStorage.getItem('token');
let shoppingcart = JSON.parse(sessionStorage.getItem('shoppingcart'));
console.log(" cart is nu " + typeof(shoppingcart) + shoppingcart) ;

showCartList();


document.getElementById("shoppingcart").innerHTML = "in cart " + totalPrice(shoppingcart);

$('#btnBuy').click(function () {
    buyBestelling();
    return false;

});


function showCartList() {
    console.log('chout button  ' + shoppingcart.length);
    let korteLijst = [];
    for (let item of shoppingcart) {
        if (item !== null  && typeof (item) !== "undefined") {
          //  let artikelBesteld = item.artikel;
          //  artikelBesteld.aantal = item.aantal;
            korteLijst.push(item);
        }
    }
    console.log('korte lijst is nu ' + korteLijst.length);
    renderCart(korteLijst);
    renderTabel(korteLijst);
  //  sessionStorage.setItem('shoppingcart' , JSON.stringify(shoppingcart));
  //  console.log('sessionstorage  ' + JSON.parse(sessionStorage.getItem('shoppingcart')));
    return false;

}

function renderCart(data) {
    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    let list = data === null ? [] : (data instanceof Array ? data : [data]);

    $('#artikelCart li').remove();
    $.each(list, function (index, besteldArtikel) {
        addToCart(besteldArtikel);//$('#artikelList').append('<li><a href="#" data-identity="' + artikel.idartikel + '">'+artikel.naam+'</a></li>');
    });
}
function addToCart(artikelData) {
    let artikel = artikelData.artikel;
    let aantal = artikelData.aantal;
    $('#artikelCart').append('<li><a href="#" data-identity="' + artikel.idartikel + '">' + artikel.naam + '</a> ' + artikel.prijs +'  '  + aantal + '</li>');
}
function removeFromCart(artikelId) {
    console.log('remove from list ' + artikelId);

    $("#artikelCart a[data-identity=" + artikelId + "]").remove();
}

function totalPrice(cart) {
    let totaal = 0;
    for (let item of cart) {
        if (typeof(item) !== 'undefined' && item !== null ) {
            totaal += item.aantal * item.artikel.prijs;
        }
    }
    return totaal;
}

function buyBestelling() {
    console.log('buy' + token);
     $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: rootURL + '/Buy',
        dataType: "json",
        data: cartToJSON(),
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", "Bearer " + token);
        },
        success: function (data, textStatus, jqXHR) {
            alert('Bestelling placed successfully ');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Bestelling placed error: ' + errorThrown);
           
            if (errorThrown === 'Unauthorized') {
                
                window.location.replace("inlogscherm.html");
            } 
        }
    });
}
function cartToJSON() {
    let wagen = {};
    for (let item of shoppingcart) {
        if (item !== null  && typeof (item) !== "undefined") {
          //  let artikelBesteld = item.artikel;
          //  artikelBesteld.aantal = item.aantal;
            wagen[item.artikel.idartikel] = item.aantal;
        }
    }
    return JSON.stringify(wagen
        
        
    //    "image": uploadfiles.files[0]
    );
}
function renderTabel(data) {
    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    let list = data === null ? [] : (data instanceof Array ? data : [data]);

//    $('#tableBody tr').remove();
    $.each(list, function (index, artikel) {
   //     console.log("..");
        addToTabel(artikel); //$('#artikelList').append('<li><a href="#" data-identity="' + artikel.idartikel + '">'+artikel.naam+'</a></li>');
    });
}
function addToTabel(besteldArtikel) {
//console.log("artikel " + artikelData.idartikel);
let artikel = besteldArtikel.artikel;
    let aantal = besteldArtikel.aantal;
    $('#tableBody').append
    ('<tr> <td><a href = "#" data-identity= "'+artikel.naam +'" >dump</td><td>' +artikel.naam +"</td><td>" +aantal +"</td><td>" +artikel.prijs +"</td>  </a></tr>");
}
