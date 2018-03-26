function goToRegister() {
	window.location.href = "/register.html";
}
function logout() {
	window.location.href = "/logout.html";
}
function goToForgotPassword() {
	window.location.href = "/forgotPassword.html";
}

function goToLogin() {
	window.location.href = "/login.html";
}
function resetPassword() {
	var email = $('#InputEmailFirst').val();
	$.ajax({
		url : '/sendToken?email=' + email,
		dataType : 'json',
		success : function(data) {
			var error = data.error;
			if (error != null) {
				$('.serverResponse').html(data.message + ", error=" + error);
			} else {
				$('.serverResponse').html(data.message);
			}

		},
	});
	// $(".forgotPassWordServerResponse").load("/resetPassword?email="+email+"");
}
function confirmResetPassword() {
	var newPassword = $("#newPassword").val();
	var confirmedPassword = $("#confirmNewPassword").val();

	$("#error").html("");
	if (newPassword != confirmedPassword) {
		$("#error").show();
	} else {
		$.ajax({
			url : '/user/savePassword?newPassword=' + newPassword,
			type : 'get',
			dataType : 'json',
			success : function(data) {
				var error = data.error;
				if (error != null) {
					$('.serverResponse')
							.html(data.message + ", error=" + error);
				} else {
					$('.serverResponse').html(data.message);
				}

				setTimeout(function() {
					window.location.href = "/login.html";
				}, 2000);
			},
		});
	}
}

function uploadPicture()
{
	alert("upload picture function called");
	var form_data = new FormData();
	var file_data = $("#imageFile").prop("files")[0]; 
	form_data.append("file", file_data);
	
	var token=$('#csrfToken').val();
	
	alert("token inserted is "+token);
	$.ajaxPrefilter(function(options) {
		alert("Prefilter");
	    if (options.type === 'POST') {
	        options.data = options.data || {};
	        options.data['csrf-token'] = token;
	        alert("token inserted is "+token);
	    }
	});
	
	
	 $.ajax({
		    url: '/user/uploadImage',
		    data: form_data,
		    dataType: 'text',
		    processData: false,
		    contentType: false,
		    cache: false,
		    type: 'post',
		    success: function(data){
		    	alert(data);
		      $('.imageHolder').html('<img class="img-responsive" src="/user/getImage/'+data+'" />');
		    }
		  });
	
	
}

// $(document).ready(function() {
// 	$('#register_password').pwstrength({
// 		ui : {
// 			showVerdictsInsideProgressBar : true
// 		}
// 	});
// 	$('.progress').hide();
// 	$('#register_password').focus(function() {
// 		$('.progress').show();
// 	});
//
// 	$('video,audio').mediaelementplayer(/* Options */);
// });

function getAllTitles(type)
{
    hidemypopover();
	var currentPage=$(".currentPage").val();
	if(currentPage===undefined)
	{
		currentPage=0;
	}
	var type=type;
	//var genre=$('#selGenre').children(':selected').text();
	var genre=$('.genreName').val();
	if(genre==='notSelected')
	{
		genre=null;
	}
	var perPage=$('#perPage').children(':selected').text();
	if(perPage=="")
		perPage=12;
	var url="GetTitles?type="+type+"&page="+currentPage+"&perPage="+perPage+"&genre="+genre;
	$('.noInsRowsContainer').html('');
	var wrapper = $('.rowsContainer');
	wrapper.load(url,function(responseText, statusText, xhr){
		if(statusText == "success")
		{
			wrapper.children('.rowsContainer').unwrap();

				//prikazujemo dugmice za navigaciju
				if(currentPage==="0")
				{
					$(".buttonPrevious").css({"visibility":"hidden"});
				}
				else
				{
					$(".buttonPrevious").css({"visibility":"visible"});
				}
		}
	});
}

function getNotInserted()
{
    hidemypopover();
	var currentPage=$(".currentPage").val();
	if(currentPage===undefined)
	{
		currentPage=0;
	}

	var perPage=$('#perPage').children(':selected').text();
	if(perPage=="")
		perPage=12;
	var url = 'GetNotInserted';
	var wrapper = $('.noInsRowsContainer');
	wrapper.load(url,function(responseText, statusText, xhr){
		if(statusText == "success")
		{
			$('.rowsContainer').html('');
			wrapper.children('.noInsRowsContainer').unwrap();

			//prikazujemo dugmice za navigaciju
			if(currentPage==="0")
			{
				$(".buttonPrevious").css({"visibility":"hidden"});
			}
			else
			{
				$(".buttonPrevious").css({"visibility":"visible"});
			}
		}
	});
}

