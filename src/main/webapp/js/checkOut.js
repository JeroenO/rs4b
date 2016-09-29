/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
const rootURL = "https://localhost:8181/ws4/webresources/Klant";
let token = sessionStorage.getItem('token');
let shoppingcart = JSON.parse(sessionStorage.getItem('shoppingcart'));
console.log(" cart is nu " + typeof(shoppingcart) + shoppingcart) ;

showCartTabel();
showTotalen();


$(document).on('click', '#tableBodyCheckOut a', function () {
    removeFromCart($(this).data('identity'));
    recalculateCart();
});

$('#btnBuy').click(function () {
    buyBestelling();
    return false;

});

$('#chat').bind('keypress', function (e){
    let code = e.which;
     console.log(code);
    if (code === 13) {
        document.getElementById("antwoord").innerHTML = getAnswer($('#chat').val());
    }
   

});
function showCartTabel() {
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
//    renderCart(korteLijst);
    renderTabel(korteLijst);
  //  sessionStorage.setItem('shoppingcart' , JSON.stringify(shoppingcart));
  //  console.log('sessionstorage  ' + JSON.parse(sessionStorage.getItem('shoppingcart')));
    return false;

}
function showTotalen() {
document.getElementById("shoppingcart").innerHTML = "in cart " + totalPrice(shoppingcart);

document.getElementById("tableFooterCheckOut").innerHTML = '<td colspan="3"></td><td colspan="1">' + totalPrice(shoppingcart)+ '  </td>';
}
function recalculateCart(){
    
    sessionStorage.setItem('shoppingcart' , JSON.stringify(shoppingcart));
    showTotalen();
}

function removeFromCart(artikelId) {
    console.log('remove from list ' + artikelId);
    shoppingcart[artikelId] = null;
    $("#tableBodyCheckOut tr[data-identity=" + artikelId + "]").remove();
    
    
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
    return JSON.stringify(
            wagen
       
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
    $('#tableBodyCheckOut').append
    ('<tr data-identity= "'+artikel.idartikel +'"> <td><a href = "#" data-identity= "'+artikel.idartikel +'" >dump</a></td><td>' +artikel.naam +"</td><td>" +aantal +"</td><td>" +artikel.prijs +"</td>  </tr>");
}


function getAnswer(vraag){
    
    return vraag + " weet ik niet.. ";
}
//<h5 href = "#" data-identity= "'+artikel.idartikel +'" >
//<tr data-identity= "'+artikel.idartikel +'"> <td><a href = "#" data-identity= "'+artikel.idartikel +'" >dump</a></td><td>' +artikel.naam +"</td><td>" +aantal +"</td><td>" +artikel.prijs +"</td></tr>;
//</h5>;

//('<tr> <td><a href = "#" data-identity= "'+artikel.idartikel +'" >dump</a></td><td>' +artikel.naam +"</td><td>" +aantal +"</td><td>" +artikel.prijs +"</td>  </tr>");
