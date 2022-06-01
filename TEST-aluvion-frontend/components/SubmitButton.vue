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
    <button class=" btn submit-button">{{submitbutton}}</button>
  </section>
</template>

<style>
  .submit-button {
    background: #6675df;
    padding: 10px 20px 10px 20px;
    font-size: 20px;
    color: #fff;
    justify-content: center;
    text-transform: uppercase;
    font-weight: bold;
  }

</style>
