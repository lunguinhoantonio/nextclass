 const menuBtn = document.getElementById("menu-btn");
  const mobileMenu = document.getElementById("mobile-menu");

  menuBtn.addEventListener("click", () => {
    mobileMenu.classList.toggle("active");
  });

  const navBar = document.querySelector(".navbar");

let prevScrollPos = window.scrollY;

window.addEventListener("scroll", function () {

    let currScrollPos = window.scrollY;

    if (currScrollPos > prevScrollPos) {

        // DESCENDO
        navBar.style.transform = "translateY(-105%)";

    } else {

        // SUBINDO
        navBar.style.transform = "translateY(0%)";
    }

    prevScrollPos = currScrollPos;
});