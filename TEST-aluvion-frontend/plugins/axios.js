export default function ({ $axios, store }, inject) {
    const sataApi = $axios.create();
    
    sataApi.setBaseURL('http://localhost:8081/api');

    // const token = store.state.currentUser.token
    // sataApi.setToken(token, 'Bearer')
    // console.log(store)
    sataApi.onResponse((response) => {
        if (response.status === 404) {
            console.log('Oh no it returned a 404')
        }
    })

    inject('sataApi', sataApi)
}
