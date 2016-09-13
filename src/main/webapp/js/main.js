// The root URL for the RESTful services
const rootURL = "http://localhost:8080/ws4/webresources/artikel";
//
let currentArtikel;

// Retrieve wine list when application starts 
findAll();
$('#pic').attr('src', 'pics/' + 'tinker1.jpeg');
//// Nothing to delete in initial application state
//$('#btnDelete').hide();
//
//// Register listeners
//$('#btnSearch').click(function() {
//	search($('#searchKey').val());
//	return false;
//});
////
//// Trigger search when pressing 'Return' on search key input field
//$('#searchKey').keypress(function(e){
//	if(e.which == 13) {
//		search($('#searchKey').val());
//		e.preventDefault();
//		return false;
//    }
//});
//
$('#btnAdd').click(function() {
	newArtikel();
	return false;
        
});
//
$('#btnSave').click(function() {
	if ($('#artikelId').val() === '')
		addArtikel();
	else
		updateArtikel();
	return false;
});

$('#btnDelete').click(function() {
	deleteArtikel();
	return false;
});

$(document).on('click','#artikelList a', function() {
	findById($(this).data('identity'));
});
//
//// Replace broken images with generic wine bottle
//$("img").error(function(){
//  $(this).attr("src", "pics/generic.jpg");
//
//});
//
//function search(searchKey) {
//	if (searchKey == '') 
//		findAll();
//	else
//		findByName(searchKey);
//}
//
function newArtikel() {
	//$('#btnDelete').hide();
	currentArtikel = {};
	renderDetails(currentArtikel); // Display empty form
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
//
//function findByName(searchKey) {
//	console.log('findByName: ' + searchKey);
//	$.ajax({
//		type: 'GET',
//		url: rootURL + '/search/' + searchKey,
//		dataType: "json",
//		success: renderList 
//	});
//}
//
function findById(id) {
	console.log('findById: ' + id);
	$.ajax({
		type: 'GET',
		url: rootURL + '/' + id,
		dataType: "json",
		success: function(data){
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
		success: function(data, textStatus, jqXHR){
			alert('Artikel created successfully' + data.idartikel );
//			$('#btnDelete').show();
			$('#artikelId').val(data.idartikel);
                        addToList(data);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('addArtikel error: ' + textStatus);
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
		success: function(data, textStatus, jqXHR){
			alert('Artikel updated successfully');
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('updateArtikel error: ' + textStatus);
		}
	});
}

function deleteArtikel() {
	console.log('deleteWine');
	$.ajax({
		type: 'DELETE',
		url: rootURL + '/' + $('#artikelId').val(),
		success: function(data, textStatus, jqXHR){
                        removeFromList($('#artikelId').val());
			alert('Artikel deleted successfully');
                        newArtikel();
                        
                },
		error: function(jqXHR, textStatus, errorThrown){
			alert('deleteArt error');
		}
	});
}

function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	let list = data === null ? [] : (data instanceof Array ? data : [data]);

	$('#artikelList li').remove();
	$.each(list, function(index, artikel) {
		addToList(artikel);//$('#artikelList').append('<li><a href="#" data-identity="' + artikel.idartikel + '">'+artikel.naam+'</a></li>');
	});
}
function addToList(artikelData) {
    
    $('#artikelList').append('<li><a href="#" data-identity="' + artikelData.idartikel + '">'+artikelData.naam+'</a></li>');
}
function removeFromList(artikelId) {
    console.log('remove from list ' + artikelId);
  
   $("#artikelList a[data-identity=" + artikelId + "]").remove(); 
    }
function renderDetails(artikel) {
	$('#artikelId').val(artikel.idartikel);
	$('#name').val(artikel.naam);
	$('#prijs').val(artikel.prijs);
//	$('#country').val(wine.country);
//	$('#region').val(wine.region);Id).remove();
//	$('#year').val(wine.year);
//	$('#pic').attr('src', 'pics/' + wine.picture);
	$('#description').val(artikel.omschrijving);
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
	var artikelId = $('#artikelId').val();
	return JSON.stringify({
		"idartikel": artikelId === "" ? null : artikelId, 
		"naam": $('#name').val(), 
		"prijs": $('#prijs').val(),
		"omschrijving": $('#description').val()
		});
}
