const axios = require('axios');

const returnObject = {
    success: null,
    data: ''
}

const IngredientesService = {

    async listarIngredientes() {
        let ingredientes = returnObject
        await axios.get('http://localhost:8080/ingredientes')
            .then(function (response) {
                ingredientes.success = true
                ingredientes.data = response.data
            })
            .catch(function (error) {
                ingredientes.success = false
                ingredientes.data = error
            })

        return ingredientes
    },

}

export default IngredientesService