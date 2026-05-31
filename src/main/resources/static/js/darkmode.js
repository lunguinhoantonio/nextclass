const themeToggle = document.getElementById("theme-toggle");
const iconeLua = "fa-moon";
const iconeSol = "fa-sun";

(function () {
  if (localStorage.getItem("theme") === "dark") {
    document.body.classList.add("dark-mode");
    if (themeToggle) {
      themeToggle.querySelector("i").classList.replace(iconeLua, iconeSol);
    }
  }
})();

if (themeToggle) {
  themeToggle.addEventListener("click", () => {
    const isDark = document.body.classList.toggle("dark-mode");
    const icon = themeToggle.querySelector("i");

    if (isDark) {
      localStorage.setItem("theme", "dark");
      icon.classList.replace(iconeLua, iconeSol);
    } else {
      localStorage.setItem("theme", "light");
      icon.classList.replace(iconeSol, iconeLua);
    }
  });
}