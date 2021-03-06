//function to jump to the bottom
function scrolltobottom(){
	$('html, body').animate({
		scrollTop: $(document).height()-$(window).height()},
		0,
		"easeOutQuint"
	);
}
// adds message to the chatMessages div
function addMessage(msg) {
	//gets username
	$loggedInUser = firebase.auth().currentUser.displayName;
	//formats the date from milliseconds to readable date and time
	$msgDate = moment(msg.date).format("DD/MM/YYYY hh:mm:ss");

	//if the message is send by the logged in user display as his message else display as anothers message
	if(msg.username == $loggedInUser){
		$newMessage = $([
			'<div class="row my_msg"><div class="col s12 m5 right"><div class="card-panel light-blue"><span class="theMessage white-text">'+msg.message+'</span><div class="width100 msg_sender_info"><span class="username_in_msg my_msg order2"><b>'+msg.username+'</b></span><span class="date_in_msg order1 white-text">'+$msgDate+'</span></div></div></div></div>'
		].join());
	}else{
		$newMessage = $([
			'<div class="row not_my_msg"><div class="col s12 m5"><div class="card-panel "><span class="theMessage">'+msg.message+'</span><div class="width100 msg_sender_info"><span class="username_in_msg"><b>'+msg.username+'</b></span><span class="date_in_msg">'+$msgDate+'</span></div></div></div></div>'
		].join());
	}

	$('#chatMessages').append($newMessage);
	scrolltobottom();

}

(function(){

	//click event listener
	$('#sendMsg').click( function(){

		//gets info to be stored in the database
		$datePosted = Date.now();
		$username = firebase.auth().currentUser.displayName;
		$message = $('#messageInput').val();

		//checks if message isn't empty
		if (!$message == ("" || " ")){
			//Puts the message into the database
			firebase.database().ref().child('messages').push({
				date: $datePosted,	
				username: $username,
				message: $message
			});

			console.log($username +"'s message was sent");

			//pushes message to the user aswell to get from friends.html
			firebase.database().ref().child('users').child($username).child('messages').push({
				message: $message
			});

			//Clears the input field after posting
			$('#messageInput').val('');
		} else {
			console.log("Messages can not be empty!");
		}
		
	});

	//Runs when user adds to database
	var checkForNewPosts = function() {
		//var now = moment();
		firebase.database().ref().child('messages').limitToFirst(20).on('child_added', function(snapshot) {
			$msg = snapshot.val();
			addMessage($msg)
			$('#loadingMessages').hide();
			
			if($msg.date){
				console.log("XD");
				$datetime = moment($msg.date);
				$user = firebase.auth().currentUser.displayName;
				$isAfter = $datetime.isAfter(moment());
				if($isAfter && ($user != $msg.username)) {
					if(Notification.permission !== 'default') {
						notify = new Notification('New Message', {
						'body': 'Someone just posted a message on Chatchat!',
						'tag': '1234'
						});
						notify.onclick = function() {
							window.location = '?message=' + this.tag;
						}
					} else {
						alert("Please click on request permission link before acessing this link!");
					}
				}
			}
			
		});


	};

	//Begin checking for posts for data
	checkForNewPosts()

	//Sends user to feed if logged inn
	firebase.auth().onAuthStateChanged(firebaseUser => {
		if (!firebaseUser) {
		document.location.href = "index.html";
		}
	});

}());

// Asks premission to use notifications on click
var dnperm = document.getElementById("dnperm");
dnperm.addEventListener('click', function(e) {
	e.preventDefault();
	if(!window.Notification) {
		alert("Notification is not supported!");
	} else {
		Notification.requestPermission().then(function(p) {
			if(p === 'denied') {
				alert("You denied to show notifications!");
			} else {
				alert("You accepted to get notifications!");
			}
		});
	}
});