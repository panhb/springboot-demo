var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue!'
  }
})

var vm = new Vue({
  el: '#example',
  data: {
    message: 'Hello Vue SB!'
  },
  computed: {
	  reversedMessage : function(){
		  return this.message.split('').reverse().join('');
	  }
  }
})

axios.get('/user/index').then(function(response){
  alert(response.data.list[0].createDate)
})


