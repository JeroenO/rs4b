/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//let shoppingcart = [];
document.getElementById("ingelogdAls").innerHTML = "hallo " + extractUserName(token);

//
//$('#btnCheckOut').click(function () {
//    console.log('chout button  ' + shoppingcart.length);
//    let korteLijst = [];
//    for (let item of shoppingcart) {
//        if (typeof (item) !== "undefined") {
//            let artikel = item.artikel;
//            korteLijst.push(artikel);
//        }
//    }
//    console.log('korte lijst is nu   ' + korteLijst.length);
//    renderList(korteLijst);
//    sessionStorage.setItem('shoppingcart' , shoppingcart);
//    console.log('sessionstorgae  ' + sessionStorage.getItem('shoppingcart'));
//    return false;
//
//});


function extractUserName(teken) {
    var tokenwoorden = teken.split(" ");
    var tokenmail = tokenwoorden[tokenwoorden.length - 1];
    return tokenmail.split("@")[0];
}

function fillCart(aantal) {
    console.log('fillCart');
    let id = currentArtikel.idartikel;

    let besteldArtikel = {
        aantal: aantal,
        artikel: currentArtikel
    };

    if (shoppingcart[id] === undefined) {
        shoppingcart[id] = besteldArtikel;
    } else {
        shoppingcart[id].aantal += aantal;
    }

    document.getElementById("shoppingcart").innerHTML = "in cart " + totalPrice(shoppingcart); // + " <button id=\"btnCheckOut\">check out</button>";
//    $('#shoppingcart').append('<button id="btnCheckOut">check out</button>');
//    let chckOutBut = $('#butCheckOut');
//    let fun = addToCheckoutButton();
//    chckOutBut.click(fun);


}

function totalPrice(cart) {
    let totaal = 0;
    for (let item of cart) {
        if (item !== undefined) {
            totaal += item.aantal * item.artikel.prijs;
        }
    }
    return totaal;
}

function showCart(cart) {


}

//function renderCart(data) {
//    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
//    let list = data === null ? [] : (data instanceof Array ? data : [data]);
//
//    $('#artikelCart li').remove();
//    $.each(list, function (index, artikel) {
//        addToList(artikel);//$('#artikelList').append('<li><a href="#" data-identity="' + artikel.idartikel + '">'+artikel.naam+'</a></li>');
//    });
//}
//function addToCart(artikelData) {
//
//    $('#artikelCart').append('<li><a href="#" data-identity="' + artikelData.idartikel + '">' + artikelData.naam + '</a></li>');
//}
//function removeFromList(artikelId) {
//    console.log('remove from list ' + artikelId);
//
//    $("#artikelCart a[data-identity=" + artikelId + "]").remove();
//}

function addToCheckoutButton() {
    console.log('chout button  ' + shoppingcart.length);
    let korteLijst = [];
    for (let item of shoppingcart) {
        if (typeof (item) !== "undefined") {
            let artikel = item.artikel;
            korteLijst.push(artikel);
        }
    }
    console.log('korte lijst  ' + korteLijst.length);
    renderList(korteLijst);
    return false;

}