function getNext()
{
	//alert("getNext pozvana");
	var previousNum=parseInt($(".currentPage").val());
	var perPage=parseInt($('#perPage').children(':selected').text());
	var pageToGet=previousNum+perPage;
	var currentPage=$(".currentPage").val(pageToGet+'');
	getAllTitles();
}

function getPrevious()
{
	//alert("getNext pozvana");
	var previousNum=parseInt($(".currentPage").val());
	var perPage=parseInt($('#perPage').children(':selected').text());
	var pageToGet=previousNum-perPage;
	var currentPage=$(".currentPage").val(pageToGet+'');
	getAllTitles();
}


function getNextAdvanced()
{
	//alert("getNext pozvana");
	var previousNum=parseInt($(".currentPage").val());
	var perPage=parseInt($('#perPage').children(':selected').text());
	var pageToGet=previousNum+perPage;
	var currentPage=$(".currentPage").val(pageToGet+'');
	getAllTitles();
}

function getPreviousAdvanced()
{
	//alert("getNext pozvana");
	var previousNum=parseInt($(".currentPage").val());
	var perPage=parseInt($('#perPage').children(':selected').text());
	var pageToGet=previousNum-perPage;
	var currentPage=$(".currentPage").val(pageToGet+'');
	getAllTitles();
}



function getNextNotInserted()
{
	//alert("getNext pozvana");
	var previousNum=parseInt($(".currentPage").val());
	var perPage=parseInt($('#perPage').children(':selected').text());
	var pageToGet=previousNum+perPage;
	var currentPage=$(".currentPage").val(pageToGet+'');
	getNotInserted();
}

function getPreviousNotInserted()
{
	//alert("getNext pozvana");
	var previousNum=parseInt($(".currentPage").val());
	var perPage=parseInt($('#perPage').children(':selected').text());
	var pageToGet=previousNum-perPage;
	var currentPage=$(".currentPage").val(pageToGet+'');
	getNotInserted();
}



function capitaliseFirstLetterHere(string)
{
	return string.charAt(0).toUpperCase() + string.slice(1);
}

function goForMovies()
{
    $('.noInsRowsContainer').html('');
	var currentPage=$(".currentPage").val(0+'');

	//prikazujemo dugmice za navigaciju
	if(currentPage==="0")
	{
		$(".buttonPrevious").css({"visibility":"hidden"});
	}
	else
	{
		$(".buttonPrevious").css({"visibility":"visible"});
	}
	getAllTitles('movie');
}

function goForSeries()
{
    $('.noInsRowsContainer').html('');
	var currentPage=$(".currentPage").val(0+'');

	if(currentPage==="0")
	{
		$(".buttonPrevious").css({"visibility":"hidden"});
	}
	else
	{
		$(".buttonPrevious").css({"visibility":"visible"});
	}
	getAllTitles('series');
}
function goForGames()
{
    $('.noInsRowsContainer').html('');
	var currentPage=$(".currentPage").val(0+'');

	if(currentPage==="0")
	{
		$(".buttonPrevious").css({"visibility":"hidden"});
	}
	else
	{
		$(".buttonPrevious").css({"visibility":"visible"});
	}
	getAllTitles('game');
}

