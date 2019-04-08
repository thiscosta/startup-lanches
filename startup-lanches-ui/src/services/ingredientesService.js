const axios = require('axios');


const IngredientesService = {

    async listarIngredientes() {
        let ingredientes
        await axios.get('http://localhost:8080/ingredientes')
            .then(function (response) {
                ingredientes = response.data
            })
            .catch(function (error) {
                ingredientes = error
            })

        return ingredientes
    },

}

export default IngredientesService