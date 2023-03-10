let footer = document.querySelector("#footertxt");
let navbar = document.querySelector(".navbar")
let about = document.querySelector(".about")
let aboutbtn = document.getElementById("aboutbtn")

//year for footer
let foo = new Date()
let year = foo.getFullYear()

//footer text
footer.textContent = `© ${year} Hospital app. All rights reserved.`

// navbar add to fixed
window.addEventListener("scroll", function() {
    //if scrollY of the window is above 200 pixels then add class
    if (window.scrollY > 200){
        navbar.classList.add("navbar2")
    } else {
        navbar.classList.remove("navbar2")
    }
})

//scroll algorithm
function scroll(element){
    if (!navbar.classList.contains("navbar2")){
        let ab = element.offsetTop
        window.scrollTo(0, ab - 190)
    } else{
        let ab = element.offsetTop
        window.scrollTo(0, ab - 90)
    }
}

//aplying it to about button
aboutbtn.addEventListener("click", (e) => {
    e.preventDefault();
    scroll(about);
})

