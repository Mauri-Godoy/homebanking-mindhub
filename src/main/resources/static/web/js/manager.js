let app = new Vue({
  el: "#app",

  data: {
    response: "",
    clients: "",
    newClient: {
      email: "",
      firstName: "",
      lastName: "",
      password: "",
    },
    newEmail: "",
    newFirstName: "",
    newLastName: "",
    newPassword: "",
    loanName: "",
    maxAmount: "",
    payments: []
  },

  created() {
    this.loadData();
  },

  methods: {
    loadData() {
      axios.get("/rest/clients").then(function (response) {
        app.response = response.data;
        app.clients = response.data._embedded.clients;
      });
    },
    addClient() {
      if (
        this.newEmail.includes("@") &&
        this.newEmail.includes(".") &&
        this.newFirstName &&
        this.newLastName &&
        this.newPassword
      ) {
        this.newClient.email = this.newEmail;
        this.newClient.firstName = this.newFirstName;
        this.newClient.lastName = this.newLastName;
        this.newClient.password = this.newPassword;
        this.clients.push(this.newClient);
        this.newEmail = "";
        this.newFirstName = "";
        this.newLastName = "";
        this.newPassword = "";
        this.postClient();
        swal("Registro finalizado", "", "success");
      } else {
        swal("Falta completar datos", "", "error");
      }
    },
    postClient() {
      axios
        .post("/rest/clients", {
          firstName: this.newClient.firstName,
          lastName: this.newClient.lastName,
          email: this.newClient.email,
          password: this.newClient.password,
        })
        .then((response) => {
          this.loadData();
        });
    },
    deleteClient(url) {
      swal({
        title: "Estás seguro?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
      }).then((willDelete) => {
        if (willDelete) {
          axios.delete(url).then((response) => {
            this.loadData();
          });
          swal("El cliente ha sido eliminado", {
            icon: "success",
          });
        }
      });
    }, signOut() {
      axios.post('/api/logout').then(response => window.location.href = "/web/index.html")
    },
    addLoan() {
      axios.post("/rest/loans", {
          name: app.loanName,
          maxAmount: app.maxAmount,
          payments: app.payments
        })
        .then((response) => {
          app.name = "",
          app.maxAmount = "",
          app.payments = []
          this.loadData();
        });
    },
  },
});
