<script lang="ts">
// imagine this is the login panel
import Vue from 'vue'
import SubmitButton from '../components/SubmitButton.vue'

interface buttonPropsType {
  buttonValue: string;
  buttonFunction: (email:string, password:string) => void; 
}

const buttonProps: buttonPropsType = {
  buttonValue: "ENTRAR",
  buttonFunction: async () => {

  }
}

export default Vue.extend({
    name: "IndexPage",
    components: { SubmitButton }
})
</script>


<template>
  <section>
    <!-- <NuxtLink to="/main"> to main_page AL ABORDAJE</NuxtLink> -->
    <article class="main-login-container">
      <article class="img-left-side">
        <img id="left-login-img" src="~/assets/img/mockup_login_img.png" alt="login-image"/>
      </article>
      
      <article class="text-right-side">
        <div class="right-side-header">

          <article class="login-header-logo">
            <img id="conicyt-logo" src="~/assets/img/conicyt_logo.jpg" alt="conicyt-logo">
            <div class="login-header-logo-text">
              <p>FONDEF</p>
              <p>Fondo de Fomento al Desarrollo</p>
              <p>Cientifico y Tecnológico</p>
            </div>
          </article> 

          <article class="login-header-welcome">
            <h2 id="header-welcome-mssg">Welcome!</h2>
            <h2 id="header-project-name">Proyecto Aluvión</h2>
          </article> 
      
        </div>
        <div class="right-side-body">
          <SubmitButton :buttonProps="buttonProps"  />

        </div>
      </article>
    </article>
  </section>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500&display=swap');

* {
  margin:0;
  font-family: 'Poppins', sans-serif;
}
.main-login-container {
  border: 1px solid black;
  width: 70rem;
  height: 27rem;
  margin: 13.5vh auto;
  display:grid;
  grid-template-columns: 27% auto;
}


.img-left-side {
  overflow: hidden;
  border-right: 1px solid black;;
}

#left-login-img {
  height: 100%;
  object-fit: cover;
  object-position: -6.8rem 0rem;
}


/* este es el contenedor principal */
.text-right-side {
  display: grid;
  grid-template-rows: 37% auto;
}

/* este es el fragmento que tiene el 37% del alto del conteneor */
.right-side-header {
  display: flex;
  justify-content: space-between;
}

/* contenedor del logo y el texto de fondef */
.login-header-logo {
    height: 100%;
    display: flex;
}
.login-header-logo-text{
  width: fit-content;
  margin-top: auto;
  margin-left: 0.7rem;
}
.login-header-logo-text p:first-child {
  font-size: 1.2rem;
}

#conicyt-logo { 
  height:159.8px; 
  width:auto 
}

/* contenedor del mensaje de la derecha arriba */
.login-header-welcome {
  width: fit-content;
  margin: 2rem 13% 0rem 0rem;
}




#header-welcome-mssg {
  font-size: 2rem;
  font-weight: 400;
}

#header-project-name {
  font-size: 1.2rem;
  font-weight: 400;
}
</style>
