const axios = require('axios');

const returnObject = {
    success: null,
    data: ''
}

const LanchesService = {

    async listarLanches() {
        let lanches = returnObject
        await axios.get('http://localhost:8080/lanches')
            .then(function (response) {
                lanches.success = true
                lanches.data = response.data
            })
            .catch(function (error) {
                lanches.success = false
                lanches.data = error
            })

        return lanches
    },

}

export default LanchesService