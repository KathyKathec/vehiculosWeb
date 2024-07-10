

/*
  $(".dropdown-item").click(function(e) {
    e.preventDefault();
    var categoria = $(this).data("categoria");
    // setamos o request para o nome da categoria clicada
    var url = new URL(window.location.href);
    url.searchParams.set("categoria", categoria);
    window.location.href = url.toString();
});
*/
$(document).ready(function() {

    // Handle form submission
    $('#searchModelo').submit(function(event) {
      event.preventDefault(); // Prevent default form submission
  
      // Get the search value, trimming any leading/trailing spaces
      const modelo = $('#modelo').val().trim();
  console.log(modelo.toString());
  console.log("idiot2");
      // Redirect to the server-side URL with both modelo and potentially categoria
      var url = new URL(window.location.href);
      url.searchParams.set("modelo", modelo);
  console.log(url.toString());
  console.log("idiot3");

      
  
      window.location.href = url.toString();
    });
  
    
  });
  $(document).ready(function() {
    // Handle form submission
    $('#searchAno').submit(function(event) {
        event.preventDefault(); // Prevent default form submission
        
        // Get the search value, trimming any leading/trailing spaces
        const ano = $('#ano').val().trim();
        
        // Construct the URL with the year parameter
        var url = "/admin/ano/" + ano;
        
        // Redirect to the server-side URL with the year
        window.location.href = url;
    });
});