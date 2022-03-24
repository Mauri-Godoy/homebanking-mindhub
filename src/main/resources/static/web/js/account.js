let app = new Vue({
  el: "#app",

  data: {
    account: {},
    transactions: [],
    client: ""
  },

  created() {
    this.loadData();
  },

  methods: {
          loadData() {
             const urlParams = new URLSearchParams(window.location.search);
             const id = urlParams.get('id');

             axios.get('/api/accounts/' + id)
               .then(function (response) {
                 app.account = response.data;
                 app.transactions = response.data.transactions.sort((a,b) => a.id - b.id);
               }).finally(function() {
               const preloaded = document.querySelector(".preloaded");
               preloaded.style.visibility = "hidden";});
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
  },
});