function getPopOver(id,isNotInserted, isMustWatch, isFavorite, isIncorrect)
{
    hidemypopover();


	$('.IdOFSelectedTitle').val(id);

	var favoriteButton='';
	var mustWatchButton='';
	var incorrectButton='';


	if(isMustWatch === 0){
		mustWatchButton = '<button type="button" class="btn btn-xs btn-primary" style="width:150px;margin:12px;margin-top:0px" onclick="addToMustWatch('+isNotInserted+')">Add to must watch</button>';
	}
	else{
		mustWatchButton = '<button type="button" class="btn btn-xs btn-primary" style="width:150px;margin:12px;margin-top:0px" onclick="addToMustWatch('+isNotInserted+')">Remove from must watch</button>'
	}

	if(isFavorite === 0){
		favoriteButton = '<button type="button" class="btn btn-xs btn-primary" style="width:150px;margin:12px;margin-top:0px" onclick="addToFavorites('+isNotInserted+')">Add to favorites</button>';
	}
	else{
		favoriteButton = '<button type="button" class="btn btn-xs btn-primary" style="width:150px;margin:12px;margin-top:0px" onclick="addToFavorites('+isNotInserted+')">Remove from favorites</button>';
	}

	if(isIncorrect === 0){
		incorrectButton = '<button type="button" class="btn btn-xs btn-primary" style="width:150px;margin:12px;margin-top:0px" onclick="addToIncorrect('+isNotInserted+')">Add to Incorrect</button>';
	}
	else{
		incorrectButton = '<button type="button" class="btn btn-xs btn-primary" style="width:150px;margin:12px;margin-top:0px" onclick="addToIncorrect('+isNotInserted+')">Remove from Incorrect</button>';
	}



//	alert('must watch: '+isMustWatch+', favorite: '+isFavorite+', incorrect: '+isIncorrect);

//alert($('.IdOFSelectedTitle').val());
	$('#clickForTooltip'+id).popover({
		offset: 10,
		trigger: 'manual',
		html: true,
		placement: 'right',
		container: 'body',
		title: '<b><i>Title options</i></b>',
		template: '<div class="popover"  ><div class="arrow" style="top:20px;"></div><div class="popover-inner" style="width:170px;height:200px"><h3 class="popover-title"><b>File location</b></h3><div class="content">'+
		'<button type="button" class="btn btn-xs btn-danger" style="width:150px;margin:12px" onclick="deleteFromDatabase('+isNotInserted+')">Delete</button>'+
		favoriteButton+//'<button type="button" class="btn btn-xs btn-primary" style="width:107px;margin:12px;margin-top:0px" onclick="addToFavorites('+isNotInserted+')">Add to favorites</button>'+
		mustWatchButton+//'<button type="button" class="btn btn-xs btn-primary" style="width:107px;margin:12px;margin-top:0px" onclick="addToMustWatch('+isNotInserted+')">Add to must watch</button>'+
		incorrectButton//'<button type="button" class="btn btn-xs btn-primary" style="width:107px;margin:12px;margin-top:0px" onclick="addToIncorrect('+isNotInserted+')">Incorrect</button>'+
		+' </div></div></div>'
	}).popover('toggle');



    /*$('#clickForTooltip'+id).click(function() {
        if($(this).hasClass('pop')) {
            $(this)
                .popover('hide')
                .removeClass('pop');
        } else {
            $(this)
                .popover('show');
        }
    });;*///.mouseenter(function(e) {
		// alert("onmouseenter"+id);
		//$(this).popover('show');
		//alert($(this).siblings().text());
		//$("#optionToBeAdded").text($(this).siblings().text());
//alert();
    /*}).mouseleave(function(e) {
	 var _this = this;
	 setTimeout(function() {
	 if (!$(".popover:hover").length) {
	 $(_this).popover("hide");
	 }
	 }, 100);
	 })*/
}
function hidemypopover()
{
	$(".popover").remove();
}
function showInfo(idFilm)
{
	/*$('#modaTitleInfo').modal('show');*/
	$('.IdOFSelectedTitle').val(idFilm);
	var url='ShowInfo?IDtitle='+idFilm;
	var wrapper = $(".modalTitleInfoContainer");
	wrapper.load(url,function(responseText, statusText, xhr){
		if(statusText == "success")
		{
			wrapper.children('.modalTitleInfoContainer').unwrap();
			$('#modaTitleInfo').modal('show');
		}
	});
}

