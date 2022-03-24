let app = new Vue({
  el: "#app",
  data: {
    switchNightMode: "",
    accounts: "",
    loans: "",
    loanSelected: "",
    id: 1,
    amount: "",
    payments: "",
    selectPayments: "",
    destinationAccountNumber: ""
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      axios.get('/api/clients/current')
        .then(function (response) {
          app.switchNightMode = response.data.nightMode;
          app.accounts = response.data.accounts.sort((a, b) => a.id - b.id);
          app.selectPayments = "";
        }).catch(function (Error) {
           Swal.fire("Error");
          window.location.href = "/web/index.html";
        }).finally(function () {
          nightMode();
          const preloaded = document.querySelector(".preloaded");
          preloaded.style.visibility = "hidden";
        });

      axios.get('/api/loans').then(function (response) {
        app.loans = response.data;
        app.loanSelected = response.data[app.id - 1];
        app.payments = app.loanSelected.payments.sort((a, b) => a - b);
      })
    },

    createLoan() {
      if (this.selectPayments == "" || this.destinationAccountNumber == "" || this.amount == "") {
         Swal.fire("Ingrese todos los datos solicitados")
      } else {
        axios.post('/api/loans', { id: this.id, amount: this.amount, payments: this.selectPayments, destinationAccountNumber: this.destinationAccountNumber }).then(response => window.location.href = "/web/loans.html",  Swal.fire("Prestamo creado con exito"))
          .catch(error =>  Swal.fire("Error"));
      }

    },
    format(num) { return String(num).replace(/(?<!\..*)(\d)(?=(?:\d{3})+(?:\.|$))/g, '$1,'); },
  }
})