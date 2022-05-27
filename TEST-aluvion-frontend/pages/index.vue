<script>
// ESTE ES LA PAGINA LOGIN
import Vue from 'vue'
import SubmitButton from '../components/SubmitButton.vue'


export default Vue.extend({
    name: "IndexPage",
    components: { SubmitButton },
    // async asyncData({ $axios }) {

    // },
    data() {
      return {
        emailInputText: "email",
        passwordInputText: "contraseña",
        loginButtonText: "INGRESAR",

        apiURL: "http://localhost:8080/api",

        userEmailField: "",
        userPasswordField: ""
    }},
    methods: {
       getCredentialsToSend() {
         return {
           userEmail: document.getElementById("loginEmailField").value, 
           userPassword: document.getElementById("loginPasswordField").value
           };
       },
       displayCurrentCredentials() {
         alert(`Email: ${document.getElementById("loginEmailField").value}\nPassword: ${document.getElementById("loginPasswordField").value}`)
       },

       async sendDataToAPI({$axios}) {
         // esta función envía los valores del email y de la contraseña al servidor
         // y desplegará con un cuadrito (la parte de alert()) lo que nos respondan
          const serverPath = `${this.apiURL}/login/`;
          // alert(serverPath);
          const serverResponse = await $axios.$post(serverPath, this.getCredentialsToSend()).catch(() => "ay wey. error1");
          alert(serverResponse);
          return { serverResponse }
          
      },
        async enviarOrdenEncender({$axios}) {
          const serverPath = `${this.apiURL}/leddeldestino`;
          const serverResponse = await $axios.$get(serverPath).catch(err => err);
          if (serverResponse instanceof Error) {
              alert("ERROR. rayos :(", serverResponse)
              return false;
          }
          alert(serverResponse);
          return true;
      },
    }

})
</script>


<template>
    <section class="main-login-container">
      <article class="img-left-side">
        <img id="left-login-img" src="~/assets/img/mockup_login_img.png" alt="login-image"/>
      </article>
      
      <article class="text-right-side">
        <div class="right-side-header">

          <article class="login-header-logo">
            <img id="anid-logo" src="~/assets/img/anid-logo.png" alt="conicyt-logo">
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
          <article class="right-side-body-inputs">
            <InputBox idValue="loginEmailField"  :textoTransparente="emailInputText"/>
            <InputBox idValue="loginPasswordField"   :textoTransparente="passwordInputText"/>
          </article>
          <article class="right-side-body-buttons">
            <NuxtLink to="/operador/main-operador"><SubmitButton :textbutton="loginButtonText"/></NuxtLink>
            <p>¿Contraseña olvidada?</p>
          </article>
        </div>
      </article>



            <!-- POR ACÁ SE ENVÍAN LOS VALORES AL SERVER DEL HECTOR -->
      <!-- LA SINTAXIS ES ASÍ DADO QUE SE OCUPA UN ARGUMENTO EN ESPECÍFICO QUE NO SÉ COMO PODRIAMOS OBTENER DE OTRA FORMA-->
      <button    @click="() => enviarOrdenEncender({$axios})"  class="btn btn-primary">ENCENDER LED</button>


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

  #anid-logo { 
    height:159.8px; 
    width:auto 
  }

  /* contenedor del mensaje de la derecha arriba */
  .login-header-welcome {
    width: fit-content;
    margin: 2rem 13% 0rem 0rem;
  }

  /* contenedor del cuerpo => formulario y boton */
  /* FORMULARIO */
  .right-side-body {
    display: grid;
    grid-template-rows: auto 38%;
  }

  .right-side-body-inputs .input-box-component:first-child{
    border-bottom: 0;
  }

  .right-side-body-inputs {
    width: 30rem;
    margin: 0 auto;
    padding-top: 4.5rem;
  }

  /* BOTON Y CONTRASEÑA OLVIDADA */
  .right-side-body-buttons {
    text-align: center;
    width: fit-content;
    margin: 0 auto;
  }

  .right-side-body-buttons .submit-button-component:hover{
    background-color: black;
    color: white;
    transition: 0.2s;
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