function editTitleInfo(idFilm, notInserted)
{
	//za slucaj da se do editModal-a doslo preko titleInfoModal-a
	$('#modaTitleInfo').modal('hide');

	var url="ShowInfo?IDtitle=";
	if(notInserted===false) {
		url = "/EditTitle?IDtitle=";
	}
	else
	{
		url="EditInfoNotInserted?IDtitle=";
	}
	var wrapper;
	var wrapperChild;

	if(notInserted===true)
	{
		wrapper = $('.modalEditNoInsTitleInfoContainer');
	}
	else
	{
		wrapper = $('.modalEditTitleInfoContainer');
	}
	wrapper.load(url+idFilm, function(responseText, statusText, xhr){
		if(statusText == "success")
		{
			if(notInserted===true)
			{
				wrapper.children('.modalEditNoInsTitleInfoContainer').unwrap();
			}
			else
			{
				wrapper.children('.modalEditTitleInfoContainer').unwrap();
			}
			//$('#editType').val(type).prop('selected', true);
			$('.editImgUrl').on('keyup',function(){
				var newImageSrc=$('.editImgUrl').val();
				$('.imageContainer').html('<img class="imageLink" src="'+newImageSrc+'">');
			});
			if(notInserted===true)
			{
				$('#modalEditNotInsertedInfo').modal('show');
			}
			else
			{
				$('#modaEditInfo').modal('show');
			}

		}
	});


	//dovlacimo podatke za imdb-a za uneti id


	/*$.ajax({
	 contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	 dataType: "json",
	 url: "ShowInfo?IDtitle="+idFilm,
	 success: function (server_response)
	 {
	 $('.imageContainer').html('<img class="imageLink" src="'+server_response.picture+'">');
	 $('.titleName').html('<b>'+capitaliseFirstLetter(server_response.imdbName)+'</b>');
	 $('.titleYear').html(server_response.year);
	 $('.titleQuality').html(server_response.quality);
	 $('.titleGenre').html(server_response.genre);
	 $('.titlePlot').html(server_response.plot);
	 $('.titleCast').html(server_response.actors);
	 $('.titleLocation').html(server_response.location);
	 $('#modaTitleInfo').modal('show');
	 }

	 });*/
}

function changeNumPerPage()
{

	//alert($('#perPage').children(':selected').text());
	getAllTitles();
}

function updateProgress(){
	var result = '';

	$.ajax({
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		async: false,
		type: "POST",
		url: "getResponseStatus",
		success: function (server_response)
		{
			result = server_response;
			if( result != 'done' ){
				$('.progress-bar').width(result+'%');
				$('.progress-bar').html(result+'%');
			}
		}
	}).done(function() {
		//return result;
	});
	if( result != 'done' ){
		setTimeout(updateProgress, 2000);
	}else{
		$('.progress-bar').width('100%');
		$('.progress-bar').html('100%');
	}

}

function synchonizationCalled()
{
	$('#modalSync').modal('show');
	$.ajax({
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		type: "POST",
		url: "Synchronize",
		success: function (server_response)
		{
			$('#modalSync').modal('hide');
			getAllTitles('All');
		}

	}).done(function() {
		$('#modalSync').modal('hide');
		getAllTitles('All');
	});/**/

	//check for progress update
	updateProgress();

}
function deleteFromDatabase(isNotInserted)
{
	hidemypopover();
	/* if(id===undefined)
	 {
	 id=$(".IdOFSelectedTitle").val();
	 }*/
	var url;
	var id=$(".IdOFSelectedTitle").val();
	if(isNotInserted===false)
	{
		url="DeleteTitle";

	}
	else
	{
		url="DeleteTitleFromNotInserted";
	}
	$.ajax({
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		type: "POST",
		url: url+"?idTitle="+id,
		success: function (server_response)
		{
			$('#modaTitleInfo').modal('hide');
			getAllTitles('All');
            alert('Title deleted');
		}
	});
}
function addToFavorites(isNotInserted)
{
	hidemypopover();
	var url;
	var id=$(".IdOFSelectedTitle").val();
	if(isNotInserted===false)
	{
		url="AddToFavorites";
	}
	$.ajax({
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		type: "POST",
		url: url+"?idTitle="+id,
		success: function (server_response)
		{

			alert("Added to favorites");
			//getAllTitles('All');
		}
	});
}

function addToMustWatch(id)
{
	hidemypopover();
	/* if(id===undefined)
	 {
	 id=$(".IdOFSelectedTitle").val();
	 }*/
	var url;
	var id=$(".IdOFSelectedTitle").val();

	$.ajax({
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		type: "POST",
		url: "AddToMustWatch?idTitle="+id,
		success: function (server_response)
		{
			alert("Added to must watch");
			//getAllTitles('All');
		}
	});
}

