let app = new Vue({
	el: "#app",

	data: {
		accounts: {},
		transactions: [],
		owner: "",
		switchNightMode: "",
		indexAccount: 0,
		accountSelected: "",
		selectCuenta: "propia",
		ownAccount: "select",
		anotherAccount: "VIN-",
		amount: "",
		description: "",
		switchForm: false,
		destinationAccountOwner: ""
	},

	created() {
		this.loadData();
	},

	methods: {
		loadData() {

			axios.get('/api/clients/current')
				.then(function(response) {
					app.owner = response.data;
					app.accounts = response.data.accounts.sort((a, b) => a.id - b.id);
					app.switchNightMode = response.data.nightMode;
					app.accountSelected = app.accounts[app.indexAccount];
					app.transactions = app.accounts[app.indexAccount].transactions.sort((b, a) => a.id - b.id);
					app.amount = "";
					app.description = "";
					app.ownAccount = "select";
					app.anotherAccount = "VIN-";
					app.destinationAccountOwner = " ";
				}).finally(function() {
					console.log("nightMode=" + app.switchNightMode);
					nightMode();
					const preloaded = document.querySelector(".preloaded");
					preloaded.style.visibility = "hidden";
				}).catch(function(error) {
					window.location.href = "/web/index.html";
					Swal.fire("Error");
				});
		},
		showToggle() {
			const navMenu = document.querySelector(".nav-menu");
			navMenu.classList.toggle("invisible");
			navMenu.classList.toggle("opacity-0");
		},
		showUser() {
			const navMenuUser = document.querySelector(".nav-menu-user");
			navMenuUser.classList.toggle("invisible");
			navMenuUser.classList.toggle("opacity-0");
			navMenuUser.classList.toggle("h-0");
		},
		activeNightMode() {
			axios.patch("/api/nightmode")
				.then((response) => {
					this.loadData();
				})
		},
		getOwnerAccount() {
			axios.get("/api/accounts/" + this.anotherAccount).then(response => {
				app.destinationAccountOwner = "a " + response.data;
				this.loadData()
			})
		},

		addTransaction() {
			let destinationNumber;
			if (this.anotherAccount != "VIN-") {
				destinationNumber = this.anotherAccount;
			} else {
				destinationNumber = this.ownAccount;
			}
			if (app.amount == "" || app.description == "" || destinationNumber == "VIN-" || destinationNumber == "") {
				Swal.fire('Ingrese todos los datos solicitados');
			} else {
				axios.post('/api/transactions', "amount=" + app.amount + "&description=" + app.description + "&originNumber=" + app.accountSelected.number + "&destinationNumber=" + destinationNumber, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
					.then(response => {
						const audio = new Audio("./assets/sound.mp3");
						audio.play();
						this.loadData();
					}).catch(error => Swal.fire("Error"));
			}

		},
		signOut() {
			axios.post('/api/logout').then(response => window.location.href = "/web/index.html");
		},
		format(num) { return String(num).replace(/(?<!\..*)(\d)(?=(?:\d{3})+(?:\.|$))/g, '$1,'); },
	},
});
