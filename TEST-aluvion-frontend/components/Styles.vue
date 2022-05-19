<script>
export default {
  name: 'Styles'
}
</script>
.
<template>
    <section>
        <head>
            <title>Gestion de Usuarios</title>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
            <!-- CSS Sytlesheets -->
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
            <link rel="stylesheet" href="../Styles.css">
            <!--Bootstrap -->
            <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous">
            </script>
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.6.0/dist/umd/popper.min.js" integrity="sha384-KsvD1yqQ1/1+IA7gi3P0tyJcT3vR+NdBTt13hSJ2lnve8agRGXTTyNaBYmCR/Nwi" crossorigin="anonymous">
            </script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.min.js" integrity="sha384-nsg8ua9HAw1y0W1btsyWgBklPnCUAFLuTMS2G72MMONqmOymq585AcH49TLBQObG" crossorigin="anonymous">
            </script>
            <!-- Font Awesome -->
            <script src="https://kit.fontawesome.com/d82979872c.js" crossorigin="anonymous"></script>
            <!-- Font Google 1 Squada One -->
            <link rel="preconnect" href="https://fonts.gstatic.com">
            <link href="https://fonts.googleapis.com/css2?family=Squada+One&display=swap" rel="stylesheet">
        </head>
    </section>
</template>
<style>
            @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500&display=swap');

            * {
            margin:0;
            font-family: 'Poppins', sans-serif;
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

            .common-menu-button {
            background-color: white;
            border: 1px solid black;
            border-radius: 50px;
            padding: 1rem 3rem;
            margin: 20px 0;
            font-size: 2rem;
            font-weight: 300;
            }

            .common-menu-button:hover {
            background-color: black;
            color: white;
            transform: scale(1.1);
            }
</style>