$(function(){
  $("#province").change(function(){
      var city=$("#city");
      city.empty();
      city.append(" <option value= '0' >--</option>");
      if(this.value != 0){
          requestRegion(2,this.value,city);
      }
  });
  $("#city").change(function(){
      var district=$("#district");
      district.empty();
      district.append(" <option value= '0' >--</option>");
      if(this.value != 0){
         requestRegion(3,this.value,district);
     }
  });
});
function requestRegion(level,parentId,obj){
	  $.get("../rest/getRegion.do?level="+level+"&parentId="+parentId,null,
	  function(msg){
	   msg=msg.result;
	   for(var i = 0 ;i< msg.length ; i++){
		  $(obj).append(" <option value= "+msg[i].id+"> "+ msg[i].name +" </option>");
	   }
	});
}
