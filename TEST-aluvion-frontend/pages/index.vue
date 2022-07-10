<script>

// ESTE ES LA PAGINA LOGIN
import Vue from 'vue'
import {mapState} from "vuex";
import { checkIfUserShouldBeHere, getQuickAccessEnumsDict } from '~/utils/utility_functions.js'

export default Vue.extend({
    name: "IndexPage",
    data() {
        return { }
    },
    computed: mapState(["urlApi"]),

    mounted() {
        console.clear();
        let rolUsuario = window.localStorage.getItem("rol");
        if (["OPERADOR", "CONFIGURADOR", "ADMNISTRADOR"].includes(rolUsuario)) {
            const anchorElement = document.createElement("a");
            rolUsuario = rolUsuario.toLowerCase();
            anchorElement.href= `${rolUsuario}/menu-${rolUsuario}`;
            anchorElement.click();
            return true;
        };
        window.localStorage.clear();
        return false;
    },

    methods: {
        getCredenciales() {
            return JSON.stringify({
                        email: document.getElementById("loginCorreo").value,
                        password: document.getElementById("loginContrasena").value
            })
        },

        async fetchEnumeracionesDict (jwt) {
            console.clear();
            const serverPath = `${this.urlApi}/extras/enums`;
            const request_config = { method: 'get', headers: {'authorization': jwt} };
            const rawServerResponse = await fetch(serverPath, request_config).catch(err => err);
            if (rawServerResponse instanceof Error) return;
            const enumDictsData = await rawServerResponse.json();
            if (!(enumDictsData["listaItemsEnum"])) return;
            window.localStorage.setItem("listaItemsEnum", JSON.stringify(
                getQuickAccessEnumsDict(enumDictsData["listaItemsEnum"]))
            );
        },

        async performAuthentication() {
            const serverPath = `${this.urlApi}/login`;
            const postConfig = { 
                method: 'post', 
                body: this.getCredenciales(),
                headers: { 'Content-Type': 'application/json' }};
            return await fetch(serverPath, postConfig).catch(err => err);
        },

        guardarItemsYRedireccionar(objetoSesion) {
            let [ token, rol ] = [ objetoSesion['jsonWebToken_'], objetoSesion['rolUsuario_'] ]
            window.localStorage.setItem("token", token);
            window.localStorage.setItem("rol", rol );
            const anchorElement = document.createElement("a");
            rol = rol.toLowerCase()
            anchorElement.href= `${rol}/menu-${rol}`;
            anchorElement.click();
        },

        async enviarDatosLogin(e) {
            e.preventDefault();
            console.clear();
            const rawServerResponse = await this.performAuthentication();
            if (rawServerResponse instanceof Error) {
                alert("No se pudo iniciar sesion");
                console.log(rawServerResponse);
                return false;
            }
            const objetoSesion = await rawServerResponse.json();
            if (!objetoSesion["sesionIniciada_"]){
                alert("Credenciales incorrectas");
                return false; 
            }
            await this.fetchEnumeracionesDict(objetoSesion['jsonWebToken_']);
            this.guardarItemsYRedireccionar(objetoSesion);
            return true;
        }
    }

})

</script>

<template>
    <section>
        <div class="limiter">         
            <div class="container-login100">
                <div class="wrap-login100">
                    <div class="fill">
                        <img :src="require('~/assets/img/cerro.png?url')"/>
                    </div>
                    <form class="login100-form validate-form">
                        <span class="login100-form-title p-b-43">Iniciar Sesión</span>
                        <div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">
                            <input id="loginCorreo" class="input100" type="text" name="email">
                            <span class="focus-input100"></span>
                            <span class="label-input100">Correo electrónico</span>
                        </div>					
                        <div class="wrap-input100 validate-input" data-validate="Password is required">
                            <input id="loginContrasena" class="input100" type="password" name="pass">
                            <span class="focus-input100"></span>
                            <span class="label-input100">Contraseña</span>
                        </div>
                        <div class="flex-sb-m w-full p-t-3 p-b-32">
                            <div>
                                <a href="#" class="txt1">
                                    ¿Olvidaste tu contraseña?
                                </a>
                            </div>
                        </div>
                        <div class="container-login100-form-btn">
                            <button @click="(e) => enviarDatosLogin(e)" class="login100-form-btn">
                            INGRESAR
                            </button>
                        </div>
                    </form>   
                </div>   
            </div>
        </div>
    </section>
