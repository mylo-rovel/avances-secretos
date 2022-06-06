<script>
export default {
  name: 'NavbarPag',
  props:["tituloPag" ]
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
              <a class="nav-link" aria-current="page" href="/operador/main-operador">Inicio</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" aria-current="page" href="/operador/encender">Perfil</a>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link active dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
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
              <a class="nav-link active" href="/#/">Cerrar Sesión</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </div>
</template>