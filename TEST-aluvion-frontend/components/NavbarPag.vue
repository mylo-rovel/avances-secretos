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
        this.linkInicio = `${rolUsuario}/menu-${rolUsuario}`;
        this.linkPerfil = `/perfil-usuario`;
        this.linkOpciones = `${rolUsuario}/opciones-${rolUsuario}`;
      },
      cerrarSesion(e) {
        cerrarSesionUsuario();
      }
    }
  }
</script>
<template>
    <nav class="navbar navbar-expand-lg" style="background-color: #f1f1f1;">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">Sistema de Alerta Temprana Aluvional </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item">
              <a class="nav-link" aria-current="page" :href="linkInicio">Inicio</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" aria-current="page" :href="linkPerfil">Perfil</a>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link active dropdown-toggle" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                Opciones
              </a>
              <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href="/operador/encender">{{tituloPag}}</a></li>
              </ul>
              <!--<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href={{linkPag}}>{{tituloPag}}</a></li>
              </ul>-->
            </li>
            <li class="nav-item">
              <a class="nav-link active cerrar-sesion-p"  v-on:click="(e) => cerrarSesion(e)"><span>Cerrar Sesión</span></a>
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
</style>