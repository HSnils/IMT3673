
(function(){

	//Gets elements
	$signinButton = $('#sign_in');

	//click event listener
	$signinButton.click(function(){
		firebase.auth().signInAnonymously();
	});

	//Sends user to register if logged inn
	firebase.auth().onAuthStateChanged(function(user) {
		if (user) {
			document.location.href = "register.html";
		}else {
			console.log('not logged inn');
		}
	});

	//Auth listener
	firebase.auth().onAuthStateChanged(firebaseUser => {
		console.log(firebaseUser);
	});

})();