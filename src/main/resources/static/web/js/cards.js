let app = new Vue({
  el: "#app",

  data: {
    client: "",
    cards: "",
    selectCard: "all",
    arrayShowCard: [],
    },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
    const urlParams = new URLSearchParams(window.location.search);
   const id = urlParams.get('id');

      axios.get("/api/clients/" + id).then(function (response) {
        app.client = response.data;
        app.cards = response.data.cards.sort((a,b) => a.id - b.id);
      }).finally(function() {
                       const preloaded = document.querySelector(".preloaded");
                       preloaded.style.visibility = "hidden";})
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
    showCard(index) {
    const cards = document.querySelector(".set-card");
    cards.style.visibility = "hidden";
    this.arrayShowCard.push(this.cards[index]);},
    hideCard(){
    const cards = document.querySelector(".set-card")
    cards.style.visibility = "visible"
    this.arrayShowCard = [];
    }
  },
});
