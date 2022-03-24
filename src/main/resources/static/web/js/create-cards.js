let app = new Vue({
  el: "#app",

  data: {
    type: "",
    color: "",
    switchNightMode: "",
    cardsCredit: [],
    cardsDebit: []
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      axios.get('/api/clients/current').then(function (response) {
        app.cardsDebit = response.data.cards.filter(element => element.type == 'DEBIT');
        app.cardsCredit = response.data.cards.filter(element => element.type == 'CREDIT');
        app.switchNightMode = response.data.nightMode;
      }).catch(function (Error) {
         Swal.fire("Error");
        window.location.href = "/web/index.html";
      }).finally(function () {
        nightMode();
        const preloaded = document.querySelector(".preloaded");
        preloaded.style.visibility = "hidden";
      })
    },
    createCard() {
      if (app.type == "" || app.color == "") {
         Swal.fire('Ingrese todos los datos solicitados');
      } else {
        axios.post('/api/clients/current/cards', "type=" + this.type + "&" + "color=" + this.color, { headers: { 'content-type': 'application/x-www-form-urlencoded' } }).then(response => window.location.href = "/web/cards.html")
      }
    }
  }
})