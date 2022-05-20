let categories  = new window.multiplatform.com.handstandsam.shoppingapp.multiplatform.Something().getCategoryNames()

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