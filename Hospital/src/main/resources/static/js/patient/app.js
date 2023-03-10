let sidebar = document.querySelector(".sidebar")
let toggle = document.getElementById("icon")
let closebtn = document.getElementById("close")

toggle.addEventListener("click", toggleSidebar)
closebtn.addEventListener("click", closeSidebar)

function toggleSidebar(e) {
    e.preventDefault()  //prevents the default action of the event
    sidebar.classList.add("sidebar2")
    toggle.style.display = "none"
}

function closeSidebar(e) {
    e.preventDefault()
    sidebar.classList.remove("sidebar2")
    toggle.style.display = "unset"
}