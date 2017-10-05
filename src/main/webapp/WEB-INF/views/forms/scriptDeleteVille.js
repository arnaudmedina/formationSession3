<script>
  $(document).ready(function(){
    $('#DeleteButton').click(function(){
    	$.ajax({
    		  url: 'http://localhost:9093/form?mode=MOD&id=valIdVille',
    		  method: 'DELETE'
    		})
    		  .done(function( data ) {
    		    console.log(data);
    		  });
    });
  });
</script>