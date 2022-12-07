<script>
export default {
  name: 'PageHeader',
  props:["headerTitle"]
}
</script>

<template>
  <section>
    <div class="common-header-top-logo">
      <img class="common-anid-logo" src="~/assets/img/anid-logo.png" alt="conicyt-logo" height="50" weight="100">
      <div class="common-header-top-logo-text">
        <p>FONDEF</p>
        <p>Fondo de Fomento al Desarrollo</p>
        <p>Cientifico y Tecnológico</p>
      </div>
      <div class="common-header-top-options">
      <!-- in this part goes the profile section -->
      
      </div>
    </div>
    <div class="common-header-top-title-container">
    <!-- this is the part where we put the title -->
      <!--<p class="common-header-title">{{headerTitle}}</p>-->
    </div>
  </section>
</template>

<style>

  /* TOP RIGHT PART OF THE HEADER */
  .common-header-top-logo {
      /* background-color: red; */
      width: 95%;
      margin: 0 auto;
      height: fit-content;
      margin-top: 1.5%;
      display: flex;
  }
  .common-header-top-logo-text{
    width: fit-content;
    font-size: 0.85rem;
    margin-top: auto;
    margin-left: 0.7rem;
  }
  .common-header-top-logo-text p:first-child {
    font-size: 1rem;
  }



  /* TOP LEFT PART OF THE HEADER */

  .common-header-top-options {
    margin-left: auto;
    width: auto;
    height: auto;
    display:flex;
    flex-direction: column;
    justify-content: space-between;
  }

  .common-header-top-profile-section {
    margin-left: auto;
    padding-right: 10%;
    height: 50%;
    text-align: center;
  }
  .common-profile-logo {
    max-width: 30px;
  }

  .common-header-top-options-section{
    display:flex;
    justify-content: space-between;
    padding-top: 2%;
  }

  .common-header-top-options-section p,
  .common-header-top-profile-word {
    font-size: 1.2rem;
    font-weight: 300;
  }

  /* BOTTOM PART OF THE HEADER */

  .common-header-top-title-container {
    width: 95%;
    margin: 0 auto;
    text-align: left;
    border-bottom: 1px solid black;
  }
  .common-header-title {
    font-size: 25px;
    font-weight: 350;
    padding: 10px;
    padding-top: 20px;
  }
  li{
    font-family: 'Poppins', sans-serif;
    color: black; 
  }

</style>
