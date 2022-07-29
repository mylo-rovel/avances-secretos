<script lang="js">
  export default {
    name: 'SubmitButton',
    props: ["submitbutton"]
  }
  // LA IDEA ES QUE ESTE COMPONENTE RECIBA ESTILOS COMO PROPS
  // PARA HACERLO MÁS CUSTOMIZABLE
  //document.getElementById("submit-button").addEventListener("click",
    //function() {
      //document.querySelector(".modal-confirmacion").style.display = "flex";
  //  }); //activar panel

</script>

<template>
  <section>
    <button id="submit-btn" type = "submit" class="submit-button">{{submitbutton}}</button>
  </section>
</template>

<style>
  .submit-button {
    background: #025cfa;
    padding: 0.5rem 1.25rem 0.5rem 1.25rem;
    font-size: 1rem;
    color: #fff;
    justify-content: center;
    text-transform: uppercase;
    font-weight: bold;
    border-radius: 10px;
  }
  .submit-button:hover {
        background: white;
        color: #025cfa;
        border: 1px solid #025cfa;
        transform: scale(1.1);
    }

</style>
