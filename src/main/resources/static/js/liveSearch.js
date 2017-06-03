$(document).ready(function() {  
    //alert("Upad");
	// Icon Click Focus
	$('div.icon').click(function(){
		$('input#search').focus();
	});

	// Live Search
	// On Search Submit and Get Results
	function search() {
		var query_value = $('input#search').val();
		$('b#search-string').html(query_value);
		if(query_value !== ''){
                    //alert("Uneto:"+query_value);
			$.ajax({
                                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "json",
				url: "QuickSearchResults?name="+query_value,
				success: function(server_response){
                                        var content=returnHtmlContent(server_response);
					$("ul#results").html(content);
                                        
                                        //menjamo border radius kad se predje strlicom preko polja
                                        $(".result").mouseover(function(){
                                            //alert("called");
                                           $(this).css({"border-radius": "10px;"});
                                        });
                                        $('.result').click(function(){
                                            var idFilm=$(this).children(":first").attr("id");
                                            //alert("Kliknuto");
                                             showInfo(idFilm);   
                                            });
				}
			});
                        
                        /**/
		}

            return false;    
	}

	$("input#search").on("keyup", function(e) {
		// Set Timeout
		clearTimeout($.data(this, 'timer'));

		// Set Search String
		var search_string = $(this).val();

		// Do Search
		if (search_string == '') {
			$("ul#results").fadeOut();
			$('h4#results-text').fadeOut();
		}else{
			$("ul#results").fadeIn();
			$('h4#results-text').fadeIn();
			$(this).data('timer', setTimeout(search, 100));
		};
	});

});
function returnHtmlContent(json)
{
    var i=0;
    var contentHtml=''
    /*<li class="result"><a target="_blank" href="http://php.net/manual-lookup.php?pattern=soundex&amp;lang=en"><h3>So<b class="highlight">Und</b>ex</h3><h4>so<b class="highlight">und</b>ex</h4></a></li>*/
   for(i=0;i<json.length;i++)
   {
	   var entry=json[i];
        contentHtml+='<li class="result ">';
        contentHtml+='<h3 id='+json[i][0]+'><b>'+capitaliseFirstLetter(json[i][1])+'</b></h3>';
        contentHtml+='</li>';
   }
        return contentHtml;
}
function capitaliseFirstLetter(string)
{
    return string.charAt(0).toUpperCase() + string.slice(1);
}