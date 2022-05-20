//let categories  = new window.multiplatform.com.handstandsam.shoppingapp.multiplatform.Something().getCategoryNames()

const jsMultiplatformApi  = new window.multiplatform.com.handstandsam.shoppingapp.multiplatform.JsMultiplatformApi()


let showCategories = function(categories) {
    console.log("show Categories" )
    console.log(categories)

    let items = $( "#items" );
    $.each(categories, function( index, value ) {
      let newItem = $( "#item" ).clone();
      newItem.show();
      newItem.addClass("item")
      let img = newItem.find("img")
      img.attr("src",value.image);
      let text = newItem.find("p")
      text.text(value.label);
      newItem.appendTo( items );
      console.log( index + ": " + value );
    });
}


jsMultiplatformApi.getCategoriesAsync().then(function(result) {
 console.log("SUCCESS")

 $("#loading").hide();
 showCategories(result)
},
function(error) {
 console.log("ERROR")
 console.log(error)
});
