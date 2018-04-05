(function(){

	// Runs when user adds to database
	var chechForUpdates = function() {
		
		firebase.database().ref().child('users').orderByChild('date').on('child_added', function(snapshot) {
			$loggedInUser = firebase.auth().currentUser.displayName;
	
			$user = snapshot.val();
			$messages = $user.messages;

			$('#loadingMessages').hide();

			$userWithMsg = document.createElement("div");
			$userWithMsg.className = "card-panel";
			$text = document.createElement("b");
			$text.className = "flex100 friendsUsername";
			$text.textContent = $user.username;

			$userWithMsg.appendChild($text);

		

			$userMessages = document.createElement("div");
			$userMessages.className ="userMessages hidden";

			Object.entries($messages).forEach(([key, value]) => {

				$showUserMessage = document.createElement('div');
				$showUserMessage.innerHTML = '- '+value.message;
				$showUserMessage.className = "friendsListMessage";

				$userMessages.appendChild($showUserMessage);
			});

			$userWithMsg.appendChild($userMessages);

			

			$userWithMsg.addEventListener("click", (event) => {
				console.log("click");
				var childlist = event.target.getElementsByClassName("userMessages")[0];
				var textList = event.target.getElementsByClassName("friendsUsername")[0];
				
				childlist.className = "userMessages openUser";
				textList.className = "borderUnder";
				
			})

			/*
			tried to create a function to close the opened users
			$('.openUser').click(function(){
				$('.openUser').removeClass('openUser').addClass('hidden');
				$('.borderUnder').removeClass('borderUnder');
			})*/

			$('#friendsBox').append($userWithMsg);

		});
	}

	//Loogs for updats to the database
	chechForUpdates();

	//Sends user to feed if logged inn
	firebase.auth().onAuthStateChanged(firebaseUser => {
		if (!firebaseUser) {
		document.location.href = "index.html";
		}
	});

}());