function addToIncorrect(id)
{
	hidemypopover();
	/*if(id===undefined)
	 {
	 id=$(".IdOFSelectedTitle").val();
	 }*/
	var id=$(".IdOFSelectedTitle").val();
	$.ajax({
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		type: "POST",
		url: "AddToIncorrect?idTitle="+id,
		success: function (server_response)
		{
			alert(server_response);
			//getAllTitles('All');
		}
	});
}
function fetchFromIMDB()
{
	var id=$(".imdbID").val();
	$.ajax({
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		dataType: "json",
		url: "FetchFromIMDB?IMDBID="+id,
		success: function (server_response)
		{

			var image=server_response.Poster;
			var name=capitaliseFirstLetter(server_response.Title);
			var year=server_response.Year;
			var genre=server_response.Genre;
			var plot=server_response.Plot;
			var actors=server_response.Actors;
            var genreArray = genre.split(", ");

			$('.imageContainer').html('<img class="imageLink" src="'+image+'">');
			$('.editImgUrl').val(image);
			$('.editName').val(name);
			$('.editYear').val(year);


           // $('.editGenre option').each(function(){
               //var option_val = this.text;
               for(var i in genreArray) {
                   // if(genreArray[i] == option_val){
                   $(".editGenre option[text='" + genreArray[i] + "']").prop("selected", "selected");

                   //}
               }
           // });

			//$('.editGenre').val(genreArray);

			$('.editPlot').val(plot);
			$('.editActors').val(actors);
		}
	});
}
function saveChanges(idTitle, isNotInserted)
{
	var imgUrl=$('.editImgUrl').val();
	var location=$('.editLocation').val();
	var actors=$('.editActors').val();
	var plot=$('.editPlot').val();
	var genre=$('.editGenre').val();
	var quality=$('.editQuality').val();
	var year=$('.editYear').val();
	var name=$('.editName').val();
	var type=$('#editType option:selected').val();
	var imdbLink="http://www.imdb.com/title/"+$('.imdbID').val()+"/?ref_=fn_al_tt_1";
	//alert(year);
	//alert("image:"+imgUrl+" location:"+location+" actors:"+actors+" plot:"+plot+" genre:"+genre+" quality:"+quality+" year:"+year+" name:"+name);

	var url;
	if(isNotInserted===false)
	{
		url="SaveChanges?";
	}
	else
	{
		url="SaveChangesForNotInserted?IMDBlink="+imdbLink+"&"
	}

	url = url +"IDfilm="+idTitle+"&picture="+encodeURIComponent(imgUrl)+"&location="+encodeURIComponent(location)+"&actors="+encodeURIComponent(actors)+"&description="+encodeURIComponent(plot)+"&genre="+genre+"&quality="+quality+"&year="+year+"&rawName="+encodeURIComponent(name)+"&imdbTitle="+encodeURIComponent(name)+"&IDtype="+type;
	$.ajax({
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		type: "POST",
		url: url,
		success: function (server_response)
		{
			alert('Title changed!');
			$('#modaEditInfo').modal('hide');
		}

	});
}
function showFavorites()
{
    hidemypopover();

	$(".currentPage").val(0+'');
	var currentPage=$(".currentPage").val();
	if(currentPage===undefined)
	{
		currentPage=0;
	}
	var perPage=$('#perPage').children(':selected').text();
	var url =  "GetTitles?type=favorite&page="+currentPage+"&perPage="+perPage;
	var wrapper = $('.rowsContainer');
	wrapper.load(url,function(responseText, statusText, xhr){
		if(statusText == "success")
		{
			wrapper.children('.rowsContainer').unwrap();

			//prikazujemo dugmice za navigaciju
			if(currentPage==="0")
			{
				$(".buttonPrevious").css({"visibility":"hidden"});
			}
			else
			{
				$(".buttonPrevious").css({"visibility":"visible"});
			}
		}
	});
}


function showMustWatch()
{
    hidemypopover();

	$(".currentPage").val(0+'');
	var currentPage=$(".currentPage").val();
	if(currentPage===undefined)
	{
		currentPage=0;
	}
	var perPage=$('#perPage').children(':selected').text();
	var url =  "GetTitles?type=mustWatch&page="+currentPage+"&perPage="+perPage;
	var wrapper = $('.rowsContainer');
	wrapper.load(url,function(responseText, statusText, xhr){
		if(statusText == "success")
		{
			wrapper.children('.rowsContainer').unwrap();
			//prikazujemo dugmice za navigaciju
			if(currentPage==="0")
			{
				$(".buttonPrevious").css({"visibility":"hidden"});
			}
			else
			{
				$(".buttonPrevious").css({"visibility":"visible"});
			}
		}
	});
}



