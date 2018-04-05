(function(){
	$db = firebase.database();

	//creates random username
	function createRandomUsername() {
		return Math.random().toString(35).substr(5, 10);
	};

	$randomUsername = createRandomUsername();
	console.log($randomUsername);

	//Sets the username inputfield to contain the randomly created username as a suggestion
	$('#username').val($randomUsername);

//click listener
$('#sign_up').click(function() {

	$user_id = firebase.auth().currentUser.uid;
	$username = $('#username').val();

	//stores user to database if nickname is not taken
	firebase.database().ref().child('users').once('value', function(snapshot) {
		if (!snapshot.hasChild($username || $user_id)) {
			console.log('User created!');
			//updates user
			firebase.auth().currentUser.updateProfile({displayName: $username});
			
			//puts the new user into the database
			firebase.database().ref().child('users').child($username).set({
	           user_id: $user_id,
	           username: $username
	         });

		}
		else {
			alert("That user already exists");
			console.log('User with username: ' + $username + " already exists!")
		}
	});

	//When user has updated displayName send to feed
	if (firebase.auth().currentUser.displayName) {
		document.location.href = "chat.html";
	}
});

//Sends user to feed if logged inn
firebase.auth().onAuthStateChanged(function(loggedInUser) {
	if (!loggedInUser) {
		document.location.href = "index.html";

	} else if(firebase.auth().currentUser.displayName) {
		document.location.href = "chat.html";
	}
});

}());