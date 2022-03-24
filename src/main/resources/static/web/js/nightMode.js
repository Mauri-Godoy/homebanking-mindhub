function nightClassCreator(normalClass, nightClass) {
  const newClass = document.querySelectorAll(".night_" + normalClass);
  if (app.switchNightMode) {
    newClass.forEach(nw => {
      nw.className += (" " + nightClass);
      nw.classList.remove(normalClass);
    });
  } else {
    newClass.forEach(nw => {
      nw.className += (" " + normalClass);
      nw.classList.remove(nightClass);
    })
  }
}

function nightClassNameCreator(name, normalClass, nightClass) {
  const newClass = document.querySelectorAll(".night_" + name);
  if (app.switchNightMode) {
    newClass.forEach(nw => {
      nw.className += (" " + nightClass);
      nw.classList.remove(normalClass);
    });
  } else {
    newClass.forEach(nw => {
      nw.className += (" " + normalClass);
      nw.classList.remove(nightClass);
    })
  }
}

function nightMode() {


  nightClassCreator("border-slate-600", "border-slate-700");
  nightClassCreator("bg-slate-600", "bg-slate-900");
  nightClassCreator("bg-white", "bg-slate-900");
  nightClassCreator("text-slate-600", "text-white");
  nightClassNameCreator("text-sky-to-white", "text-sky-500", "text-white")
  nightClassNameCreator("text-slate-to-emerald", "text-slate-600", "text-emerald-400");
  nightClassNameCreator("border-slate-to-emerald", "border-slate-600", "border-emerald-400");
  nightClassNameCreator("hover-bg-slate-to-emerald", "hover:bg-slate-600", "hover:bg-emerald-400");



  //antes de crear la función 

  const body = document.querySelector("body");
  const texts = document.querySelectorAll(".text-night");
  const bgs = document.querySelectorAll(".bg-night");
  const textsWhite = document.querySelectorAll(".text-night-white");
  const bgsRed = document.querySelectorAll(".bg-night-red");
  const bgsEmerald = document.querySelectorAll(".bg-night-emerald");
  const bgsSlate = document.querySelectorAll(".bg-night-slate")
  const menus = document.querySelectorAll(".menu-night");

  if (app.switchNightMode) {

    body.className += " bg-slate-900";

    texts.forEach(text => {
      text.className += " text-slate-300";
      text.classList.remove("text-slate-600");
    });

    textsWhite.forEach(textWhite => {
      textWhite.classList.remove("text-slate-500");
      textWhite.classList.remove("text-black");
      textWhite.className += " text-white";
    });

    bgs.forEach(bg => {
      bg.className += " bg-slate-800";
      bg.classList.remove("bg-white");
    })

    bgsRed.forEach(bg => {
      bg.className += " border-red-300";
      bg.classList.remove("bg-red-50");
    })

    bgsEmerald.forEach(bg => {
      bg.className += " border-emerald-300";
      bg.classList.remove("bg-emerald-50");
    })

    bgsSlate.forEach(bg => {
      bg.className += " bg-slate-800";
      bg.classList.remove("bg-sky-500");
    })

    menus.forEach(menu => {
      menu.classList.remove("border-slate-600");
      menu.classList.remove("text-slate-600");
      menu.classList.remove("hover:bg-slate-600");
      menu.classList.remove("hover:text-white");
      menu.className += "  border-white text-white hover:bg-white hover:text-slate-900";
    })

  } else {

    body.classList.remove("bg-slate-900")

    texts.forEach(text => {
      text.classList.remove("text-slate-300");
      text.className += " text-slate-600";
    });

    textsWhite.forEach(textWhite => {
      textWhite.classList.remove("text-slate-500");
      textWhite.classList.remove("text-white");
      textWhite.className += " text-black";
    });

    bgs.forEach(bg => {
      bg.className += " bg-white";
      bg.classList.remove("bg-slate-800");
    })

    bgsRed.forEach(bg => {
      bg.className += " bg-red-50";
      bg.classList.remove("border-red-300");
    })

    bgsEmerald.forEach(bg => {
      bg.className += " bg-emerald-50";
      bg.classList.remove("border-emerald-300");
    })

    bgsSlate.forEach(bg => {
      bg.className += " bg-sky-500";
      bg.classList.remove("bg-slate-800");
    })

    menus.forEach(menu => {
      menu.classList.remove("border-white");
      menu.classList.remove("text-white");
      menu.classList.remove("hover:bg-white");
      menu.classList.remove("hover:text-slate-900");
      menu.className += "  border-slate-600 text-slate-600 hover:bg-slate-600 hover:text-white";
    })
  }
}