function play(ID)
{
	// alert(IDSite);
	$.ajax({
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		type: "GET",
		url: "PlayFile?idTitle="+ID,
	});
}
function openFolder(id)
{

	$.ajax({
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		type: "GET",
		url: "OpenFolder?idTitle="+id,
	});

	/*var location="file://///" + locationOnDrive.replace(/\s/g,'%20');

	alert('trying to open: '+location);

	var x =this.pageX -$(document).scrollLeft();
	var y =this.pageY -$(document).scrollTop();

	$("#dialog").dialog( "option", "position", [x,y]);
	$("#dialog").dialog('open');
	window.open(location);*/
	/*$.ajax({
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		type: "POST",
		url: "OpenFolder?IDFilm="+ID,
		success: function (server_response)
		{
			var location="file:///" + locationOnDrive;
			var x =this.pageX -$(document).scrollLeft();
			var y =this.pageY -$(document).scrollTop();

			$("#dialog").dialog( "option", "position", [x,y]);
			$("#dialog").dialog('open');
//});
			window.open(location);
		}
	});*/
}
function getTotalNumber()
{
	$.ajax({
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		type: "GET",
		url: "GetTotalNumber",
		success: function(server_response)
		{
			$(".totalFoundDiv").html("<b>Found "+server_response.totalFound+" titles in database</b>");
		}
	});
}
function openAdvancedSearch()
{
	//alert("js call");
	window.location.href = "openAdvancedSearch";

}

function openGeneralOptions(){
    window.location.href = "openGeneralOptions";
}

function goToAndShowForMustWatch()
{
	window.location.href = "/app";
	showMustWatch();
}

function goToAndShowForFavorites()
{
	window.location.href = "/app";
	showFavorites();
}

function goToAndShowForMovies()
{
	window.location.href = "/app";
	goForMovies();
}

function goToAndShowForSeries()
{
	window.location.href = "/app";
	goForSeries();
}

function goToAndShowForGames()
{
	window.location.href = "/app";
	goForGames();
}

function goToAndShowForNotInserted()
{
	window.location.href = "/app";
	getNotInserted();
}


function getAllCountResults()
{
	$.ajax({
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		dataType: "json",
		url: "GetCountResults",
		success: function (server_response) {
			fillSelectBoxes(server_response);
		}
	});
}

function fillSelectBoxes(server_response)
{
	$('.selectType').html('<label for="sel1"><b>Select type:</b></label>'+
		'<select class="form-control" id="selType" style="margin-top: 10px" onChange="getSpecificCounts()">'+
		'<option value="notSelected">select type</option>'+
		'<option value="Movie">Movie ('+server_response.movie+')</option>'+
		'<option value="Series">Series ('+server_response.series+')</option>'+
		'<option value="Game">Game ('+server_response.game+')</option>'+
		'<option value="Other">Other ('+server_response.other+')</option>'+
		'</select>');



	$('.selectGenre').html('<label for="sel1"><b>Select genre:</b></label>'+
		'<select class="form-control" id="selGenre" style="margin-top: 10px" onChange="getSpecificCounts()">'+
		'<option value="notSelected">select genre</option>'+
		'<option value="Comedy">Comedy ('+server_response.Comedy+')</option>'+
		'<option value="Crime">Crime ('+server_response.Crime+')</option>'+
		'<option value="Thriller">Thriller ('+server_response.Thriller+')</option>'+
		'<option value="Adventure">Adventure ('+server_response.Adventure+')</option>'+
		'<option value="Family">Family ('+server_response.Family+')</option>'+
		'<option value="Fantasy">Fantasy ('+server_response.Fantasy+')</option>'+
		'<option value="Biography">Biography ('+server_response.Biography+')</option>'+
		'<option value="History">History ('+server_response.History+')</option>'+
		'<option value="Adventure">Adventure ('+server_response.Adventure+')</option>'+
		'<option value="Romance">Romance ('+server_response.Romance+')</option>'+
		'<option value="Documentary">Documentary ('+server_response.Documentary+')</option>'+
		'<option value="War">War ('+server_response.War+')</option>'+
		'<option value="Mystery">Mystery ('+server_response.Mystery+')</option>'+
		'<option value="Drama">Drama ('+server_response.Drama+')</option>'+
		'<option value="FilmNoir">FilmNoir ('+server_response.FilmNoir+')</option>'+
		'<option value="Horror">Horror ('+server_response.Horror+')</option>'+
		'<option value="Animation">Animation ('+server_response.Animation+')</option>'+
		'<option value="Action">Action ('+server_response.Action+')</option>'+
		'<option value="SciFi">SciFi ('+server_response.SciFi+')</option>'+
		'</select>');

	var yearSelectBody='<label for="sel1"><b>Select year:</b></label>'+
		'<select class="form-control" id="selYear" style="margin-top: 10px" onChange="getSpecificCounts()">'+
		'<option value=NotSelected>select a year</option>';
	for(var i=1950; i<=2017;i++)
	{
		yearSelectBody=yearSelectBody+'<option value="'+i+'">'+i+'</option>';
	}
	yearSelectBody=yearSelectBody+'</select>';
	$('.selectYear').html(yearSelectBody);
}