</template>

<style>

     @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500&display=swap');
     @import url('https://fonts.googleapis.com/css2?family=Lora:wght@700&family=Montserrat&family=Poppins&display=swap');
     @import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@700&display=swap');

    
    * {
        margin: 0px; 
        padding: 0px; 
        box-sizing: border-box;
    }

    body, html {
        height: 100%;
        font-family: 'Poppins', sans-serif;
    }
    
    input {
        outline: none;
        border: none;
    }
    label {
        display: block;
        margin: 0;
    }
    button {
        outline: none !important;
        border: none;
        background: transparent;
    }

    button:hover {
        cursor: pointer;
    }
    a {
        font-family: 'Poppins';
        font-size: 14px;
        line-height: 1.7;
        color: #666666;
        margin: 0px;
        transition: all 0.4s;
        -webkit-transition: all 0.4s;
        -o-transition: all 0.4s;
        -moz-transition: all 0.4s;
    }
    a:focus {
        outline: none !important;
    }
    a:hover {
        text-decoration: none;
        color: #6675df;
    }
    input:focus::-webkit-input-placeholder { color:transparent; }
    input:focus:-moz-placeholder { color:transparent; }
    input:focus::-moz-placeholder { color:transparent; }
    input:focus:-ms-input-placeholder { color:transparent; }

    input::-webkit-input-placeholder { color: #999999;}
    input:-moz-placeholder { color: #999999;}
    input::-moz-placeholder { color: #999999;}
    input:-ms-input-placeholder { color: #999999;}

    iframe {
        border: none !important;
    }
    .limiter {
        width: 100%;
        margin: 0 auto;
        
    }

    .container-login100 {
        width: 100%;  
        min-height: 100vh;
        display: -webkit-box;
        display: -webkit-flex;
        display: -moz-box;
        display: -ms-flexbox;
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        align-items: center;
        background: #f2f2f2;
    }
    .wrap-login100 {
        width: 100%;
        background: #fff;
        overflow: hidden;
        display: -webkit-box;
        display: -webkit-flex;
        display: -moz-box;
        display: -ms-flexbox;
        /*display: flex;
        flex-wrap: wrap;
        align-items: stretch;
        flex-direction: row-reverse;*/
        display:grid;
        grid-template-columns: 60% 40%;
    }

    .login100-form {
        min-height: 100vh;
        display: block;
        /*background-color: #f7f7f7;*/
        background-color: #f7f7f7;
        padding: 173px 55px 55px 55px;
    }

    .login100-form-title {
        width: 100%;
        display: block;
        font-family: 'Poppins';
        font-size: 30px;
        color: #333333;
        line-height: 1.2;
        text-align: center;
    }

    .wrap-input100 {
        display: -webkit-box;
        display: -webkit-flex;
        display: -moz-box;
        display: -ms-flexbox;
        display: flex;
        flex-wrap: wrap;
        align-items: flex-end;
        width: 100%;
        height: 80px;
        position: relative;
        border: 1px solid #e6e6e6;
        border-radius: 10px;
        margin-bottom: 10px;
    }

    .label-input100 {
        font-family: 'Montserrat';
        font-size: 18px;
        color: #999999;
        line-height: 1.2;

        display: block;
        position: absolute;
        pointer-events: none;
        width: 100%;
        padding-left: 24px;
        left: 0;
        top: 30px;

        -webkit-transition: all 0.4s;
        -o-transition: all 0.4s;
        -moz-transition: all 0.4s;
        transition: all 0.4s;
    }

    .input100 {
        display: block;
        width: 100%;
        background: transparent;
        font-family: 'Montserrat-Regular';
        font-size: 18px;
        color: #555555;
        line-height: 1.2;
        padding: 0 26px;
    }

    input.input100 {
        height: 100%;
        -webkit-transition: all 0.4s;
        -o-transition: all 0.4s;
        -moz-transition: all 0.4s;
        transition: all 0.4s;
    }
    .focus-input100 {
        position: absolute;
        display: block;
        width: calc(100% + 2px);
        height: calc(100% + 2px);
        top: -1px;
        left: -1px;
        pointer-events: none;
        border: 1px solid #6675df;
        border-radius: 10px;

        visibility: hidden;
        opacity: 0;

        -webkit-transition: all 0.4s;
        -o-transition: all 0.4s;
        -moz-transition: all 0.4s;
        transition: all 0.4s;

        -webkit-transform: scaleX(1.1) scaleY(1.3);
        -moz-transform: scaleX(1.1) scaleY(1.3);
        -ms-transform: scaleX(1.1) scaleY(1.3);
        -o-transform: scaleX(1.1) scaleY(1.3);
        transform: scaleX(1.1) scaleY(1.3);
    }

    .input100:focus + .focus-input100 {
        visibility: visible;
        opacity: 1;

        -webkit-transform: scale(1);
        -moz-transform: scale(1);
        -ms-transform: scale(1);
        -o-transform: scale(1);
        transform: scale(1);
    }

    .eff-focus-selection {
        visibility: visible;
        opacity: 1;

        -webkit-transform: scale(1);
        -moz-transform: scale(1);
        -ms-transform: scale(1);
        -o-transform: scale(1);
        transform: scale(1);
    }

    .input100:focus {
        height: 48px;
    }

    .input100:focus + .focus-input100 + .label-input100 {
        top: 14px;
        font-size: 13px;
    }

    .has-val {
        height: 48px !important;
    }

    .has-val + .focus-input100 + .label-input100 {
        top: 14px;
        font-size: 13px;
    }

    /*==================================================================
    [ Restyle Checkbox ]*/

    .input-checkbox100 {
        display: none;
    }

    .label-checkbox100 {
        font-family: 'Poppins';
        font-size: 13px;
        color: #999999;
        line-height: 1.4;

        display: block;
        position: relative;
        padding-left: 26px;
        cursor: pointer;
    }

    .label-checkbox100::before {
        content: "\f00c";
        font-family: FontAwesome;
        font-size: 13px;
        color: transparent;

        display: -webkit-box;
        display: -webkit-flex;
        display: -moz-box;
        display: -ms-flexbox;
        display: flex;
        justify-content: center;
        align-items: center;
        position: absolute;
        width: 18px;
        height: 18px;
        border-radius: 2px;
        background: #fff;
        border: 1px solid #6675df;
        left: 0;
        top: 50%;
        -webkit-transform: translateY(-50%);
        -moz-transform: translateY(-50%);
        -ms-transform: translateY(-50%);
        -o-transform: translateY(-50%);
        transform: translateY(-50%);
    }

    .input-checkbox100:checked + .label-checkbox100::before {
        color: #6675df;
    }
    /*------------------------------------------------------------------
    [ Button ]*/
    .container-login100-form-btn {
        width: 100%;
        display: -webkit-box;
        display: -webkit-flex;
        display: -moz-box;
        display: -ms-flexbox;
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
    }

    .login100-form-btn {
        display: -webkit-box;
        display: -webkit-flex;
        display: -moz-box;
        display: -ms-flexbox;
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 0 20px;
        width: 100%;
        height: 50px;
        border-radius: 10px;
        background: #6675df;

        font-family: 'Montserrat';
        font-size: 12px;
        color: white;
        line-height: 1.2;
        text-transform: uppercase;
        letter-spacing: 1px;

        -webkit-transition: all 0.4s;
        -o-transition: all 0.4s;
        -moz-transition: all 0.4s;
        transition: all 0.4s;
    }

    .login100-form-btn:hover {
        background: #333333;
    }

    /*------------------------------------------------------------------
    [ Responsive ]*/
     @media (max-width: 900px) {
        .wrap-login100 {
            display: flex;
            flex-direction: column-reverse;
            height: 100vh;
        }
        .fill, .fill img {
            display:none;
        }

    }
/*
    @media (max-width: 992px) {
        .login100-form {
            width: 50%;
            padding-left: 30px;
            padding-right: 30px;
        }
        .login100-more {
            width: 50%;
        }
    }

    @media (max-width: 768px) {
        .login100-form {
            width: 100%;
        }
        .login100-more {
            display: none;
            background-image: url('~/assets/img/cerro.png');
        }
    }

    @media (max-width: 576px) {
        .login100-form {
            padding-left: 15px;
            padding-right: 15px;
            padding-top: 70px;
        }
    }

*/
    /*------------------------------------------------------------------
    [ Alert validate ]*/

    .validate-input {
        position: relative;
    }

    .alert-validate::before {
        content: attr(data-validate);
        position: absolute;
        z-index: 100;
        max-width: 70%;
        background-color: #fff;
        border: 1px solid #c80000;
        border-radius: 2px;
        padding: 4px 25px 4px 10px;
        top: 50%;
        -webkit-transform: translateY(-50%);
        -moz-transform: translateY(-50%);
        -ms-transform: translateY(-50%);
        -o-transform: translateY(-50%);
        transform: translateY(-50%);
        right: 12px;
        pointer-events: none;

        font-family: Poppins-Regular;
        color: #c80000;
        font-size: 13px;
        line-height: 1.4;
        text-align: left;

        visibility: hidden;
        opacity: 0;

        -webkit-transition: opacity 0.4s;
        -o-transition: opacity 0.4s;
        -moz-transition: opacity 0.4s;
        transition: opacity 0.4s;
    }

    .alert-validate::after {
        content: "\f12a";
        font-family: FontAwesome;
        display: block;
        position: absolute;
        z-index: 110;
        color: #c80000;
        font-size: 16px;
        top: 50%;
        -webkit-transform: translateY(-50%);
        -moz-transform: translateY(-50%);
        -ms-transform: translateY(-50%);
        -o-transform: translateY(-50%);
        transform: translateY(-50%);
        right: 18px;
    }

    .alert-validate:hover:before {
        visibility: visible;
        opacity: 1;
    }

    .beautyButton,
    .beautyButton:hover {
        text-decoration: none;
        color: white;
    }

    @media (max-width: 992px) {
        .alert-validate::before {
            visibility: visible;
            opacity: 1;
        }
    }
    .login100-more {
        width: calc(100% - 560px);
        background-repeat: no-repeat;
        background-size: cover;
        background-position: center;
        position: relative;
        z-index: 1;
    }

    .login100-more::before {
        content: "";
        display: block;
        position: absolute;
        z-index: -1;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        background: rgba(0,0,0,0.1);
    }
    .p-t-3 {padding-top: 3px;}
    .p-b-32 {padding-bottom: 32px;}
    .p-b-43 {padding-bottom: 43px;}
    .flex-sb-m {
        display: -webkit-box;
        display: -webkit-flex;
        display: -moz-box;
        display: -ms-flexbox;
        display: flex;
        justify-content: space-between;
        -ms-align-items: center;
        align-items: center;
    }
    .w-full {
        width: 100%;
    }
    .txt1 {
        font-family: 'Poppins';
        font-size: 13px;
        line-height: 1.4;
        color: #555555;
    }
    .txt2 {
        font-family: 'Poppins';
        font-size: 13px;
        line-height: 1.4;
        color: #999999;
    }
    .size1 {
        width: 355px;
        max-width: 100%;
    }
    .size2 {
        width: calc(100% - 43px);
    }

    .bg1 {background: #3b5998;}
    .bg2 {background: #1da1f2;}
    .bg3 {background: #cd201f;}

    .fill {
        display: flex;
        justify-content: center;
        align-items: center;
        overflow: hidden;
        height: 100%;
    }
    .fill img {
        min-width: 100%;
        min-height: 100%;
        object-fit: cover;
    }
</style>
