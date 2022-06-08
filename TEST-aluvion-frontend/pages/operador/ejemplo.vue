<script>

import Vue from 'vue'

import SubmitButton from '~/components/SubmitButton.vue'
import CancelButton from '~/components/CancelButtom.vue'
import NavbarPag from '~/components/NavbarPag.vue'

export default Vue.extend({
    name: "ejemplo",
    components: { SubmitButton, CancelButton},
  
    data() {
        return {
          "submitbutton": "enviar",
          "cancelbutton": "cancelar"
        };
    },
    //methods:
      //  function mostrar() {
        //    document.getElementById("sidebar").style.width = "300px";
          //  document.getElementById("contenido").style.marginLeft = "300px";
            //document.getElementById("abrir").style.display = "none";
            //document.getElementById("cerrar").style.display = "inline";
    
})
</script>
<template>
    <section class="container">
        <div class="container-fluid">
            <NavbarPag :tituloPag="tituloPag"/>
        </div>
        <div class="row my-5">
            <div class="col">
                <SubmitButton :submitbutton = "submitbutton"/>
            </div>
            <div class="col">
                <CancelButtom :cancelbutton = "cancelbutton"/>
            </div>
        </div> 
        <!--<div id="sidebar" class= "sidebar" style="width: 300px;">
            <ul class="menu">
                <li><a>Secuencia</a></li>
                <li><a>Simulación</a></li>
                <li><a>Perfil</a></li>
            </ul>
        </div>
        <div>
            <a id="abrir" class="abrir-cerrar" href="" onclick="mostrar()" style="display: none;">Abrir</a>
        </div>-->
    </section>
</template>
<style>
    
</style>