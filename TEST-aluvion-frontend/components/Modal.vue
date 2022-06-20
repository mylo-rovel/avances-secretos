<script>
    export default {
        name: 'Modal',
        props: ["infoContenido"],
        data(){
            return {
                infoContenido: "",
            }
        },
        methods: {
            abrirModal(){
                document.getElementById("submit-btn").addEventListener("click",
                function() {
                    document.querySelector(".back-modal").style.display = "flex";
                });
            },
            cerrarModal(){
                document.querySelector(".close-modal").addEventListener("click",
                function() {
                    document.querySelector('.back-modal').style.display = "none";
                });

            },
        }
    }

</script>
<template>

    <section>
        <div class= "back-modal">
            <div class="modal-content">
                <div>
                    <h2>{{infoContenido}}</h2>
                </div>
                <div class="close-modal">+</div>                
            </div>
        </div>  
    </section>

</template>
<style>
    .back-modal {
        display: none;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.7);
        position: relative;
        top: 0;
        justify-content: center;
        align-items: center;
    }
    .modal-content {
        /*width: 500px;
        height: 300px;*/
        background-color: white;
        border-radius: 10px;
        text-align: center;
        padding: 5px;
        position: relative;
        margin-left: 40px;
        margin-right: 40px;
    }
    .close-modal {
        position: absolute;
        top: 0;
        right: 14px;
        font-size: 42px;
        transform: rotate(45deg);
        cursor: pointer;
    }
</style>