function getSpecificCounts()
{
	var type=$('#selType').children(':selected').val();;
	var genre=$('#selGenre').children(':selected').val();
	var year=$('#selYear').children(':selected').val();

	$.ajax({
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		dataType: "json",
		url: "GetSpecificCounts?type="+type+"&genre="+genre,
		success: function (server_response) {

		}
	});
}

/*function getAllTitles(type)
 {
 var currentPage=$(".currentPage").val();
 if(currentPage===undefined)
 {
 currentPage=0;
 }
 var type=type;
 //var genre=$('#selGenre').children(':selected').text();
 var genre=$('.genreName').val();
 if(genre==='notSelected')
 {
 genre=null;
 }
 var perPage=$('#perPage').children(':selected').text();
 if(perPage=="")
 perPage=12;
 var url="GetTitles?type="+type+"&page="+currentPage+"&perPage="+perPage+"&genre="+genre;
 $('.noInsRowsContainer').html('');
 var wrapper = $('.rowsContainer');
 wrapper.load(url,function(responseText, statusText, xhr){
 if(statusText == "success")
 {
 wrapper.children('.rowsContainer').unwrap();
 getPopOver('0',false);
 $("[data-toggle=tooltip]").tooltip();
 //prikazujemo dugmice za navigaciju
 if(currentPage==="0")
 {
 $(".buttonPrevious").css({"visibility":"hidden"});
 }
 else
 {
 $(".buttonPrevious").css({"visibility":"visible"});
 }
 }
 });
 }*/
function advancedSearch()
{
    hidemypopover();
	var genre=$('#selGenre').children(':selected').val();
	$(".genreName").val(genre);
	var perPage=$('#perPage').children(':selected').text();
	var type=$('#selType').children(':selected').val();
	var year=$('#selYear').children(':selected').val();
	//window.location.href = "/app";
	//prikazujemo dugmice za navigaciju
	if(currentPage==="0")
	{
		$(".buttonPrevious").css({"visibility":"hidden"});
	}
	else
	{
		$(".buttonPrevious").css({"visibility":"visible"});
	}

	var currentPage=$(".currentPage").val();
	if(currentPage===undefined)
	{
		currentPage=0;
	}

	if(perPage=="")
		perPage=12;

	var url="GetTitles?type="+type+"&page="+currentPage+"&perPage="+perPage+"&genre="+genre+"&year="+year;
	$('.noInsRowsContainer').html('');
	var wrapper = $('.rowsContainer');
	wrapper.load(url,function(responseText, statusText, xhr){
		if(statusText == "success")
		{
			$('.advancedSearchDiv').css('display','none');
			wrapper.children('.rowsContainer').unwrap();
			//prikazujemo dugmice za navigaciju
			if(currentPage==="0")
			{
				$(".buttonPrevious").css({"visibility":"hidden"});
			}
			else
			{
				$(".buttonPrevious").css({"visibility":"visible"});
			}
		}
	});
}

function displayDuplicateInfo(id) {

	//alert('id is '+id);
	//duplicateOccurences

	$('.duplicateName').removeClass('selectedDuplicate');

	$('#'+id+'.duplicateName').addClass('selectedDuplicate');

	$('.duplicateOccurences').html('');
	var wrapper = $('.duplicateOccurences');
	var url="showDuplicateInfo/"+id;

	wrapper.load(url,function(responseText, statusText, xhr){
		if(statusText == "success")
		{
			//$('.advancedSearchDiv').css('display','none');
			wrapper.children('.duplicateOccurences').unwrap();

		}
	});
}

function escapeHtml(unsafe) {
	return unsafe
		.replace(/&/g, "\&");
}
