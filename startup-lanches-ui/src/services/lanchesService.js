const axios = require('axios');


const LanchesService = {

    async listarLanches() {
        let lanches
        await axios.get('http://localhost:8080/lanches')
            .then(function (response) {
                lanches = response.data
            })
            .catch(function (error) {
                lanches = error
            })

        return lanches
    },

}

export default LanchesService