// add message function
function addMessage(msg) {
	$loggedInUser = firebase.auth().currentUser.displayName;
	if(msg.username == $loggedInUser){
		$newMessage = $([
			'<div class="row"><div class="col s12 m5 right"><div class="card-panel light-blue"><span class="theMessage white-text">'+msg.message+'</span><div class="width100 msg_sender_info"><span class="username_in_msg order2">'+msg.username+'</span><span class="date_in_msg order1 white-text">'+moment(msg.date).format('MMMM Do YYYY, h:mm:ss a')+'</span></div></div></div></div>'
		].join());
	}else{
		$newMessage = $([
			'<div class="row"><div class="col s12 m5"><div class="card-panel "><span class="theMessage">'+msg.message+'</span><div class="width100 msg_sender_info"><span class="username_in_msg">'+msg.username+'</span><span class="date_in_msg">'+moment(msg.date).format('MMMM Do YYYY, h:mm:ss a')+'</span></div></div></div></div>'
		].join());
	}

	$('#chatMessages').append($newMessage);

}

(function(){

	//click event listener
	$('#sendMsg').click( function(){

		//gets info to be stored in the database
		$datePosted = new Date();
		$username = firebase.auth().currentUser.displayName;
		$message = $('#messageInput').val();

		//Puts the message into the database
		firebase.database().ref().child('messages').push({
			date: $datePosted,	
			username: $username,
			message: $message
		});

		console.log('message sent');
		/*//Pushes users data to database
		firebase.database().ref().child('users').child(username).child('messages').push({
		message: message
		});*/

		//Clears the input field after posting
		$('#messageInput').val('');
	});

// Runs when user adds to database
var startListening = function() {
	//var now = moment();
	firebase.database().ref().child('messages').on('child_added', function(snapshot) {
		var msg = snapshot.val();
		//console.log(msg);
		addMessage(msg)

	});
}

// Begin listening for data
startListening();


//Sends user to feed if logged inn
firebase.auth().onAuthStateChanged(firebaseUser => {
if (!firebaseUser) {
document.location.href = "index.html";
}
else {
console.log('logged');
}

});

//Auth listener
firebase.auth().onAuthStateChanged(firebaseUser => {
console.log(firebaseUser);
});

}());