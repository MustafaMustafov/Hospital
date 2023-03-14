let sidebar = document.querySelector(".sidebar")
let toggle = document.getElementById("icon")
let closebtn = document.getElementById("close")
let buttons = document.querySelector(".list")
let body = document.querySelector("*")
let closemodal = document.querySelectorAll("#close-modal")
let modals = document.querySelectorAll(".group-modal, .filter-modal")
let groupDoctor = document.getElementById("group-doctor")
let groupSpecialty = document.getElementById("group-specialty")
let groupDate = document.getElementById("group-date")
let radioDoctor = document.getElementById("radioDoc")
let radioSpecialty = document.getElementById("radioSpecialty")
let radioDate = document.getElementById("radioDate")

let arr = [groupDoctor, groupSpecialty, groupDate]
let radioArr = [radioDoctor, radioSpecialty, radioDate]

toggle.addEventListener("click", toggleSidebar)
closebtn.addEventListener("click", closeSidebar)
closeModals()

//sidebar
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

//modals
buttons.addEventListener("click", (e) => {
    modals.forEach((modal) => {
        modal.classList.forEach((classs) => {
            if (classs.includes(e.target.textContent.split(" ").join("").toLowerCase())) {
                closeModal(e)
                modal.classList.add("filter-group-cl")
                closeSidebar(e)
            }
        })
    })

})

function closeModals() {
    closemodal.forEach((closeb) => {
        closeb.addEventListener("click", closeModal)
    })
}

function closeModal(e) {
    e.preventDefault()
    modals.forEach((modal) => {
        if (modal.classList.contains("filter-group-cl")) {
            modal.classList.remove("filter-group-cl")
        }

    })
}

$(".filter-modal, .group-modal").draggable({
    handle: ".header"
});

function getInput(radioButton, input) {
    radioButton.addEventListener("click", () => {
        if(radioButton.checked) {
            arr.forEach(inp => {
                inp.style.display = "none"
            });
            input.style.display = "flex"
        }
    })

}

let i = 0;
radioArr.forEach(radio => {
    getInput(radio, arr[i])
    i++;
})



