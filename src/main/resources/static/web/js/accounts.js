let app = new Vue({
	el: "#app",

	data: {
		client: "",
		accounts: "",
		accountType: "CURRENT",
		cards: "",
		loans: "",
		selectCard: "all",
		arrayShowCard: [],
		switchAccount: false,
		switchNightMode: ""
	},

	created() {

		this.loadData();
	},

	methods: {
		loadData() {
			axios.get('/api/clients/current').then(function(response) {
				console.log("nightMode=" + response.data.nightMode);
				app.client = response.data;
				app.accounts = response.data.accounts.sort((a, b) => a.id - b.id);
				app.loans = response.data.loans.sort((a, b) => a.id - b.id);
				app.cards = response.data.cards.sort((a, b) => a.id - b.id);
				app.switchNightMode = response.data.nightMode;
			}).finally(function() {
				if (app.accounts.length < 1) {
					app.createAccount();
					if (window.matchMedia &&
						window.matchMedia('(prefers-color-scheme: dark)').matches) {
						app.activeNightMode();
					}
				}
				nightMode();
				const preloaded = document.querySelector(".preloaded");
				preloaded.style.visibility = "hidden";
			});
		},
		showAccounts() {
			this.switchAccount = !this.switchAccount;
		},
		createAccount() {
			axios.post('/api/clients/current/accounts', { headers: { 'content-type': 'application/x-www-form-urlencoded' } }).then(response => app.loadData(), Swal.fire('Cuenta creada con exito'))
		},
		deleteAccount(id) {
			axios.patch('/api/accounts/deleteaccount', "id=" + id).then(response => app.loadData()).catch(Error => Swal.fire(Error.response.data));
		},
		addType(id) {
			axios.patch('/api/accounts/addtype', "id=" + id + "&type=" + app.accountType).then(response => app.loadData(), Swal.fire('El tipo de cuenta ha sido asignado con exito')).catch(error => Swal.fire(error));
		},
		showCard(index) {
			const cards = document.querySelector(".set-card");
			cards.style.visibility = "hidden";
			this.arrayShowCard.push(this.cards[index]);
		},
		hideCard() {
			const cards = document.querySelector(".set-card")
			cards.style.visibility = "visible"
			this.arrayShowCard = [];
		},
		deleteCard(id) {
			axios.patch('/api/cards/deletecard', "id=" + id).then(response => app.loadData(), Swal.fire('Tarjeta eliminada con exito')).catch(error => Swal.fire(error));
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
		signOut() {
			axios.post('/api/logout').then(response => window.location.href = "/web/index.html", Swal.fire('Nos vemos pronto ' + app.client.firstName + '!'));
		},
		format(num) { return String(num).replace(/(?<!\..*)(\d)(?=(?:\d{3})+(?:\.|$))/g, '$1,'); },
	}

});
