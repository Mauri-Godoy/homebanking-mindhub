let app = new Vue({
  el: "#app",
  data: {
    switchNightMode: "",
    accounts: "",
    number: "",
    cvv: "",
    amount: "",
    description: ""
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      axios.get('/api/clients/current')
        .then(function (response) {
          app.switchNightMode = response.data.nightMode;
        }).catch(function (Error) {
           Swal.fire("Error");
          window.location.href = "/web/index.html";
        }).finally(function () {
          nightMode();
          const preloaded = document.querySelector(".preloaded");
          preloaded.style.visibility = "hidden";
        });},

    createLoan() {
      if (this.number == "" || this.cvv == "" || this.amount == "" || this.description == "") {
         Swal.fire("Ingrese todos los datos solicitados")
      } else {
        axios.post('/api/payments', { number: this.number, amount: this.amount, cvv: this.cvv, description: this.description}).then(response => window.location.href = "/web/accounts.html").catch(error =>  Swal.fire(error.response.data));
      }
    }
}})