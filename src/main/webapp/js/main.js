// The root URL for the RESTful services
const rootURL = "https://localhost:8181/ws4/webresources/artikel";
//
let currentArtikel;
let token = sessionStorage.getItem('token');
let shoppingcart = JSON.parse(sessionStorage.getItem('shoppingcart'));
console.log("cart nu " + shoppingcart );

if (!shoppingcart) {
    shoppingcart = [];
}
else {
    document.getElementById("shoppingcart").innerHTML = "in cart " + totalPrice(shoppingcart);
}

findAll();
showButtonsIndex();

$('#pic').attr('src', 'pics/' + 'fairy125.gif');

$(window).on("beforeunload", function() {
    sessionStorage.setItem('shoppingcart' , JSON.stringify(shoppingcart));
    console.log('sessionstorage  ' + JSON.parse(sessionStorage.getItem('shoppingcart')));
});

$('#btnAdd').click(function () {
    newArtikel();
    return false;

});
//
$('#btnSave').click(function () {
    if ($('#artikelId').val() === '')
        addArtikel();
    else
        updateArtikel();
   if (uploadfiles.value !== "") {
       uploadFile(uploadfiles.files[0]);
   }
    return false;
});

$('#btnDelete').click(function () {
    deleteArtikel();
    return false;
});
$('#btnEmptyCart').click(function () {
    shoppingcart = [];
    document.getElementById("shoppingcart").innerHTML = "in cart " + totalPrice(shoppingcart);
    return false;
});

$(document).on('click', '#btnAddToCart', function () {
    fillCart(+($('#aantalArtikel').val()));
});

$(document).on('click', '#artikelList a', function () {
    findById($(this).data('identity'));
});

let uploadfiles = document.querySelector('#fileinput');
uploadfiles.addEventListener('change', function () {
    let files = this.files;
    for(let i=0; i< files.length; i++){
        previewImage(this.files[i]);
    }

}, false);

function newArtikel() {

    currentArtikel = {};
    renderDetails(currentArtikel); // Display empty form
   // $('#pic').hide();
}

function findAll() {
    console.log('findAll');
    $.ajax({
        type: 'GET',
        url: rootURL,
        dataType: "json", // data type of response
        success: renderList
    });
}

function findById(id) {
    console.log('findById: ' + id);
    $.ajax({
        type: 'GET',
        url: rootURL + '/' + id,
        dataType: "json",
        success: function (data) {
            //	$('#btnDelete').show();
            console.log('findById success: ' + data.naam);
            currentArtikel = data;
            renderDetails(currentArtikel);
        }
    });
}
//
function addArtikel() {
    console.log('addArtikel');
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: rootURL,
        dataType: "json",
        data: formToJSON(),
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", "Bearer " + token);
        },
        success: function (data, textStatus, jqXHR) {
            alert('Artikel created successfully' + data.idartikel);
//			$('#btnDelete').show();
            $('#artikelId').val(data.idartikel);
            addToList(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('addArtikel error: ' + errorThrown);
        }
    });
}

function updateArtikel() {
    console.log('updateArtikel');
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: rootURL + '/' + $('#artikelId').val(),
        dataType: "json",
        data: formToJSON(),
         beforeSend: function (request) {
            request.setRequestHeader("Authorization", "Bearer " + token);
        },
        success: function (data, textStatus, jqXHR) {
            alert('Artikel updated successfully');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('updateArtikel error: ' + errorThrown);
        }
    });
}

function deleteArtikel() {
    console.log('delete' + token);
    $.ajax({
        type: 'DELETE',
        url: rootURL + '/' + $('#artikelId').val(),
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", "Bearer " + token);
        },
        success: function (data, textStatus, jqXHR) {
            removeFromList($('#artikelId').val());
            alert('Artikel deleted successfully');
            newArtikel();

        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('deleteArt error  ' + errorThrown);
        }
    });
}

