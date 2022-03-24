let app = new Vue({
  el: "#app",

  data: {
    //Para que lo pruebe cualquiera
    emailUser: "melba@mindhub.com",
    passwordUser: "12345",
    email: "",
    password: "",
    firstName: "",
    lastName: "",
    switchNightMode: false,
    switchSignIn: true,
    switchRegister: false,
    signInError: false
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      axios.get('/api/clients/current').then(response => window.location.href = "/web/accounts.html").catch(function (error) {
        nightMode();
        const preloaded = document.querySelector(".preloaded");
        preloaded.style.visibility = "hidden";
      })
    },

    showToggle() {
      const navMenu = document.querySelector(".nav-menu");
      navMenu.classList.toggle("invisible");
      navMenu.classList.toggle("opacity-0");
    },

    activeNightMode() {
      this.switchNightMode = !this.switchNightMode;
      nightMode();
    },

    signIn() {
      axios.post('/api/login', "email=" + this.emailUser + "&" + "password=" + this.passwordUser, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
        .then(function response() {
          if (app.emailUser == "admin") {
            window.location.href = "/web/manager.html";
          } else {
            window.location.href = "/web/accounts.html";
          }

        }).catch(function (error) {
          if (error.response) {
            app.signInError = true;
            app.passwordUser = "";
          } else if (error.request) {
            console.log(error.request);
          } else {
            console.log('Error', error.message);
          }
        })

    },

    register() {
      axios.post('/api/clients', "firstName=" + this.firstName + "&" + "lastName=" + this.lastName + "&" + "email=" + this.email + "&" + "password=" + this.password, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
        .then(function (response) {
          app.switchSignIn = true;
          app.switchRegister = false;
          app.emailUser = app.email;
          app.passwordUser = "";
          app.email = "";
          app.firstName = "";
          app.password = "";
          app.lastName = "";
           Swal.fire("¡Registro Finalizado! Ya puedes loguearte.");
        }).catch(Error =>  Swal.fire(Error.response.data));
    }
  }
})