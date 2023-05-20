const btnOpens = document.getElementById('pops')
const modals = document.querySelector('.modals')
const btnCloses = document.querySelector('.modals .btns')

const toggelModals = () => {
    modals.classList.toggle('modal-reveal')
}
btnOpens.addEventListener('click', function(){
    toggelModals()
})

btnCloses.addEventListener('click', function(){
    toggelModals()
})