function renderList(data) {
    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    let list = data === null ? [] : (data instanceof Array ? data : [data]);

    $('#artikelList li').remove();
    $.each(list, function (index, artikel) {
   //     console.log("..");
        addToList(artikel); //$('#artikelList').append('<li><a href="#" data-identity="' + artikel.idartikel + '">'+artikel.naam+'</a></li>');
    });
}
function addToList(artikelData) {
//console.log("artikel " + artikelData.idartikel);
    $('#artikelList').append('<li><a href="#" data-identity="' + artikelData.idartikel + '">' + artikelData.naam + '</a></li>');
}
function removeFromList(artikelId) {
    console.log('remove from list ' + artikelId);

    $("#artikelList a[data-identity=" + artikelId + "]").remove();
}
function renderDetails(artikel) {
    $('#artikelId').val(artikel.idartikel);
    $('#name').val(artikel.naam);
    $('#prijs').val(artikel.prijs);
    $('#description').val(artikel.omschrijving);
   $('#pic').attr('src', 'pics/' + artikel.naam + '.jpg');
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
    var artikelId = $('#artikelId').val();
    return JSON.stringify({
        "idartikel": artikelId === "" ? null : artikelId,
        "naam": $('#name').val(),
        "prijs": $('#prijs').val(),
        "omschrijving": $('#description').val()
    //    "image": uploadfiles.files[0]
    });
}



function previewImage(file) {
    let galleryId =  "gallery";

    let gallery = document.getElementById(galleryId);
    while(gallery.firstChild) gallery.removeChild(gallery.firstChild);  // slechts 1 plaatje toevoegen
    let imageType = /image.*/;

    if (!file.type.match(imageType)) {
        throw "File Type must be an image";
    }

    let thumb =  document.createElement("div");
    
    thumb.classList.add('thumbnail'); // Add the class thumbnail to the created div

    let img = document.createElement("img");
    img.file = file;
    
    thumb.appendChild(img);
    gallery.appendChild(thumb);

    // Using FileReader to display the image content
    let reader = new FileReader();
    reader.onload = (function(aImg) { return function(e) { aImg.src = e.target.result; }; })(img);
    reader.readAsDataURL(file);
}

function totalPrice(cart) {
    let totaal = 0;
    for (let item of cart) {
        if (item !== undefined && item !== null) {
            totaal += item.aantal * item.artikel.prijs;
        }
    }
    return totaal;
}

function uploadFile(file) {
    console.log('uploadfile');
    
    let fd = new FormData();
    fd.append("upload_file", file);
    fd.append("artikelnaam", currentArtikel.naam);
    
    $.ajax({
        type: 'POST',
        contentType: false,
        url: rootURL + '/image' ,
        processData: false,
        data: fd,
        success: function (data, textStatus, jqXHR) {
            alert('plaatje uploaded successfully');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('updateArtikel error: ' + textStatus);
        }
    });
}
function showButtonsIndex() {
    let username = extractUserName(sessionStorage.getItem('token'));
    console.log("showing button for "+ username);
    if (username === "ra@ra" || username === "evil@hacker") {
        $('#btnDelete').show();
        $('#btnSave').show();
        $('#btnAdd').show();
        $('#fileinput').show();
    }
}
function extractUserName(teken) {
    if (teken){
    var tokenwoorden = teken.split(" ");
    var tokenmail = tokenwoorden[tokenwoorden.length - 1];
    return tokenmail;
}
}


//$('#btnCheckOut').click(function () {
//    console.log('chout button  ' + shoppingcart.length);
//    let korteLijst = [];
//    for (let item of shoppingcart) {
//        if (typeof (item) !== "undefined") {
//            let artikel = item.artikel;
//            korteLijst.push(artikel);
//        }
//    }
//    console.log('korte lijst is nu ' + korteLijst.length);
//    renderList(korteLijst);
//    
////    sessionStorage.setItem('shoppingcart' , JSON.stringify(shoppingcart));
////    console.log('sessionstorage  ' + JSON.parse(sessionStorage.getItem('shoppingcart')));
//    return false;
//
//});
//$('#btnAddToCart').click(function() {
//	fillCart(1);
//	return false;
//});
