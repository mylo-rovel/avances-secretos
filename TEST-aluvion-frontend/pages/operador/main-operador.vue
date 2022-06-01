<script>
// ESTE ES LA PAGINA LOGIN
import Vue from 'vue'
import PageHeader from '~/components/PageHeader.vue'
import NavbarPag from '~/components/NavbarPag.vue'
//import SubmitButton from '~/components/SubmitButton.vue'
import TituloContenido from '~/components/TituloContenido.vue'


export default Vue.extend({
  name: "MainOperadorPage",
  components: { PageHeader, NavbarPag, TituloContenido },
  data() {
        return {
          "tituloPag": "Secuencia",
          "tituloContenido": "Simulación de lluvias"
        };
    },
})
</script>


<template>
  <section>
    <div class = "container-fluid">
      <NavbarPag :tituloPag="tituloPag"/>
    </div>
    <div class = "container-fluid">
      <PageHeader :headerTitle="headerTitle"/>
    </div>
    <div class = "container">
      <div class="container-body-button-menu">
        <TituloContenido :tituloContenido="tituloContenido"/>
        <div class="contenido-boton">
          <NuxtLink to="/operador/secuencia"> <button class="common-menu-button">Secuencias de lluvia</button> </NuxtLink>
        </div>
        <div class="contenido-boton">
          <NuxtLink to="/operador/simulacion"> <button class="common-menu-button">Simulación</button></NuxtLink>
        </div>
      </div>
    </div>
  </section>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500&display=swap');

* {
  margin:0;
  font-family: 'Poppins', sans-serif;
}
.contenido-boton{
  padding: 100px, 100px;
  text-align: center;
}

.container-body-button-menu {
  display: flex;
  flex-direction: column;
  width: fit-content;
  margin: 0 auto;
}

.common-menu-button {
  background: #6675df;
  border: 1px solid black;
  border-radius: 25px;
  padding: 10px 30px;
  /*padding: 1rem 3rem;*/
  margin: 20px 0;
  font-size: 10px;
  font-weight: 100;
}
.common-menu-button:hover {
  background: white;
  color: #6675df;
  transform: scale(1.1);
}

</style>
