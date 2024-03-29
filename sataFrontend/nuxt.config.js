export default {
  // Global page headers: https://go.nuxtjs.dev/config-head
  head: {
    title: 'sataFrontend',
    htmlAttrs: {
      lang: 'en',
    },
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' },
      { name: 'format-detection', content: 'telephone=no' },
    ],
    link: [
      { 
        rel: 'icon',
        type: 'image/x-icon',
        href: '/favicon.ico' 
      },
      { 
        rel: 'stylesheet',
        href: '/css/bootstrap.min.css' 
      },
      { 
        rel: 'stylesheet',
        href: '/css/custom.css' 
      }
    ],
  },

  // Global CSS: https://go.nuxtjs.dev/config-css
  css:[],

  // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
  plugins: [],

  // Auto import components: https://go.nuxtjs.dev/config-components
  components: true,

  // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
  buildModules: [
    // https://go.nuxtjs.dev/eslint
    // '@nuxtjs/eslint-module',
    '@nuxtjs/google-fonts'
  ],

  // Modules: https://go.nuxtjs.dev/config-modules
  modules: [],

  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {},
  googleFonts: {
    families: {
      Poppins: true,
      Roboto: true,
      // 'Josefin+Sans': true,
      // Lato: [100, 300],
      // Raleway: {
      //   wght: [100, 400],
      //   ital: [100]
      // },
    }
  },
}
