import lanches from '../reducers/lanchesReducer'
import compras from '../reducers/comprasReducer'
import ingredientes  from '../reducers/ingredientesReducer'
import mensagens from '../reducers/mensagemReducer'

import { combineReducers } from 'redux'

export default combineReducers({ 
    lanches, compras, ingredientes, mensagens
})
