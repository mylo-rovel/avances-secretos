<script>
  import { cerrarSesionUsuario } from '~/utils/utility_functions.js';

  export default {
    name: 'NavbarPag',
    props:["tituloPag" ],
    data() {
      return { 
        rolUsuario: "" ,
        linkInicio: "",
        linkPerfil: "",
        linkOpciones: "",
        //#0a132d color del gob
        };
    },
    mounted() {
      let rolGuardado = window.localStorage.getItem("rol");
      if (rolGuardado) {
        rolGuardado = rolGuardado.toLowerCase();
        this.rolUsuario = rolGuardado;
        return true;
      };
    },
    methods: {
      setLinksNavbarPag(rolUsar) {
        this.linkInicio = `menu-${this.rolUsuario}`;
        // this.linkPerfil = `/perfil-usuario`;
        //this.linkOpciones = `${rolUsuario}/opciones-${rolUsuario}`;
        // console.log(this.linkInicio);
      },
      cerrarSesion(e) {
        cerrarSesionUsuario();
      }
    }
  }
</script>
<template>
    <nav class="navbar navbar-expand-lg  navbar-dark bg-dark" >
      <div class="container-fluid">
        <a class="navbar-brand" href="/">Sistema de Alerta Temprana Aluvional </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item item-menu">
              <a @click="setLinksNavbarPag(rolUsar)" class="nav-link" aria-current="page" :href="linkInicio">Inicio</a>
            </li>
            <li class="nav-item item-menu">
              <a @click="setLinksNavbarPag(rolUsar)" class="nav-link" aria-current="page" :href="linkPerfil">Perfil</a>
            </li>
            <li class="nav-item dropdown item-menu">
              <a class="nav-link  dropdown-toggle" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                Opciones
              </a>
              <ul class="desplegable  " aria-labelledby="navbarDropdown">
                <li><a class="opcion dropdown-item" href="/operador/registrar-simulacion">Registrar Simulación</a></li>
                <li><a class="opcion dropdown-item" href="/operador/iniciar-simulacion">Iniciar Simulación</a></li>
                <li><a class="opcion dropdown-item" href="/operador/ver-simulacion">Ver Simulación</a></li>
                <li><a class="opcion dropdown-item" href="/operador/lista-simulaciones">Historial de Simulaciones</a></li>
              </ul>
              <!--<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href={{linkPag}}>{{tituloPag}}</a></li>
              </ul>-->
            </li>
            <li class="nav-item item-menu">
              <a class="nav-link  cerrar-sesion-p"  v-on:click="(e) => cerrarSesion(e)"><span>Cerrar Sesión</span></a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </div>
</template>

<style>
  .cerrar-sesion-p {
    cursor: pointer;
    height:min-content;
  }
  .container-fluid {
    background-color: rgb(33,37,41);
  }
  .item-menu{
    display: inline-block;
  	background-color: #212529;
  	padding: 0.5rem 1rem;
  	position: relative;
  	line-height: 1rem;
  }
  .desplegable{
    background-color: #fff;
    padding: 1rem 2rem;
    display: none;
    position: absolute;
    top: 3rem;
    left: 0;
    font-size: 1rem;
    z-index: 1;
    width: fit-content;
    border: 0.15rem solid #212529;
  }
  .desplegable a {
    display: block;
    margin-bottom: 1rem;
  }
  .item-menu:hover > a {
    color: #ffffff;
  }
  .item-menu:hover .desplegable {
    display: block;
  }
  .opcion{
    padding: 0.5rem 1.25rem 0.5rem 1.25rem;
    font-size: 1rem;
    color: #212529;
    justify-content: center;
    
  }
  .opcion:hover {
    background: #212529;
    color: #fff;
    transform: scale(1.1);
    border-radius: 0.5rem;
  }
</style>
