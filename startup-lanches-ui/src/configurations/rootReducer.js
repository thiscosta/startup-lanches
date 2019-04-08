import lanches from '../reducers/lanchesReducer'
import compras from '../reducers/comprasReducer'
import ingredientes  from '../reducers/ingredientesReducer'

import { combineReducers } from 'redux'

export default combineReducers({ 
    lanches, compras, ingredientes
})
