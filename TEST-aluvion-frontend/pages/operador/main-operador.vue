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
          "tituloPag": "Simulación de Lluvias",
          "tituloContenido": "Simulación de Lluvias"
        };
    },
})
</script>

<template>
  <section class="main-operador-container">
    <article class="container-header">
        <PageHeader :headerTitle="headerTitle"/>
    </article>
    <article class="container-body-button-menu">
      <NuxtLink to="/operador/registrar-simulacion"><button class="common-menu-button">Registrar Simulación</button></NuxtLink>
      <NuxtLink to="/operador/iniciar-simulacion"><button class="common-menu-button">Iniciar Simulación</button></NuxtLink>
      <NuxtLink to= "/operador/ver-simulacion"><button class="common-menu-button">Ver Simulación</button></NuxtLink>
      <NuxtLink to= "/operador/lista-secuencia"><button class="common-menu-button">Historial de Simulaciones</button></NuxtLink>
      
    </article>
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
  background: white;
  border: 1px solid black;
  border-radius: 15px;
  padding: 10px 20px;
  /*padding: 1rem 3rem;*/
  margin: 20px 0;
  font-size: 1rem;
  font-weight: 100;
}
.common-menu-button:hover {
  background: white;
  color: black;
  transform: scale(1.1);
}
.main-operador-container {
    width: 100vw;
    height: 100vh;
    display:grid;
    grid-template-rows: 35% auto;
}
.container-body-button-menu {
  display: flex;
  flex-direction: column;
  width: fit-content;
  margin: 0 auto;
}



</style>
