const btnOpen = document.getElementById('pop')
const modal = document.querySelector('.modal')
const btnClose = document.querySelector('.modal .btn')

const toggelModal = () => {
    modal.classList.toggle('modal-reveal')
}

btnOpen.addEventListener('click', function(){
          toggelModal()
})
btnClose.addEventListener('click', function(){
    toggelModal()
})


let togg2 = document.getElementById("pop2");
let d2 = document.getElementById("d2");


function togg(){
  if(getComputedStyle(d2).display != "none"){
    d2.style.display = "none";
  } else {
    d2.style.display = "block";
  }
};
togg2.onclick = togg;